#include<stdio.h>
#include<stdlib.h>



void* map(void* list, int len, void* (*f)(void*),short type_size) {
	void* returned_list = malloc(len*type_size);
	void* temp = returned_list;
	for (int i = 0; i<len; i++) {
		*temp = f(list[i]);
		temp = temp + type_size;
	}
	return returned_list;
}
int square(int x) {
	return x*x;
}

void printList(void* list, int len, short type_size) {
	printf("[");
	for (int i = 0; i<len; i++) {
		if (i==(len-1)) {
			printf("%i]\n",list[i]);
		} else {
			printf("%i,",list[i]);
		}
	}
}

void findPythagore(int list[]) {

}












int main() {
	int len = 5;
	int list[] = {1,2,3,4,5};
	int* ret = map(list,len,square);
	printList(ret,len);
	printf("%lu \n",sizeof());
	return 0;
}
