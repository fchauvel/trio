package eu.diversify.trio.performance;

import eu.diversify.trio.generator.Setup;
import eu.diversify.trio.utility.Count;
import eu.diversify.trio.utility.Probability;
import eu.diversify.trio.utility.pdf.PDF;
import eu.diversify.trio.utility.pdf.UniformDistribution;

/**
 * Generate new generator setups based on probability distributions
 * characterizing the different parameters
 */
public class GeneratorSetupRandomizer {

    public static final PDF DEFAULT_NODE_COUNT_PDF = new UniformDistribution(10, 100);
    public static final PDF DEFAULT_EDGE_CREATION_PDF = new UniformDistribution(0, 1);
    public static final PDF DEFAULT_NEIGHBOR_COUNT_PDF = new UniformDistribution(0, 1);
    public static final PDF DEFAULT_EDGE_UPDATE_PDF = new UniformDistribution(0, 1);
    public static final PDF DEFAULT_ALPHA_PDF = new UniformDistribution(0, 1);
    public static final PDF DEFAULT_BETA_PDF = new UniformDistribution(0, 1);

    private final PDF nodeCount;
    private final PDF edgeCreation;
    private final PDF neighborCount;
    private final PDF edgeUpdate;
    private final PDF alpha;
    private final PDF beta;

    public GeneratorSetupRandomizer() {
        this(
                DEFAULT_NODE_COUNT_PDF,
                DEFAULT_EDGE_CREATION_PDF,
                DEFAULT_NEIGHBOR_COUNT_PDF,
                DEFAULT_EDGE_UPDATE_PDF,
                DEFAULT_ALPHA_PDF,
                DEFAULT_BETA_PDF);
    }

    public GeneratorSetupRandomizer(PDF nodeCount, PDF edgeCreation, PDF neighborCount, PDF edgeUpdate, PDF alpha, PDF beta) {
        this.nodeCount = nodeCount;
        this.edgeCreation = edgeCreation;
        this.neighborCount = neighborCount;
        this.edgeUpdate = edgeUpdate;
        this.alpha = alpha;
        this.beta = beta;
    }

    public Setup next() {
        final Count chosenNodeCount = nodeCount();
        return new Setup(
                chosenNodeCount,
                edgeCreation(),
                neighborCount(chosenNodeCount),
                edgeUpdate(),
                alpha(), beta());
    }

    private Probability beta() {
        return new Probability(beta.sample());
    }

    private Probability alpha() {
        return new Probability(alpha.sample());
    }

    private Probability edgeUpdate() {
        return new Probability(edgeUpdate.sample());
    }

    private Count neighborCount(final Count chosenNodeCount) {
        return new Count( (2 *  (int) ((chosenNodeCount.value() / 2) * neighborCount.sample())));
    }

    private Probability edgeCreation() {
        return new Probability(edgeCreation.sample());
    }

    private Count nodeCount() {
        return new Count((int) nodeCount.sample());
    }

}
