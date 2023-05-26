#include "graph.h"
#include <stdio.h>
#include <stdlib.h>

graph_t create_graph(int num_nodes) {
    graph_t graph = (graph_t) malloc(sizeof(struct Graph));
    graph->num_nodes = num_nodes;
    graph->nodes = (struct Node**) malloc(num_nodes * sizeof(struct Node*));
    for (int i = 0; i < num_nodes; i++) {
        graph->nodes[i] = (struct Node*) malloc(sizeof(struct Node));
        graph->nodes[i]->neighbors = NULL;
        graph->nodes[i]->num_neighbors = 0;
    }
    return graph;
}

void init(graph_t g, int** adj_matrix) {
    for (int i = 0; i < g->num_nodes; i++) {
        for (int j = 0; j < g->num_nodes; j++) {
            if (adj_matrix[i][j] == 1) {
                add_neighbor(g->nodes[i], g->nodes[j]);
            }
        }
    }
}

void add_neighbor(struct Node* node, struct Node* neighbor) {
    node->num_neighbors++;
    node->neighbors = (struct Node**) realloc(node->neighbors, node->num_neighbors * sizeof(struct Node*));
    node->neighbors[node->num_neighbors - 1] = neighbor;
}

void set_node_data(struct Node* node, void* data) {
    node->data = data;
}

void* get_node_data(struct Node* node) {
    return node->data;
}

void print_graph(graph_t g) {
    for (int i = 0; i < g->num_nodes; i++) {
        printf("%s -> (", (char*) get_node_data(g->nodes[i]));
        for (int j = 0; j < g->nodes[i]->num_neighbors; j++) {
            printf("%s", (char*) get_node_data(g->nodes[i]->neighbors[j]));
            if (j != g->nodes[i]->num_neighbors - 1) {
                printf(", ");
            }
        }
        if(g->nodes[i]->num_neighbors==0)
          printf("empty");
        printf(")\n");
    }
}

