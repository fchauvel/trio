package eu.diversify.trio.graph.generator;

import static eu.diversify.trio.graph.generator.GraphFactory.graphFactory;
import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.graph.model.Vertex;
import eu.diversify.trio.utility.Count;

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
        final Graph graph = graphFactory().emptyGraph(nodeCount);
        for (Vertex eachSource : graph.vertexes()) {
            for (Vertex eachTarget : graph.vertexes()) {
                if (allowsEdgeBetween(eachSource, eachTarget)) {
                    graph.connect(eachSource, eachTarget);
                }
            }
        }
        return graph;
    }
    
    private boolean allowsEdgeBetween(Vertex source, Vertex target) {
        return allowsEdgeBetween(source.id(), target.id());
    }

    boolean allowsEdgeBetween(int sourceId, int targetId) {
        int distance = Math.abs(sourceId - targetId);
        if (distance > nodeCount.value() / 2) {
            distance = nodeCount.value() - distance;
        }
        return distance > 0 && distance <= neighborCount.value() / 2;
    }

}
