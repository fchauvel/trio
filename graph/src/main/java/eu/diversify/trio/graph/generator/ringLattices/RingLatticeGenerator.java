
package eu.diversify.trio.graph.generator.ringLattices;

import eu.diversify.trio.graph.AdjacencyMatrix;
import eu.diversify.trio.graph.Graph;
import eu.diversify.trio.graph.Node;
import eu.diversify.trio.graph.generator.GraphGenerator;

/**
 * Define the neighborhood of a ring lattice
 */
public class RingLatticeGenerator implements GraphGenerator {

    private final int nodeCount;
    private final int neighborhoodSize;

    public RingLatticeGenerator(int nodeCount, int diameter) {
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
    
    public Graph nextGraph() {
        final AdjacencyMatrix graph = new AdjacencyMatrix(nodeCount);
        for (Node eachSource: graph.nodes()) {
            for (Node eachTarget: graph.nodes()) {
                if (allowsEdgeBetween(eachSource, eachTarget)) {
                    graph.connect(eachSource, eachTarget);
                }
            }
        }
        return graph;
    }

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
    
}
