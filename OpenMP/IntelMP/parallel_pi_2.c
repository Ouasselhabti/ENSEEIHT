#include <stdio.h>
#include <omp.h>
#include <math.h>
#define NUM_THREADS 4
int main(int ** args) {
    omp_set_num_threads(NUM_THREADS);
    double pi = 0.0;
    double x1 = 0;
    double x2 = 1;
    long long  N = 100000;
    double step = (x2 - x1)/(double)N;
    double t = omp_get_wtime();
    //starting summing the area
#pragma omp parallel
{
    double tarea = 0.0;
    int id = omp_get_thread_num();
    double x = ((double)id)*step;
    for (int i = id; i<N; i = i+NUM_THREADS) {
        tarea+= (1/(1+(x*x)))*step;

        x += step*NUM_THREADS;
    }
#pragma omp atomic
    pi+=tarea;
}
    t = omp_get_wtime() - t;
    printf("The integral is %0.10f \n",4.0*pi);
    printf("It was calculated in %fs\n",t);

    return 0;
}