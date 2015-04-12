package eu.diversify.trio.graph.generator;

import eu.diversify.trio.graph.Edge;
import eu.diversify.trio.graph.Graph;
import eu.diversify.trio.graph.Node;
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

    private void validateProbability(double probability) throws IllegalArgumentException {
        if (probability < 0D || probability > 1D) {
            final String description = String.format("Invalid probability, must be within [0, 1] (found %.2f)", probability);
            throw new IllegalArgumentException(description);
        }
    }

    @Override
    public Graph nextGraph() {
        Graph graph = ringLattice.nextGraph();
        for (Edge eachEdge : graph.edges()) {
            if (isRandomlyRelinked()) {
                graph.disconnect(eachEdge.source(), eachEdge.target());
                graph.connect(eachEdge.source(), newTarget(graph, eachEdge));
            }
        }
        return graph;
    }

    private Node newTarget(Graph graph, Edge edge) {
        int newTarget = random.nextInt(graph.nodes().size()-1);
        if (newTarget == edge.source().index()) {
            newTarget++;
        }
        return node(newTarget);
    }

    private boolean isRandomlyRelinked() {
        double draw = random.nextDouble();
        return draw < relinkingProbability;
    }

    private final Random random;

}
