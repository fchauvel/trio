
package eu.diversify.trio.graph.queries;

import eu.diversify.trio.graph.Edge;

/**
 * Represent criterion to filter edges
 */
public interface EdgePredicate {
    
    boolean isSatisfiedBy(Edge edge);
    
}
