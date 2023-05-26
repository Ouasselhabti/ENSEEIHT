#include <stdio.h>
#include <omp.h>
#include <math.h>

int main(int ** args) {
    double x1 = 0;
    double x2 = 1;
    long long  N = 10000;
    double step = (x2 - x1)/N;
    double area = 0;
    double t = omp_get_wtime();
    omp_set_schedule(0x2,N/omp_get_num_threads());
#pragma omp parallel
{
    double x = 0; 

    //starting summing the area
#pragma omp for reduction(+:area) schedule(runtime)
    for (int i = 0; i<N; i++) {
        x = (double)(i)/(double)(N);
        area += (1/(1+(x*x)))*step;
    }
}
    t = omp_get_wtime() - t;
    printf("The integral is %0.10f \n",4.0*area);
    printf("It was calculated in %fs\n",t);



    return 0;
}