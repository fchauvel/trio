package eu.diversify.trio.graph.generator;

import eu.diversify.trio.graph.Edge;
import eu.diversify.trio.graph.Graph;
import static eu.diversify.trio.graph.Node.node;
import java.util.Random;

/**
 * The small world networks, following the model proposed by Strogatz and Watts.
 */
public class WattsStrogatzGenerator implements GraphGenerator {

    private final RingLatticeGenerator ringLattice;
    private final double relinkingProbability;

    public WattsStrogatzGenerator(int nodeCount, int diameter, double relinkingProbability) {
        random = new Random();
        validateProbability(relinkingProbability);
        
        ringLattice = new RingLatticeGenerator(nodeCount, diameter);
        this.relinkingProbability = relinkingProbability;
    }

    private void validateProbability(double relinkingProbability1) throws IllegalArgumentException {
        if (relinkingProbability1 < 0D || relinkingProbability1 > 1D) {
            final String description = String.format("Invalid probability, must be within [0, 1] (found %.2f)", relinkingProbability1);
            throw new IllegalArgumentException(description);
        }
    }

    @Override
    public Graph nextGraph() {
        Graph graph = ringLattice.nextGraph();
        for(Edge eachEdge: graph.edges()) {
            double draw = random.nextDouble();
            if (draw < relinkingProbability) {
                graph.disconnect(eachEdge.source(), eachEdge.target());
                int newTarget = random.nextInt(graph.nodes().size());
                graph.connect(eachEdge.source(), node(newTarget));
            }
        }
        return graph;
    }
    
    private final Random random;

}
