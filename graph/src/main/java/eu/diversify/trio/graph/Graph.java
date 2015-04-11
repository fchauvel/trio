package eu.diversify.trio.graph;

/**
 * The interface of a graph
 */
public interface Graph {

    NodeSet nodes();

    EdgeSet edges();

    void connect(Node source, Node target);

    void disconnect(Node source, Node target);

}
