package eu.diversify.trio.simulation.events;

import java.util.List;

/**
 * Listen to event occurring during simulations
 */
public interface Listener {

    /**
     * Indicate that a new simulation has been initiated
     *
     * @param simulationId the ID of the newly initiated simulation
     */
    void simulationInitiated(int simulationId);

    /**
     * Indicate that a new failure sequence is being simulated
     *
     * @param simulationId the unique ID of the simulation
     * @param sequenceId the unique ID of the sequence in the simulation
     * @param observed the unique names of the components under control in the
     * failure sequence
     * @param controlled the unique names of components under observation in the
     * failure sequence
     */
    void sequenceInitiated(int simulationId, int sequenceId, List<String> observed, List<String> controlled);

    /**
     * Indicate that the simulation of a single failure and the observed impact
     * on the other components.
     *
     * @param simulationId the unique ID of the simulation
     * @param sequenceId the unique ID of the on going failure sequence
     * @param failedComponent the unique name of the failed components
     * @param impactedComponents the unique names of all component that where
     * impacted by the failure
     */
    void failure(int simulationId, int sequenceId, String failedComponent, List<String> impactedComponents);

    /**
     * Indicate that the simulation of a failure sequence is complete.
     *
     * @param simulationId the unique ID of the simulation
     * @param sequenceId the unique ID of the completed failure sequence
     */
    void sequenceComplete(int simulationId, int sequenceId);

    /**
     * Indicate the complete of the simulation whose ID is given
     *
     * @param simulationId the ID of the simulation that just terminated
     */
    void simulationComplete(int simulationId);
}
