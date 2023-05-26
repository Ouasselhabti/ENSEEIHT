#include <stdlib.h>
#include <time.h>
#include <stdio.h>

int main(void) {
     int iam;
     time_t t=time(NULL);
   #pragma omp parallel private(iam)
     {
       iam = omp_get_thread_num();
       #pragma omp master 
       {
        sleep(0.1);
       }
      if (iam !=0) {
        #pragma omp barrier
      }
       printf("Thread %2d reached this point at time %ld.\n",iam,time(NULL)-t);
     }
     return 0;
}
