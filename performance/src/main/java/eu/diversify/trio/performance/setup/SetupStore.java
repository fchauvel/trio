package eu.diversify.trio.performance.setup;

import eu.diversify.trio.performance.GraphStore;
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
                extractInt(properties, FIRST_GRAPH_ID_KEY, Setup.DEFAULT_FIRST_GRAPH_ID),
                extractInt(properties, LAST_GRAPH_ID_KEY, Setup.DEFAULT_LAST_GRAPH_ID),
                extractDouble(properties, WARMUP_RATIO_KEY, Setup.DEFAULT_WARMUP_RATIO),
                extractString(properties, GRAPH_DIRECTORY_KEY, GraphStore.DEFAULT_GRAPH_PATH),
                extractString(properties, GRAPH_FILE_PATTERN_KEY, GraphStore.DEFAULT_GRAPH_FILE_PATTERN)
        );
    }

    private String extractString(Properties properties, String key, String defaultValue) throws IllegalArgumentException {
        String value = properties.getProperty(key);
        if (value == null) {
            value = defaultValue;
        }
        return value;
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

    private static final String WARMUP_RATIO_KEY = "warmup.ratio";
    private static final String GRAPH_FILE_PATTERN_KEY = "graph.file.pattern";
    private static final String GRAPH_DIRECTORY_KEY = "graph.directory";
    private static final String LAST_GRAPH_ID_KEY = "last.graph.id";
    private static final String FIRST_GRAPH_ID_KEY = "first.graph.id";
}
