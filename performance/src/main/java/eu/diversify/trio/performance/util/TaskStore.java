
package eu.diversify.trio.performance.util;

/**
 * The task store, which can fetch (or create) task given their id
 */
public interface TaskStore {
    
    /**
     * @param id the identity of the task of interest
     * @return return the task of given id
     */
    Task fetch(int id);
    
}
