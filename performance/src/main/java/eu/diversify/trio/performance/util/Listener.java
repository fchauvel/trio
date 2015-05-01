
package eu.diversify.trio.performance.util;

/**
 * Interface to react to progress of the micro benchmark
 */
public interface Listener {
    
    void onCompletionOfTask(int taskId, int totalTaskCount);
    
}
