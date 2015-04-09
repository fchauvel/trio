
package eu.diversify.trio.graph;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The set of node in a graph
 */
public class NodeSet extends ArrayList<Node> {

    public NodeSet() {
    }

    public NodeSet(int initialCapacity) {
        super(initialCapacity);
    }

    public NodeSet(Collection<? extends Node> c) {
        super(c);
    }

    
    
}
