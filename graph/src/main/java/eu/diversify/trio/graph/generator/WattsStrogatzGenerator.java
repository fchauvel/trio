package eu.diversify.trio.graph.generator;

import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.utility.Count;
import eu.diversify.trio.utility.Probability;
import static eu.diversify.trio.graph.generator.GraphFactory.graphFactory;
import eu.diversify.trio.graph.model.Edge;
import eu.diversify.trio.graph.model.Vertex;
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
        final Graph ringLattice = graphFactory().regularRingLattice(nodeCount, neighborCount);
        for (Edge eachEdge : ringLattice.edges()) {
            if (isRelinked()) {
                ringLattice.disconnect(eachEdge);
                ringLattice.connect(eachEdge.source(), newTarget(ringLattice, eachEdge));
            }
        }
        return ringLattice;
    }

 
    private Vertex newTarget(Graph graph, Edge edge) {
        int newTarget = random.nextInt(graph.vertexCount() - 1);
        if (newTarget == edge.source().id()) {
            newTarget++;
        }
        return graph.vertexWithId(newTarget);
    }

    private boolean isRelinked() {
        double draw = random.nextDouble();
        return draw < relinking.value();
    }

}
