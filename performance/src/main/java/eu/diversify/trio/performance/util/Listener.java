package eu.diversify.trio.performance.util;

import java.util.Properties;

/**
 * Interface to react to progress of the micro benchmark
 */
public interface Listener {

    /**
     * Declare a task as complete. All the possible information about the task
     * should be contained in the attached properties.
     *
     * @param properties describes the properties of the completed task.
     */
    void onTaskCompleted(Properties properties);

}
