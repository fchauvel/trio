package eu.diversify.trio.performance.util;

/**
 * Build task, to be executed during the micro benchmark
 */
public interface TaskFactory {
    
    /**
     * @return a new task, ready to be executed
     */
    Task prepareNewTask();

    /**
     * reset the factory so that it can produce more instance
     */
    void reset();
    
}
