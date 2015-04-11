package eu.diversify.trio.graph.generator;

import eu.diversify.trio.graph.AdjacencyMatrix;
import eu.diversify.trio.graph.EdgeSet;
import eu.diversify.trio.graph.Graph;
import eu.diversify.trio.graph.Node;
import eu.diversify.trio.graph.NodeSet;
import java.util.BitSet;
import java.util.Random;

/**
 * The Erdos-Renyi G(n, p) generator of random graphs
 */
public class ErdosRenyiModel implements GraphFamily {

    private final Random random;
    private final int nodeCount;
    private final double edgeProbability;

    public ErdosRenyiModel(int nodeCount, double edgeProbability) {
        this(new Random(), nodeCount, edgeProbability);
    }

    public ErdosRenyiModel(Random random, int nodeCount, double edgeProbability) {
        this.random = random;
        this.nodeCount = nodeCount;
        this.edgeProbability = edgeProbability;
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
        double draw = random.nextDouble();
        return (draw < edgeProbability);
    }

    @Override
    public NodeSet nodes() {
        final NodeSet nodes = new NodeSet();
        for (int index = 0; index < nodeCount; index++) {
            nodes.add(new Node(index));
        }
        return nodes;
    }

    @Override
    public EdgeSet edges() {
        throw new UnsupportedOperationException("Not supported in graph family");
    }

}
