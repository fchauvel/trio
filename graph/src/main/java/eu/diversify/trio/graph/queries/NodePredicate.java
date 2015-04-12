
package eu.diversify.trio.graph.queries;

import eu.diversify.trio.graph.Graph;
import eu.diversify.trio.graph.Node;

/**
 * Predicate that apply on nodes
 */
public interface NodePredicate {
    
    boolean isSatisfiedBy(Graph graph, Node node);
    
}
