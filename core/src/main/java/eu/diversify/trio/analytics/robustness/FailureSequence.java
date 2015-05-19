package eu.diversify.trio.analytics.robustness;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Aggregate events from the simulation into a meaningful failure sequences
 */
public class FailureSequence {

    private final int id;
    private final int min; 
    private final int max;
    private int alive;
    private int robustness;
    private final List<String> sequence;

    public FailureSequence(int id, int observed, int controlled) {
        this.id = id;
        min = observed;
        max = observed * controlled;
        alive = observed;
        sequence = new ArrayList<String>(controlled);
    }

    void record(String failedComponent, int impact) {
        assert !sequence.contains(failedComponent) : "Component '" + failedComponent + "' has already been failed";
        
        sequence.add(failedComponent);
        alive -= impact;
        robustness += alive;
    }
    
    public int id() {
        return id;
    }

    /**
     * @return the list of components that failed in this sequence, in th order
     * where they failed.
     */
    public List<String> sequence() {
        return Collections.unmodifiableList(sequence);
    }

    public int robustness() {
        return robustness;
    }

    public double normalizedRobustness() {
        return (double) (robustness - min) / (max - min);
    }

}
