#include <sys/ipc.h>
#include <sys/msg.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>

#include "msg.h"

int main(int argc, char * argv[])
{
  msgbuf msg;

  // get the message queue
  int msqid = msgget(ftok("msg.h", 1), 0);
  if(msqid < 0)
  {
    perror(strerror(errno));
    exit(1);
  }

  // read lines from standard input
  while(fgets(msg.line, 80, stdin))
  {
    if(feof(stdin))
      break;
   
    // send one line, receive decrypted line back
    msg.mtype = TYPE_ENCRYPTED;
    msgsnd(msqid, &msg, sizeof(msgbuf), 0);
    msgrcv(msqid, &msg, sizeof(msgbuf), TYPE_DECRYPTED, MSG_NOERROR);

    // print decrypted line
    printf("%s", msg.line);
  }

  return 0;
}
