/*
 */

package eu.diversify.trio;

/**
 *
 */
public interface Requirement {

    Requirement and(Requirement right);
    
    Requirement or(Requirement right);
    
    Requirement not();
    
    boolean isSatisfiedBy(Topology topology);
    
}
