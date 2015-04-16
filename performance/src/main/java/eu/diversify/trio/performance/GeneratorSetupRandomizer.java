package eu.diversify.trio.performance;

import eu.diversify.trio.generator.Setup;
import eu.diversify.trio.utility.Count;
import eu.diversify.trio.utility.pdf.PDF;
import eu.diversify.trio.utility.pdf.UniformDistribution;

/**
 * Generate new generator setups based on probability distributions
 * characterizing the different parameters
 */
public class GeneratorSetupRandomizer {

    public static final PDF DEFAULT_NODE_COUNT_PDF = new UniformDistribution(10, 100);

    private final PDF nodeCount;
    
    public GeneratorSetupRandomizer() {
        this(DEFAULT_NODE_COUNT_PDF);
    }
    
    public GeneratorSetupRandomizer(PDF nodeCount) {
        this.nodeCount = nodeCount;
    }

    public Setup next() {
        return new Setup(
                nodeCount(), 
                Setup.DEFAULT_EDGE_CREATION, 
                Setup.DEFAULT_NEIGHBOR_COUNT, 
                Setup.DEFAULT_EDGE_UPDATE, 
                Setup.DEFAULT_ALPHA, 
                Setup.DEFAULT_BETA
        ); 
    }

    private Count nodeCount() {
        return new Count((int) nodeCount.sample());
    }

}
