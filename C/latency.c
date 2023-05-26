#include <stdio.h>
#include <time.h>

int main() {
// Set up variables to store the start and end times of the measurement
struct timespec start, end;
// Use the clock_gettime function to get the current time
clock_gettime(CLOCK_REALTIME, &start);

// Do some work on CPU 1
int i;
for (i = 0; i < 10000000; i++);

// Use the clock_gettime function to get the current time again
clock_gettime(CLOCK_REALTIME, &end);

// Calculate the elapsed time by subtracting the start time from the end time
long elapsed = (end.tv_sec - start.tv_sec) * 1000000000 + (end.tv_nsec - start.tv_nsec);

// Print the elapsed time
printf("Elapsed time: %ld nanoseconds\n", elapsed);

return 0;
}
