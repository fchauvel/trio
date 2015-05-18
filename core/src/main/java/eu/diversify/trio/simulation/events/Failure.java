
package eu.diversify.trio.simulation.events;

import java.util.List;

/**
 * Indicate the failure of a given component the observed impact
 */
public class Failure implements Event {

    private final int simulationId;
    private final int sequenceId;
    private final String failedComponent;
    private final List<String> impactedComponents;

    public Failure(int simulationId, int sequenceId, String failedComponent, List<String> impactedComponents) {
        this.simulationId = simulationId;
        this.sequenceId = sequenceId;
        this.failedComponent = failedComponent;
        this.impactedComponents = impactedComponents;
    }
    
    
    
    public void sendTo(Listener listener) {
        listener.failure(simulationId, sequenceId, failedComponent, impactedComponents);
    }
    
}
