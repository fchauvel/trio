

package eu.diversify.trio;

/**
 * Metric as a general function, computed on a trace
 */
public interface Metric {
    
    String getName();
        
    double computeOn(Trace input);
    
}
