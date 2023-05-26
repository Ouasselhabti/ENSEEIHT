#ifndef GRAPH_H
#define GRAPH_H

struct Node {
    void* data;
    struct Node** neighbors;
    int num_neighbors;
};

struct Graph {
    struct Node** nodes;
    int num_nodes;
};

typedef struct Graph* graph_t;

graph_t create_graph(int num_nodes);
void init(graph_t g, int** adj_matrix);
void add_neighbor(struct Node* node, struct Node* neighbor);
void set_node_data(struct Node* node, void* data);
void* get_node_data(struct Node* node);
void print_graph(graph_t g);

#endif


