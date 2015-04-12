package eu.diversify.trio.graph;

import eu.diversify.trio.graph.queries.EdgePredicate;
import eu.diversify.trio.graph.queries.NodePredicate;
import java.util.List;

/**
 * The interface of a graph
 */
public interface Graph {

    /**
     * @return the set of nodes in this graph
     */
    List<Node> nodes();
    
    /**
     * 
     * @return the set of nodes that matches the given predicate
     * @param predicate the predicate that must be satisfied for any node to be selected;
     */
    List<Node> nodes(NodePredicate predicate);
    
    /**
     * @return the set of edges in this graph
     */
    List<Edge> edges();

    /**
     * @return the subset of edges that match the given predicate
     *
     * @param predicate the predicate that must be satisfied for any edge to be
     * selected
     */
    List<Edge> edges(EdgePredicate predicate);

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
