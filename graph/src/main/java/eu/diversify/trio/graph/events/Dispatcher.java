
package eu.diversify.trio.graph.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dispatch event sent by graphs
 */
public class Dispatcher {
    
   
    private final Map<Long, List<Subscriber>> listeners; 

    public Dispatcher() {
        this.listeners = new HashMap<>();
    }
    
    public void subscribe(long graphId, Subscriber listener) {
        List<Subscriber> listeners = this.listeners.get(graphId);
        if (listeners == null) {
            listeners = new ArrayList<>();
            this.listeners.put(graphId, listeners);
        } 
        listeners.add(listener);
    }
    
    
    public void publish(Event event) {
        List<Subscriber> listeners = this.listeners.get(event.getGraphId());
        if (listeners != null) {
            for (Subscriber eachListener: listeners) {
                event.sendTo(eachListener);
            }
        }
    }
    
}
