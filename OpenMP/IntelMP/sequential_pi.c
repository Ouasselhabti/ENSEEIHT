#include <stdio.h>
#include <omp.h>
#include <math.h>

int main(int ** args) {
    double x1 = 0;
    double x2 = 1;
    double x = 0; 
    long long  N = 10000;
    double step = (x2 - x1)/N;
    double area = 0;
    double t = omp_get_wtime();
    //starting summing the area
    for (int i = 0; i<N; i++) {
        area += (1/(1+(x*x)))*step;
        x += step;
    }
    t = omp_get_wtime() - t;
    printf("The integral is %0.10f \n",4.0*area);
    printf("It was calculated in %fs\n",t);



    return 0;
}