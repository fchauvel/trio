
package eu.diversify.trio.simulation.events;

import java.util.List;

/**
 * Indicate that a new failure sequence is being simulated.
 */
public class SequenceInitiated implements Event {
    
    private final int simulationId;
    private final int sequenceId;
    private final List<String> controlled;
    private final List<String> observed;

    public SequenceInitiated(int simulationId, int sequenceId, List<String> observed, List<String> controlled) {
        this.simulationId = simulationId;
        this.sequenceId = sequenceId;
        this.controlled = controlled;
        this.observed = observed;
    }

    public void sendTo(Listener listener) {
        listener.sequenceInititated(simulationId, sequenceId, observed, controlled);
    }
    
    
    
}
