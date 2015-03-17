
package eu.diversify.trio.performance.util;

import java.util.Map;

/**
 * A task whose execution time is measured by a micro benchmark
 */
public interface Task {
    
    
    /**
     * @return a map containing the properties that characterize this task
     */
    Map<String, Object> getProperties();
    
    /**
     * Execute the task
     */
    void execute();
    
}
