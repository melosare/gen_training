/*
===============================================================================
                  03-60-256 SYSTEM PROGRAMMING (Winter 2015)
                                 ASSIGNMENT 2
				WOGHIREM LAB 52
===============================================================================

LAB 52
JAN 30 2015
Purpose: mimic the ls utility when given -l  flag
Author: deconstructed myls.c file with added code for full
functionality as instructed. Parts also taken from other two provided files
*/

//libraries & variable

#include <pwd.h>
#include <grp.h>
#include <stdlib.h>
#include <stdio.h>
#include <dirent.h>
#include <sys/stat.h>
#include <time.h>
#include <string.h>

//definitions

int listdir(const char * path)
{
  struct stat fileInfo;
  time_t dateEpo;
  char dateCON[24];
  struct dirent * entry;
  DIR * dp;
  char filePath[256];
  int uid;
  int gid;

  dp = opendir(path);
  if(dp == NULL)
  {
    perror("could not open directory");
    return 1;
  }

  while(entry = readdir(dp))
  {
    /* This loop iterates over each file entry in the directory.
     * Each file name is stored in entry->d_name. */
     //file path recorded and used to print permissions

    strcpy(filePath, path);
    strcat(filePath, "/");
    strcat(filePath, entry->d_name);
    if(&entry->d_name[0] != "."){ //this should stop the print
				  //of hidden files as we look at 
				  //the character that begins the 
				  //name and a false return should 
				  //skip the inner block of code but it won't
    if(!stat(filePath, &fileInfo)){
    	printf( (S_ISDIR(fileInfo.st_mode)) ? "d" : "-");
        printf( (fileInfo.st_mode & S_IRUSR) ? "r" : "-");
    	printf( (fileInfo.st_mode & S_IWUSR) ? "w" : "-");
    	printf( (fileInfo.st_mode & S_IXUSR) ? "x" : "-");
    	printf( (fileInfo.st_mode & S_IRGRP) ? "r" : "-");
    	printf( (fileInfo.st_mode & S_IWGRP) ? "w" : "-");
    	printf( (fileInfo.st_mode & S_IXGRP) ? "x" : "-");
    	printf( (fileInfo.st_mode & S_IROTH) ? "r" : "-");
    	printf( (fileInfo.st_mode & S_IWOTH) ? "w" : "-");
    	printf( (fileInfo.st_mode & S_IXOTH) ? "x  " : "-  ");

    	printf("%d\t", fileInfo.st_nlink); //link count
        
        //user ID
        struct passwd * pwd;
	uid = (int)fileInfo.st_uid;
        if((pwd = getpwuid(uid)) != NULL)
        printf("%s\t", pwd->pw_name);

        // group ID
	struct group * grp;
	gid = fileInfo.st_gid;
  	if((grp = getgrgid(gid)) != NULL)
    	printf("%s\t", grp->gr_name);


    	printf("%ld\t", fileInfo.st_size); // file size

    	//time conversion
    	dateEpo = fileInfo.st_mtime;
    	strftime(dateCON, 24, "%b %d %H:%M", localtime(&dateEpo));

    	printf("%s \t", dateCON); // time of last mod
    }else{
	printf("problem gathering stat");
    }//end if else
         puts(entry->d_name);
   }//end outer if
   }//end while

  closedir(dp);

  return 0;
}

// main function
int main(int argc, char * argv[])
{
  if(argc == 1)
    return listdir(".");
  else
    return listdir(argv[1]);
}

