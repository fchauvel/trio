package eu.diversify.trio.graph.generator;

import eu.diversify.trio.graph.AdjacencyMatrix;
import eu.diversify.trio.graph.Graph;
import java.util.BitSet;
import java.util.Random;

/**
 * The Erdos-Renyi G(n, p) generator of random graphs
 */
public class ErdosRenyiGenerator implements RandomGraphGenerator {

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
    public Graph nextRandomGraph() {
        final int edgeCount = nodeCount * nodeCount;
        final BitSet adjacency = new BitSet(edgeCount);
        for(int eachEdge=0; eachEdge < edgeCount ; eachEdge++ ) {
            double draw = random.nextDouble();
            if (draw < edgeProbability) {
                adjacency.set(eachEdge);
            }
        }
        return new AdjacencyMatrix(adjacency);
    }
    
    
    
}
