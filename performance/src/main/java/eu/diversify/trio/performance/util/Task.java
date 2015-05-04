package eu.diversify.trio.performance.util;

import java.util.Properties;

/**
 * A task whose execution time is measured by a micro benchmark
 */
public interface Task {

    /**
     * @return the unique ID of this task
     */
    int id();

    /**
     * @return a map containing the properties that characterize this task
     */
    Properties properties();

    /**
     * Execute the task
     */
    void execute();

}
