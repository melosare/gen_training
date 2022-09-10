#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <signal.h>
#include <semaphore.h>
#include <errno.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/shm.h>
#include <sys/socket.h>
#include <arpa/inet.h>

#define MSG_IN 1

typedef struct {
  long mtype;
  char client_host[32];
  unsigned short client_port;
  char text[80];
} chat_msg;

static void childwait(int signo);
static void cleanup(int signo);
static void message(int signo);

int sd, msqid;

int main(int argc, char * argv[])
{
  int csd, c, s;
  struct sockaddr_in server, client;
  pid_t pid;
  sem_t * semid;
  chat_msg msg;

  if(argc != 2)
  {
    perror("usage: server port");
    exit(2);
  }

  signal(SIGCHLD, childwait);

  struct sigaction act_cu;
  act_cu.sa_handler = cleanup;
  sigemptyset(&act_cu.sa_mask);
  act_cu.sa_flags = 0;
  sigaction(SIGTERM, &act_cu, NULL);
  sigaction(SIGINT, &act_cu, NULL);

  struct sigaction act_msg;
  act_msg.sa_handler = message;
  sigemptyset(&act_msg.sa_mask);
  sigaddset(&act_msg.sa_mask, SIGTERM);
  sigaddset(&act_msg.sa_mask, SIGINT);
  act_msg.sa_flags = SA_RESTART;
  sigaction(SIGUSR1, &act_msg, NULL);

  s = shmget(IPC_PRIVATE, sizeof(sem_t), IPC_CREAT | 0600);
  semid = (sem_t *)shmat(s, 0, 0);

  if(sem_init(semid, 1, 10) == -1)
  {
    perror(strerror(errno));
    exit(1);
  }

  msqid = msgget(ftok("msgsrv.c", 1), IPC_CREAT | 0666);
  if(msqid == -1)
  {
    perror(strerror(errno));
    exit(1);
  }

  sd = socket(AF_INET, SOCK_STREAM, 0);
  if(sd == -1)
  {
    perror(strerror(errno));
    exit(1);
  }

  int yes = 1;
  if(setsockopt(sd, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(int)) == -1)
  {
    perror(strerror(errno));
    exit(1);
  }

  server.sin_addr.s_addr = INADDR_ANY;
  server.sin_family = AF_INET;
  server.sin_port = htons(atoi(argv[1]));

  if(bind(sd, (struct sockaddr *)&server, sizeof(server)) == -1)
  {
    perror(strerror(errno));
    exit(1);
  }

  listen(sd, 3);

  for(;;)
  {
    int v;
    sem_getvalue(semid, &v);
    printf("%d connections left\n", v);
    c = sizeof(struct sockaddr_in);
    if((csd = accept(sd, (struct sockaddr *)&client, (socklen_t*)&c)) == -1)
    {
      perror(strerror(errno));
      continue;
    }

    pid = fork();
    if(pid == 0)
    {
      signal(SIGINT, SIG_DFL);
      signal(SIGTERM, SIG_DFL);
      signal(SIGUSR1, SIG_DFL);

      if(sem_wait(semid) == -1)
      {
        perror(strerror(errno));
        exit(1);
      }

      msg.mtype = MSG_IN;
      strcpy(msg.client_host, inet_ntoa(client.sin_addr));
      msg.client_port = ntohs(client.sin_port);

      printf("* %s:%u connected\n", msg.client_host, msg.client_port);

      int n;
      while((n = recv(csd, msg.text, 80, 0)) > 0)
      {
        msg.text[n] = '\0';
        msgsnd(msqid, &msg, sizeof(chat_msg), 0);
        kill(getppid(), SIGUSR1);
      }

      close(csd);
      sem_post(semid);
      printf("* %s:%u disconnected\n", msg.client_host, msg.client_port);
      exit(0);
    }

    close(csd);
  }

  return 0;
}

static void childwait(int signo)
{
  waitpid(-1, NULL, WNOHANG);
}

static void cleanup(int signo)
{
  shutdown(sd, SHUT_RDWR);
  msgctl(msqid, IPC_RMID, NULL);
  exit(0);
}

static void message(int signo)
{
  int i;
  chat_msg msg;

  for(;;)
  {
    if(msgrcv(msqid, &msg, sizeof(chat_msg), MSG_IN, MSG_NOERROR | IPC_NOWAIT) == -1)
      break;
    printf("[%s:%u] %s", msg.client_host, msg.client_port, msg.text);
  }
}
