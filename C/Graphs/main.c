#include "graph.h"
#include <stdio.h>
#include <stdlib.h>

int main() {
    graph_t graph = create_graph(3);
    int** adj_matrix = (int**) malloc(3 * sizeof(int*));
    for (int i = 0; i < 3; i++) {
        adj_matrix[i] = (int*) malloc(3 * sizeof(int));
        for (int j = 0; j < 3; j++) {
            adj_matrix[i][j] = 0;
        }
    }
    adj_matrix[0][1] = 1;
    adj_matrix[1][2] = 1;
    init(graph, adj_matrix);
    set_node_data(graph->nodes[0], (void*) "Node 1");
    set_node_data(graph->nodes[1], (void*) "Node 2");
    set_node_data(graph->nodes[2], (void*) "Node 3");
    for (int i = 0; i < graph->num_nodes; i++) {
        printf("Node %d: %s\n", i + 1, (char*) get_node_data(graph->nodes[i]));
        for (int j = 0; j < graph->nodes[i]->num_neighbors; j++) {
            printf("    Neighbor %d: %s\n", j + 1, (char*) get_node_data(graph->nodes[i]->neighbors[j]));
        }
    }
    print_graph(graph);
    return 0;
}
