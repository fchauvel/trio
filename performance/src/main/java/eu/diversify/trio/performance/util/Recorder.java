
package eu.diversify.trio.performance.util;

/**
 * Record the data produced by a benchmark
 */
public interface Recorder {
    
    void record(int runIndex, Task task, Observation performance);
    
}
