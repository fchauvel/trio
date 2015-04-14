package eu.diversify.trio.graph.generator.ringLattices;

import eu.diversify.trio.graph.generator.GraphGenerator;
import eu.diversify.trio.graph.generator.Setup;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Configuration of the ring lattice generator
 */
public class RingLatticeSetup implements Setup {

    private static final Logger logger = Logger.getLogger(RingLatticeSetup.class.getName());

    public static final String NEIGHBORHOOD_SIZE_KEY = "generator.ring.lattices.neighborhood";
    public static final String NODE_COUNT_KEY = "generator.ring.lattices.size";

    private static final int DEFAULT_NEIGHBORHOOD_SIZE = 10;
    private static final int DEFAULT_NODE_COUNT = 100;

    private int nodeCount;
    private int neighborhoodSize;

    public RingLatticeSetup() {
        this(DEFAULT_NODE_COUNT, DEFAULT_NEIGHBORHOOD_SIZE);
    }

    public RingLatticeSetup(int nodeCount, int neighborhoodSize) {
        this.nodeCount = nodeCount;
        this.neighborhoodSize = neighborhoodSize;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
    }

    public int getNeighborhoodSize() {
        return neighborhoodSize;
    }

    public void setNeighborhoodSize(int neighborhoodSize) {
        this.neighborhoodSize = neighborhoodSize;
    }

    @Override
    public void updateWith(Properties properties) {
        for (String key : properties.stringPropertyNames()) {

            final String value = properties.getProperty(key);

            if (key.equals(NODE_COUNT_KEY)) {
                nodeCount = extractInteger(value, key, DEFAULT_NODE_COUNT);

            } else if (key.equals(NEIGHBORHOOD_SIZE_KEY)) {
                neighborhoodSize = extractInteger(value, key, DEFAULT_NEIGHBORHOOD_SIZE);

            }
        }
    }

    private int extractInteger(final String text, String key, int defaultValue) {
        try {
            return Integer.parseInt(text);

        } catch (NumberFormatException ex) {
            logger.log(Level.WARNING, "Unable to parse property '{0}' (found value '{1}')", new Object[]{key, text});
            return defaultValue;

        }
    }

    @Override
    public GraphGenerator instantiate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
