package eu.diversify.trio.simulation.events;

/**
 * Any simulation event
 */
public interface Event {

    /**
     * Forward this event to the given listener
     *
     * @param listener the destination listener;
     */
    void sendTo(Listener listener);

}
