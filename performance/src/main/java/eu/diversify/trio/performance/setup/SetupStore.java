package eu.diversify.trio.performance.setup;

import eu.diversify.trio.performance.GeneratorSetupRandomizer;
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
                new UniformDistribution(
                        extractDouble(properties, ASSEMBLY_SIZE_MINIMUM_KEY, 10),
                        extractDouble(properties, ASSEMBLY_SIZE_MAXIMUM_KEY, 100))
        );
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
    private static final String ASSEMBLY_SIZE_MAXIMUM_KEY = "assembly.size.maximum";
    private static final String ASSEMBLY_SIZE_MINIMUM_KEY = "assembly.size.minimum";

}
