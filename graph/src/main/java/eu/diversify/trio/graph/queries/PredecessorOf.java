
package eu.diversify.trio.graph.queries;

import eu.diversify.trio.graph.Edge;
import eu.diversify.trio.graph.Graph;
import eu.diversify.trio.graph.Node;
import static eu.diversify.trio.graph.queries.Reaching.reaching;

/**
 * Check whether a node is one of the predecessor of the selected one.
 */
public class PredecessorOf implements NodePredicate {
    
    public static final PredecessorOf predecessorOf(Node node) {
        return new PredecessorOf(node);
    }
    
    private final Node target;

    public PredecessorOf(Node target) {
        this.target = target;
    }

    @Override
    public boolean isSatisfiedBy(Graph graph, Node node) {
        for (Edge anyEdge: graph.edges(reaching(target))) {
            if (anyEdge.leaves(node)) {
                return true;
            }
        }
        return false;
    }
    
    
    
}
