package eu.diversify.trio.simulation.events;

import java.util.LinkedList;
import java.util.List;

/**
 * Dispatch event to all the registered listeners
 */
public class Channel implements Listener {

    private final List<Listener> listeners;

    public Channel() {
        this.listeners = new LinkedList<Listener>();
    }

    /**
     * Register the given listener so that it will be notified of any simulation
     * events that occurs.
     *
     * @param listener the listener object to be registered
     */
    public void subscribe(Listener listener) {
        checkListener(listener);
        listeners.add(listener);
    }

    private void checkListener(Listener listener) throws NullPointerException {
        if (listener == null) {
            throw new NullPointerException("Invalid simulation listener ('null' found)");
        }
    }

    public void simulationInitiated(int simulationId) {
        for (Listener eachListener: listeners) {
            eachListener.simulationInitiated(simulationId);
        }
    }

    public void sequenceInitiated(int simulationId, int sequenceId, List<String> observed, List<String> controlled) {
        for(Listener eachListener: listeners) {
            eachListener.sequenceInitiated(simulationId, sequenceId, observed, controlled);
        }
    }

    public void failure(int simulationId, int sequenceId, String failedComponent, List<String> impactedComponents) {
        for(Listener eachListener: listeners) {
            eachListener.failure(simulationId, sequenceId, failedComponent, impactedComponents);
        }
    }

    public void sequenceComplete(int simulationId, int sequenceId) {
        for(Listener eachListener: listeners) {
            eachListener.sequenceComplete(simulationId, sequenceId);
        }
    }

    public void simulationComplete(int simulationId) {
        for(Listener eachListener: listeners) {
            eachListener.simulationComplete(simulationId);
        }
    }

}
