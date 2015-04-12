
package eu.diversify.trio.graph.queries;

import eu.diversify.trio.graph.Edge;
import eu.diversify.trio.graph.Node;

/**
 *  Test whether a given edges enter a given node
 */
public class Leaving implements EdgePredicate {

    public static Leaving leaving(Node node) {
        return new Leaving(node);
    }
    
    private final Node source;
    
    public Leaving(Node target) {
        this.source = target;
    }

    @Override
    public boolean isSatisfiedBy(Edge edge) {
        return edge.leaves(source);
    }
    
    
    
    
    
}
