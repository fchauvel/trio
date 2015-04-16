package eu.diversify.trio.performance.setup;

import eu.diversify.trio.performance.GeneratorSetupRandomizer;
import static eu.diversify.trio.performance.GeneratorSetupRandomizer.*;
import eu.diversify.trio.utility.pdf.PDF;
import eu.diversify.trio.utility.pdf.UniformDistribution;
import java.util.Properties;

/**
 * Load and Store setup object from various format
 */
public class SetupStore {

    /**
     * Load a setup serialized in the given properties table
     *
     * @param properties the properties table that contain the data
     * @return the extracted setup object
     */
    public Setup loadFromProperties(Properties properties) {
        return new Setup(
                extractInt(properties, SAMPLE_COUNT_KEY, Setup.DEFAULT_SAMPLE_COUNT),
                extractInt(properties, WARMUP_SAMPLE_COUNT_KEY, Setup.DEFAULT_WARMUP_SAMPLE_COUNT),
                extractRandomizer(properties)
        );
    }

    private int extractInt(Properties properties, String key, int defaultValue) throws IllegalArgumentException {
        int sampleCount = defaultValue;
        final String valueText = properties.getProperty(key);
        if (valueText != null) {
            try {
                sampleCount = Integer.parseInt(valueText);
            } catch (NumberFormatException nfe) {
                final String description = String.format("Key '%s' must hold an integer value (found '%s')", key, valueText);
                throw new IllegalArgumentException(description);
            }
        }
        return sampleCount;
    }

    private GeneratorSetupRandomizer extractRandomizer(Properties properties) {
        return new GeneratorSetupRandomizer(
                extractPDF(properties, ASSEMBLY_SIZE_KEY, DEFAULT_NODE_COUNT_PDF),
                extractPDF(properties, EDGE_CREATION_PROBABILITY_KEY, DEFAULT_EDGE_CREATION_PDF),
                extractPDF(properties, NEIGHBOR_COUNT_KEY, DEFAULT_NEIGHBOR_COUNT_PDF),
                extractPDF(properties, EDGE_UPDATE_PROBABILITY_KEY, DEFAULT_EDGE_CREATION_PDF),
                extractPDF(properties, ALPHA_KEY, DEFAULT_ALPHA_PDF),
                extractPDF(properties, BETA_KEY, DEFAULT_BETA_PDF)
        );
    }
    private static final String BETA_KEY = "beta";
    private static final String ALPHA_KEY = "alpha";

    private static final String EDGE_UPDATE_PROBABILITY_KEY = "edge.update.probability";
    private static final String NEIGHBOR_COUNT_KEY = "neighbor.count";
    private static final String EDGE_CREATION_PROBABILITY_KEY = "edge.creation.probability";
    private static final String ASSEMBLY_SIZE_KEY = "assembly.size";

    private PDF extractPDF(Properties properties, String key, PDF defaultValue) throws IllegalArgumentException {
        if (!properties.containsKey(maximumOf(key))
                || !properties.containsKey(minimumOf(key))) {
            return defaultValue;
        }

        double min = extractDouble(properties, minimumOf(key), 0);
        double max = extractDouble(properties, maximumOf(key), 1);

        return new UniformDistribution(min, max);
    }

    private String maximumOf(String key) {
        return key + MAXIMUM_MARK;
    }

    private String minimumOf(String key) {
        return key + MINIMUM_MARK;
    }

    private double extractDouble(Properties properties, String key, double defaultValue) throws IllegalArgumentException {
        double sampleCount = defaultValue;
        final String valueText = properties.getProperty(key);
        if (valueText != null) {
            try {
                sampleCount = Double.parseDouble(valueText);
            } catch (NumberFormatException nfe) {
                final String description = String.format("Key '%s' must hold an double value (found '%s')", key, valueText);
                throw new IllegalArgumentException(description);
            }
        }
        return sampleCount;
    }

    private static final String SAMPLE_COUNT_KEY = "sample.count";
    private static final String WARMUP_SAMPLE_COUNT_KEY = "sample.warmup.count";
    private static final String MINIMUM_MARK = ".minimum";
    private static final String MAXIMUM_MARK = ".maximum";
}
