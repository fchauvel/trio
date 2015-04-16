
package eu.diversify.trio.utility.pdf;

import java.util.Random;

/**
 * The uniform distribution
 */
public class UniformDistribution implements PDF {
    
    private final Random random;
    private final double min;
    private final double max;
    
    public UniformDistribution(double min, double max) {
        this.random = new Random();
        this.min = min;
        this.max = max;
    }

    @Override
    public double sample() {
        return min + random.nextDouble() * (max - min);
    }
    
}
