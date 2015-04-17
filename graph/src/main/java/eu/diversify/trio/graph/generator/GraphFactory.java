package eu.diversify.trio.graph.generator;

import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.graph.model.Vertex;
import eu.diversify.trio.utility.Count;

/**
 * Create various types of graphs
 */
public class GraphFactory {

    public static GraphFactory graphFactory() {
        return new GraphFactory();
    }

    private long graphId = 0;

    public Graph fromAdjacencyMatrix(Count nodeCount, String adjacency) {
        final int edgeCount = nodeCount.value() * nodeCount.value();
        if (adjacency.length() != edgeCount) {
            throw new IllegalArgumentException("Adjacency matrix is too small (expecting " + edgeCount + " entries, but only " + adjacency.length() + " found)");
        }

        final Graph graph = emptyGraph(nodeCount);

        for (int index = 0; index < adjacency.length(); index++) {
            char edge = adjacency.charAt(index);
            if (edge == '1') {
                final Vertex source = graph.vertexWithId(index / nodeCount.value());
                final Vertex target = graph.vertexWithId(index % nodeCount.value());
                graph.connect(source, target);
            }
        }

        return graph;
    }

    public Graph emptyGraph(Count vertexCount) {
        final Graph graph = new Graph(nextGraphId());
        for (int index = 0; index < vertexCount.value(); index++) {
            graph.createVertex();
        }
        return graph;
    }

    private long nextGraphId() {
        graphId += 1;
        return graphId;
    }

    public Graph regularRingLattice(Count vertexes, Count neighborhood) {
        return new RingLatticeGenerator(vertexes, neighborhood).nextGraph();
    }

    public Graph meshedGraph(Count vertexes) {
        final Graph graph = emptyGraph(vertexes);
        for (Vertex eachSource : graph.vertexes()) {
            for (Vertex eachDestination : graph.vertexes()) {
                graph.connect(eachSource, eachDestination);
            }
        }
        return graph;
    }

}
