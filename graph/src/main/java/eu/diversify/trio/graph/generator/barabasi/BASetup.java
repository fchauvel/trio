package eu.diversify.trio.graph.generator.barabasi;

import java.util.logging.Logger;
import eu.diversify.trio.graph.generator.GraphGenerator;
import static eu.diversify.trio.graph.generator.barabasi.BAGenerator.*;
import java.util.Properties;
import java.util.logging.Level;

/**
 * The configuration of the Barabasi & Albert graph generator
 */
public class BASetup implements eu.diversify.trio.graph.generator.Setup {

    private static Logger logger = Logger.getLogger(BASetup.class.getName());

    public static final String BETA_KEY = "generator.barabasi.beta";
    public static final String ALPHA_KEY = "generator.barabasi.alpha";

    public static final int DEFAULT_NODE_COUNT = 100;

    private int nodeCount;
    private double alpha;
    private double beta;

    public BASetup() {
        this(DEFAULT_NODE_COUNT, DEFAULT_ALPHA, DEFAULT_BETA);
    }

    public BASetup(int nodeCount, double alpha, double beta) {
        this.nodeCount = nodeCount;
        this.alpha = alpha;
        this.beta = beta;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    @Override
    public void updateWith(Properties properties) {
        for (String key : properties.stringPropertyNames()) {

            final String value = properties.getProperty(key);

            if (key.equals(ALPHA_KEY)) {
                alpha = extractDouble(value, key, DEFAULT_ALPHA);

            } else if (key.equals(BETA_KEY)) {
                beta = extractDouble(value, key, DEFAULT_BETA);

            }
        }
    }

    private double extractDouble(final String text, String key, double defaultValue) {
        try {
            return Double.parseDouble(text);

        } catch (NumberFormatException ex) {
            logger.log(Level.WARNING, "Unable to parse property '{0}' (found value '{1}')", new Object[]{key, text});
            return defaultValue;
        }
    }

    @Override
    public GraphGenerator instantiate() {
        return new BAGenerator(nodeCount, alpha, beta);
    }

}
