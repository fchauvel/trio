
package eu.diversify.trio.performance.util;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
    
    private final List<MicroBenchmarkListener> listeners;
    
    public EventBroker() {
        this.listeners = new LinkedList<>();
    }
    
    public void subscribe(MicroBenchmarkListener listener) {
        this.listeners.add(listener);
    }
        
    public void taskCompleted(int taskId, int totalTaskCount, boolean isWarmup) {
        for (MicroBenchmarkListener eachListener: listeners) {
            eachListener.onCompletionOfTask(taskId, totalTaskCount, isWarmup);
        }
    }
    
    public Collection<MicroBenchmarkListener> subscribers() {
        return Collections.unmodifiableCollection(listeners);
    }
    
}
