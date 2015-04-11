package eu.diversify.trio.graph.generator;

import eu.diversify.trio.graph.AdjacencyMatrix;
import eu.diversify.trio.graph.Graph;
import eu.diversify.trio.graph.Node;
import java.util.BitSet;

/**
 * Generate a random graph based on a model 
 */
public class GraphGenerator {

    private final GraphFamily family;

    public GraphGenerator(GraphFamily neighborhood) {
        this.family = neighborhood;
    }

    public Graph nextRandomGraph() {
        final BitSet adjacency = new BitSet(family.edgeCount());
        for (Node eachSource: family.nodes()) {
            for (Node eachTarget: family.nodes()) {
                if (family.allowsEdgeBetween(eachSource, eachTarget)) {
                    adjacency.set(edgeIndex(eachSource, eachTarget));
                }
            }
        }
        return new AdjacencyMatrix(family.nodeCount(), adjacency);
    }

    private int edgeIndex(Node source, Node target) {
        return source.index() * family.nodeCount() + target.index();
    }

}
