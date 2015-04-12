package eu.diversify.trio.graph.queries;

import eu.diversify.trio.graph.Edge;
import eu.diversify.trio.graph.Graph;
import eu.diversify.trio.graph.Node;
import static eu.diversify.trio.graph.queries.Leaving.leaving;

/**
 * Check whether a node is the successor of the given node.
 */
public class SuccessorOf implements NodePredicate {

    public static SuccessorOf successorOf(Node node) {
        return new SuccessorOf(node);
    }
    
    private final Node source;

    public SuccessorOf(Node source) {
        this.source = source;
    }

    @Override
    public boolean isSatisfiedBy(Graph graph, Node node) {
        for (Edge anyEdge: graph.edges(leaving(source))) {
            if (anyEdge.reaches(node)) {
                return true;
            }
        }
        return false;
    }    
    
}
