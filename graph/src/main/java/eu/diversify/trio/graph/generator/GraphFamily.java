
package eu.diversify.trio.graph.generator;

import eu.diversify.trio.graph.Graph;
import eu.diversify.trio.graph.Node;

/**
 * Capture the needed connection with the neighborhood
 */
public interface GraphFamily extends Graph {

    int nodeCount();
    
    int edgeCount();
    
    boolean allowsEdgeBetween(Node source, Node target);
    
    
}
