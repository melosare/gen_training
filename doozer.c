#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <signal.h>
#include <semaphore.h>
#include <errno.h>

static void cleanup(int signo);

sem_t * semid;

int main(int argc, char * argv[])
{
  // register signal handling for termination cleanup
  struct sigaction act;
  act.sa_handler = cleanup;
  sigemptyset(&act.sa_mask);
  act.sa_flags = SA_RESTART;
  sigaction(SIGTERM, &act, NULL);
  sigaction(SIGINT, &act, NULL);

  // get the semaphore
  semid = sem_open("rocksem", 0);
  if(semid == SEM_FAILED)
  {
    perror(strerror(errno));
    exit(1);
  }

  // block until a job is available
  sem_wait(semid);

  // post working notice
  while(1)
  {
    puts("I'm working!");
    sleep(2);
  }
}

static void cleanup(int signo)
{
  sem_post(semid);
  exit(0);
}
