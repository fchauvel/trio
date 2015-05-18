
package eu.diversify.trio.simulation.events;

/**
 * Indicate the completion of a sequence
 */
public class SequenceComplete implements Event {

    private final int simulationId;
    private final int sequenceId;

    public SequenceComplete(int simulationId, int sequenceId) {
        this.simulationId = simulationId;
        this.sequenceId = sequenceId;
    }
           
    public void sendTo(Listener listener) {
        listener.sequenceComplete(simulationId, sequenceId);
    }
        
    
}
