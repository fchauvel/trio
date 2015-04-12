package eu.diversify.trio.graph;

/**
 * The interface of a graph
 */
public interface Graph {

    /**
     * @return the set of nodes in this graph
     */
    NodeSet nodes();

    /**
     * @return the set of edges in this graph
     */
    EdgeSet edges();

    /**
     * Create a new edge in this graph
     *
     * @param source the source node of the new edge
     * @param target the target node of the new edge
     * @return the resulting newly created edge
     */
    Edge connect(Node source, Node target);

    /**
     * Remove the given edge from the graph, or does nothing if there is no such
     * edge
     *
     * @param edge the edge to remove
     */
    void disconnect(Edge edge);

}
