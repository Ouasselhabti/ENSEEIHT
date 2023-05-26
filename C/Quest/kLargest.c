#include<stdio.h>
#include<stdlib.h>

bool in(int list[], int len, int x) {
	bool isthere = false;
	for (int i = 0; i<len; i++){
		if( list[i] == x) {
			isthere = true;
			break;
		}
	
	}
	return isthere;
}
int * findmax(int list[],int len,int visited_index[]) {
	int* ret = (int*)malloc(sizeof(int));
	int max = list[0];
	int index = 0;
	for (int i = 1; i<len; i++) {
		if ((list[i]>max)&&(!in(visited_index,len,i))) {
			max = list[i];
			index = i;
		}
	}
	*ret = max;
	*(ret+1) = index;
	return ret;
}


void printFirst(int list[] , int k,int len) {
	int inc;
	int visited_index[len];
	for (int i = 0; i<len; i++){
		visited_index[i] = -1;
	}
	for (int inc = 0; inc<k; inc++) {
			int* ret = findmax(list,len,visited_index);
			printf("%i, ",ret[0]);
			visited_index[inc] = ret[1];
		}
	printf("\n");
}

int main() {
	int len = 20;
	int list[] = {1, 23, 12, 9, 30, 2, 50,129,19982,8,192,12891,1982832,192};
	int k = 9;
	printFirst(list,k,len);
	
	return 0;
}
