
package eu.diversify.trio.analytics.sensitivity;

/**
 * Captures a single entry in the ranking (the component and
 * its sensitivity)
 */
public class Sensitivity implements Comparable<Sensitivity> {
 
    private final String component;
    private int totalImpact;
    private int failureCount;
 
    public Sensitivity(String component) {
        this.component = component;
        this.totalImpact = 0;
        this.failureCount = 0;
    }

    public double averageImpact() {
        return (double) totalImpact / failureCount;
    }

    public void recordFailure(int impact) {
        assert impact >= 0 : "A failure impact shall be positive or null (found " + impact + ")";
        this.totalImpact += impact;
        this.failureCount += 1;
    }

    @Override
    public String toString() {
        return String.format("%s : %5.2e [%d / %d]", component, averageImpact(), totalImpact, failureCount);
    }

    public int compareTo(Sensitivity o) {
        return -1 * Double.compare(averageImpact(), o.averageImpact());
    }
    
}
