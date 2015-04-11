
package eu.diversify.trio.graph.generator;

import eu.diversify.trio.graph.EdgeSet;
import eu.diversify.trio.graph.Node;
import eu.diversify.trio.graph.NodeSet;

/**
 * Define the neighborhood of a ring lattice
 */
public class RingLatticeModel implements GraphFamily {

    private final int nodeCount;
    private final int neighborhoodSize;

    public RingLatticeModel(int nodeCount, int diameter) {
        if (diameter < 0) {
            final String description = String.format("Invalid neighborhood, diameter must be positive (found %d)", diameter);
            throw new IllegalArgumentException(description);
        }
        if (diameter % 2 == 1) {
            final String description = String.format("Invalid neighborhood, diameter must be even (found %d)", diameter);
            throw new IllegalArgumentException(description); 
        }
        this.nodeCount = nodeCount;
        this.neighborhoodSize = diameter;
    }
    
    @Override
    public int nodeCount() {
        return nodeCount;
    }
    
    @Override
    public int edgeCount() {
        return nodeCount * nodeCount;
    }    
    
    @Override 
    public boolean allowsEdgeBetween(Node source, Node target) {
        int distance = Math.abs(source.index() - target.index());
        if (distance > nodeCount / 2) {
            distance  = nodeCount - distance;
        }
        return distance > 0 && distance <= radius();
    }

    private int radius() {
        return neighborhoodSize / 2;
    }

    @Override
    public NodeSet nodes() {
        final NodeSet nodes = new NodeSet();
        for(int index=0 ; index < nodeCount ; index++) {
            nodes.add(new Node(index));
        }
        return nodes;
    }

    @Override
    public EdgeSet edges() {
        throw new UnsupportedOperationException("Not supported in graph family"); 
    }
    
}
