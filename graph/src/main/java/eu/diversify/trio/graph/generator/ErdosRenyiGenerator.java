package eu.diversify.trio.graph.generator;

import eu.diversify.trio.graph.AdjacencyMatrix;
import eu.diversify.trio.graph.Graph;
import eu.diversify.trio.graph.Node;
import java.util.Random;

/**
 * The Erdos-Renyi G(n, p) generator of random graphs
 */
public class ErdosRenyiGenerator  implements GraphGenerator {

    private final Random random;
    private final int nodeCount;
    private final double edgeProbability;

    public ErdosRenyiGenerator(int nodeCount, double edgeProbability) {
        this(new Random(), nodeCount, edgeProbability);
    }

    public ErdosRenyiGenerator(Random random, int nodeCount, double edgeProbability) {
        this.random = random;
        this.nodeCount = nodeCount;
        this.edgeProbability = edgeProbability;
    }

    @Override
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
        double draw = random.nextDouble();
        return (draw < edgeProbability);
    }


}
