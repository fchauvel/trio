package eu.diversify.trio.generator;

import eu.diversify.trio.graph.generator.BarabasiAlbertGenerator;
import eu.diversify.trio.graph.generator.ErdosRenyiGenerator;
import eu.diversify.trio.graph.generator.GraphGenerator;
import eu.diversify.trio.graph.generator.WattsStrogatzGenerator;
import eu.diversify.trio.utility.Count;
import eu.diversify.trio.utility.Probability;

/**
 * Configuration of the assembly generator
 */
public class Setup {

    public static final Probability DEFAULT_BETA = new Probability(1D / 3);
    public static final Probability DEFAULT_ALPHA = new Probability(1D / 3);
    public static final Probability DEFAULT_EDGE_UPDATE = new Probability(0.05);
    public static final Count DEFAULT_NEIGHBOR_COUNT = new Count(6);
    public static final Probability DEFAULT_EDGE_CREATION = new Probability(0.05);
    public static final Count DEFAULT_NODE_COUNT = new Count(100);

    private final Count nodeCount;
    private final Probability edgeCreation;
    private final Count neighborCount;
    private final Probability edgeUpdate;
    private final Probability alpha;
    private final Probability beta;

    public Setup() {
        this(DEFAULT_NODE_COUNT, DEFAULT_EDGE_CREATION, DEFAULT_NEIGHBOR_COUNT, DEFAULT_EDGE_UPDATE, DEFAULT_ALPHA, DEFAULT_BETA);
    }

    public Setup(Count nodeCount, Probability egdeCreation, Count neighborCount, Probability edgeUpdate, Probability alpha, Probability beta) {
        this.nodeCount = nodeCount;
        this.edgeCreation = egdeCreation;
        this.neighborCount = neighborCount;
        this.edgeUpdate = edgeUpdate;
        this.alpha = alpha;
        this.beta = beta;
    }
    

    public GraphGenerator generatorFor(AssemblyKind kind) {
        switch (kind) {
            case RANDOM:
                return new ErdosRenyiGenerator(nodeCount(), edgeCreation());
            case SMALL_WORLD:
                return new WattsStrogatzGenerator(nodeCount(), neighborCount(), edgeUpdate());
            case SCALE_FREE:
                return new BarabasiAlbertGenerator(nodeCount(), alpha(), beta());
        }

        final String description = String.format("Unknown graph family '%s'", kind.name());
        throw new RuntimeException(description);
    }

    private Count neighborCount() {
        return neighborCount;
    }

    private Probability beta() {
        return beta;
    }

    private Probability alpha() {
        return alpha;
    }

    private Probability edgeUpdate() {
        return edgeUpdate;
    }

    private Probability edgeCreation() {
        return edgeCreation;
    }

    private Count nodeCount() {
        return nodeCount;
    }

}
