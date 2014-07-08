

package eu.diversify.trio;

/**
 * Action which can be performed on topologies
 */
public interface Action {

    /**
     * Execute this action on the given topology
     *
     * @param topology the topology on which this action shall be performed
     * @return the topology once the action is performed
     */
    Topology executeOn(Topology topology);

    
    /**
     * Compare this action with another object
     * @param object the object to compare with
     * @return  true if the given object is equivalent to this action
     */
    @Override
    boolean equals(Object object);
    
}
