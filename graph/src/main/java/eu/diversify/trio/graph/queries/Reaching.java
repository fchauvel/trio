
package eu.diversify.trio.graph.queries;

import eu.diversify.trio.graph.Edge;
import eu.diversify.trio.graph.Node;

/**
 * Check whether a given edge enter the given node
 */
public class Reaching implements EdgePredicate {
    
    public static Reaching reaching(Node node) {
        return new Reaching(node);
    }
    
    private final Node target;

    public Reaching(Node target) {
        this.target = target;
    }

    @Override
    public boolean isSatisfiedBy(Edge edge) {
        return edge.reaches(target);
    }
    
    
    
    
    
}
