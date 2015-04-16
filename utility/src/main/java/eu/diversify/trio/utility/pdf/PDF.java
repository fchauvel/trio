package eu.diversify.trio.utility.pdf;

/**
 * Probability Distribution function
 */
public interface PDF {

    /**
     * @return a single value sampled according to this probability distribution
     * function.
     */
    double sample();
}
