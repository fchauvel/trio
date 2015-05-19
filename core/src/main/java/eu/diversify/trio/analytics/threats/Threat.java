
package eu.diversify.trio.analytics.threats;

/**
 * The threat associated with a given failure sequence
 */
public class Threat implements Comparable<Threat> {
    private final String failureSequence;
    private final double robustness;
    private int occurrence;
    private int total;

    public Threat(String description, double robustness) {
        this.failureSequence = description;
        this.robustness = robustness;
    }

    void newInstance() {
        occurrence++;
    }

    void setTotal(int total) {
        assert total >= occurrence : "Total must be above " + occurrence + " (found " + total + ")";
        this.total = total;
    }
    
    public String failureSequence() {
        return failureSequence;
    }

    public double probability() {
        return (double) occurrence / total;
    }

    public double threatLevel() {
        return probability() * (1D - robustness);
    }

    public int compareTo(Threat o) {
        return -1 * Double.compare(threatLevel(), o.threatLevel());
    }
    
}
