
package eu.diversify.trio;

/**
 *
 *
 */
public interface Execution {
  
    boolean hasNext(Topology topology);
    
    Action nextAction(Topology topology);
    
}
