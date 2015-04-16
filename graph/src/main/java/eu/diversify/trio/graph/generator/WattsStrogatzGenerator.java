package eu.diversify.trio.graph.generator;

import eu.diversify.trio.graph.Edge;
import eu.diversify.trio.graph.Graph;
import eu.diversify.trio.graph.Node;
import eu.diversify.trio.utility.Count;
import eu.diversify.trio.utility.Probability;
import static eu.diversify.trio.graph.Node.node;
import java.util.ArrayList;
import java.util.Random;

/**
 * The small world networks, following the model proposed by Strogatz and Watts.
 */
public class WattsStrogatzGenerator implements GraphGenerator {

    private final Random random;
    private final Count nodeCount;
    private final Count neighborCount;
    private final Probability relinking;

    public WattsStrogatzGenerator(Count nodeCount, Count neighborCount, Probability relinking) {
        this(new Random(), nodeCount, neighborCount, relinking);
    } 
    
    public WattsStrogatzGenerator(Random random, Count nodeCount, Count neighborCount, Probability relinking) {
        this.random = random;
        this.nodeCount = nodeCount;
        this.neighborCount = neighborCount;
        this.relinking = relinking;
    }

    @Override
    public Graph nextGraph() {
        final Graph ringLattice = initialRingLattice();
        for (Edge eachEdge : new ArrayList<>(ringLattice.edges())) {
            if (isRelinked()) {
                ringLattice.disconnect(eachEdge);
                ringLattice.connect(eachEdge.source(), newTarget(ringLattice, eachEdge));
            }
        }
        return ringLattice;
    }

    private Graph initialRingLattice() {
        return new RingLatticeGenerator(nodeCount, neighborCount).nextGraph();
    }

    private Node newTarget(Graph graph, Edge edge) {
        int newTarget = random.nextInt(graph.nodes().size() - 1);
        if (newTarget == edge.source().index()) {
            newTarget++;
        }
        return node(newTarget);
    }

    private boolean isRelinked() {
        double draw = random.nextDouble();
        return draw < relinking.value();
    }

}
