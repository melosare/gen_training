#ifndef MYFILE_H_
#define MYFILE_H_

typedef struct {
  int fd;
  int error : 1;
  int eof : 1;
} MYFILE;

#endif // MYFILE_H_
