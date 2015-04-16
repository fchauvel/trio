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

    public Setup() {
    }
 
    
    public GraphGenerator generatorFor(AssemblyKind kind) {
        switch (kind) {
            case RANDOM:
                return new ErdosRenyiGenerator(randomNodeCount(), randomEdgeProbability());
            case SMALL_WORLD:
                return new WattsStrogatzGenerator(randomNodeCount(), new Count(6), randomRelinkingProbability());
            case SCALE_FREE:
                return new BarabasiAlbertGenerator(randomNodeCount(), randomAlpha(), randomBeta());
        }
        
        final String description = String.format("Unknown graph family '%s'", kind.name());
        throw new RuntimeException(description);
    }

    private static Probability randomBeta() {
        return new Probability(1D / 3);
    }

    private static Probability randomAlpha() {
        return new Probability(1D / 3);
    }

    private static Probability randomRelinkingProbability() {
        return new Probability(0.05);
    }

    private static Probability randomEdgeProbability() {
        return randomRelinkingProbability();
    }

    private static Count randomNodeCount() {
        return new Count(100);
    }

}
