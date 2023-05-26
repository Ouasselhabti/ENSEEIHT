#include <stdio.h>
#include <omp.h>

int main(int** argc) {
#pragma omp parallel
{
	int ID = omp_get_thread_num(); 
	printf("Hello World from core num  %d \n",ID);
}
return 0;
}
