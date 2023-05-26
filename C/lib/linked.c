#include<stdio.h>
#include<stdlib.h>

typedef struct Node
{
    int data;
    struct Node * next;
} Node;

typedef struct List
{
    int length;
    Node * root;
} List;

List* init_list() {
    List* list = (List*)malloc(sizeof(List));
    list->length = 0;
    list->root = (Node*)malloc(sizeof(Node));
    return list;
}

void append(List* list, int x) {
    Node node;
    node.data = x;
    node.next = NULL;
    Node* temp = list->root;
    Node* temp2 =temp;
    while(temp != NULL) {
        temp2 = temp;
        temp = temp->next;
    }
    temp2->next = &node;
    list->length++;
}

void print(List* list) {
    Node* node = list->root;
    printf("\n[");
    while(node != NULL) {
        printf("%i,",node->data);
        node = node->next;
    }
    printf("]\n");
}

int main() {
    List* list = init_list();
    for (int i = 0; i<10; i++) {
        append(list,i);
    }
    printf("%i\n",list->length);

    return 0;
}