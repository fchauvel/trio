
package eu.diversify.trio.graph.generator;

import eu.diversify.trio.graph.AdjacencyMatrix;
import eu.diversify.trio.graph.Graph;
import eu.diversify.trio.graph.Node;
import eu.diversify.trio.graph.util.Count;

/**
 * Define the neighborhood of a ring lattice
 */
public class RingLatticeGenerator implements GraphGenerator {

    private final Count nodeCount;
    private final Count neighborCount;

    public RingLatticeGenerator(Count nodeCount, Count neighborCount) {
        this.nodeCount = nodeCount;
        this.neighborCount = validate(neighborCount);
    }
    
    private Count validate(Count neighborCount) {
        if (neighborCount.isOdd()) {
            final String description = String.format("Neighbor count must be even (found %d)", neighborCount.value());
            throw new IllegalArgumentException(description);
        }
        return neighborCount;
    }
    
    @Override
    public Graph nextGraph() {
        final AdjacencyMatrix graph = new AdjacencyMatrix(nodeCount.value());
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
        if (distance > nodeCount.value() / 2) {
            distance  = nodeCount.value() - distance;
        }
        return distance > 0 && distance <=  neighborCount.value() / 2;
    }

   
    
}
