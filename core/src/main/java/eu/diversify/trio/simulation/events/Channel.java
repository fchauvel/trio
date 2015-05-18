package eu.diversify.trio.simulation.events;

import java.util.LinkedList;
import java.util.List;

/**
 * Dispatch event to all the registered listeners
 */
public class Channel {

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

    /**
     * Publish the given event to all the registered listeners.
     *
     * @param event the event to publish
     */
    public void publish(Event event) {
        checkEvent(event);
        for (Listener eachListener : listeners) {
            event.sendTo(eachListener);
        }
    }

    private void checkEvent(Event event) throws NullPointerException {
        if (event == null) {
            throw new NullPointerException("Cannot publish simulation event ('null' found)");
        }
    }

}
