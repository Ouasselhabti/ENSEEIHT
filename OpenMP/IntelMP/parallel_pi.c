#include <stdio.h>
#include <omp.h>
#include <math.h>
#define NUM_THREADS 4
int main(int ** args) {
    omp_set_num_threads(NUM_THREADS);
    double tarea = 0.0;
    double x1 = 0;
    double x2 = 1;
    long long  N = 10000;
    double step = (x2 - x1)/(double)N;
    double area[NUM_THREADS];
    double t = omp_get_wtime();
    //starting summing the area
#pragma omp parallel
{
    int id = omp_get_thread_num();
    double x = ((double)id)*step;
    for (int i = id; i<N; i = i+NUM_THREADS) {
        area[id] += (1/(1+(x*x)))*step;
        x += step*NUM_THREADS;
    }
}
    for (int i = 0; i<NUM_THREADS; i++) {
        tarea+=area[i];
    }
    t = omp_get_wtime() - t;
    printf("The integral is %0.10f \n",4.0*tarea);
    printf("It was calculated in %fs\n",t);



    return 0;
}