package eu.diversify.trio.graph.util;

/**
 * A simple value that represents a probability
 */
public class Probability {

    private final double probability;

    public Probability(double probability) {
        this.probability = validate(probability);
    }

    private double validate(double probability) {
        if (probability < 0D || probability > 1D) {
            final String description
                    = String.format(
                            "A probability must be within [0, 1] (found %.2f)",
                            probability
                    );
            throw new IllegalArgumentException(description);
        }
        return probability;
    }

    public double value() {
        return this.probability;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.probability) ^ (Double.doubleToLongBits(this.probability) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Probability other = (Probability) obj;
        if (Double.doubleToLongBits(this.probability) != Double.doubleToLongBits(other.probability)) {
            return false;
        }
        return true;
    }

    
    
}
