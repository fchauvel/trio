
package eu.diversify.trio.performance.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * A simple event broker that permits the UI to follow the progress of the benchmark
 */
public class EventBroker {
    
    private static EventBroker singleInstance;
    
    public static EventBroker instance() {
        if (singleInstance == null) {
            singleInstance = new EventBroker();
        }
        return singleInstance;
    }
    
    private final Map<Integer, List<Listener>> listeners;
    
    public EventBroker() {
        this.listeners = new HashMap<>();
    }
    
    public void subscribe(int benchmarkId, Listener listener) {
        List<Listener> ls = listeners.get(benchmarkId);
        if (ls == null) {
            ls = new LinkedList<>();
            listeners.put(benchmarkId, ls);
        }
        ls.add(listener);
    }
    
    public void taskCompleted(int benchmarkId, Properties taskProperties) {
        final List<Listener> listeners = this.listeners.get(benchmarkId);
        if (listeners != null) {
           for (Listener eachListener: listeners) {
                eachListener.onTaskCompleted(taskProperties); 
            }
        }
    }
    
    public Collection<Listener> subscribers() {
        final List<Listener> allListeners = new ArrayList<>();
        for (List<Listener> ofEachBenchmark: listeners.values()) {
            allListeners.addAll(ofEachBenchmark);
        }
        return Collections.unmodifiableCollection(allListeners);
    }
    
}
