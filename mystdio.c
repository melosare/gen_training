#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>

#include "mystdio.h"

#define DEFAULT_MODE (S_IRUSR | S_IWUSR | S_IRGRP | S_IWGRP | S_IROTH | S_IWOTH)

int makeflags(const char * mode)
{
  int flags = 0;

  if(!strcmp(mode, "r"))
  {
    flags |= O_RDONLY;
  }
  else
  {
    if(mode[0] == 'w')
      flags |= O_CREAT | O_TRUNC;

    if(mode[0] == 'a')
      flags |= O_CREAT | O_APPEND;

    flags |= mode[strlen(mode) - 1] == '+' ? O_RDWR : O_WRONLY;
  }

  return flags;
}

MYFILE * myfopen(const char * path, const char * mode)
{
  // allocate MYFILE struct
  MYFILE * fp = (MYFILE *)malloc(sizeof(MYFILE));

  // open file and store file descriptor
  fp->fd = open(path, makeflags(mode), DEFAULT_MODE);
  if(fp->fd == -1)
  {
    return NULL;
  }

  return fp;
}

MYFILE * myfdopen(int fd, const char * mode)
{
  // allocate MYFILE struct
  MYFILE * fp = (MYFILE *)malloc(sizeof(MYFILE));

  // check that the mode matches the file descriptor flags
  int flags = fcntl(fd, F_GETFL);
  switch(mode[0])
  {
    case 'r':
      if(!((flags & O_RDONLY) || (flags & O_RDWR)))
        return NULL;
    case 'w':
    case 'a':
      if(!((flags & O_WRONLY) || (flags & O_RDWR)))
        return NULL;
  }

  fp->fd = fd;
  return fp;
}

int myfclose(MYFILE * fp)
{
  // check for null pointer
  if(!fp)
    return MYEOF;

  // close file
  if(close(fp->fd) == -1)
  {
    fp->error = 1;
    return MYEOF;
  }

  // free memory for MYFILE struct
  free(fp);
  fp = NULL;

  return 0;
}

int myfileno(MYFILE * fp)
{
  return fp->fd;
}

int myfgetc(MYFILE * fp)
{
  // read 1 byte from the file
  char c;
  int r = read(fp->fd, &c, 1);

  // check for error or EOF
  if(r == -1)
  {
    fp->error = 1;
    return MYEOF;
  }
  else if(r == 0)
  {
    fp->eof = 1;
    return MYEOF;
  }

  return (int)c;
}

char * myfgets(char * buf, int n, MYFILE * fp)
{
  int i;

  for(i = 0; i < n - 1; ++i)
  {
    char c;

    // read 1 byte from the file
    int r = read(fp->fd, &c, 1);

    // check for error, EOF, or newline
    if(r == -1)
    {
      fp->error = 1;
      return NULL;
    }
    else if(r == 0)
    {
      fp->eof = 1;
      buf[i] = '\0';
      break;
    }
    
    // store the character
    buf[i] = c;

    // stop at a newline
    if(c == '\n')
    {
      buf[i + 1] = '\0';
      break;
    }
  }

  return buf;
}

int myfputc(int c, MYFILE * fp)
{
  // write 1 byte to the file
  char cc = (char)c;
  if(write(fp->fd, &cc, 1) != 1)
  {
    fp->error = 1;
    return MYEOF;
  }

  return c;
}

int myfputs(const char * str, MYFILE * fp)
{
  // write to the file
  if(write(fp->fd, str, strlen(str)) != strlen(str))
  {
    fp->error = 1;
    return MYEOF;
  }

  return 0;
}

int myferror(MYFILE * fp)
{
  return fp->error;
}

int myfeof(MYFILE * fp)
{
  return fp->eof;
}

void myclearerr(MYFILE * fp)
{
  // clear error and eof flags
  fp->error = 0;
  fp->eof = 0;
}

long myftell(MYFILE * fp)
{
  // return the current offset
  return (long)lseek(fp->fd, 0, SEEK_CUR);
}

int myfseek(MYFILE * fp, long offset, int whence)
{
  // seek to the given offset
  if(lseek(fp->fd, (off_t)offset, whence) == -1)
  {
    fp->error = 1;
    return -1;
  }

  return 0;
}

void myrewind(MYFILE * fp)
{
  // seek to the beginning of the file
  lseek(fp->fd, 0, SEEK_SET);
}
