/*
 */
package eu.diversify.trio.requirements;

import eu.diversify.trio.Topology;

/**
 * Require that another component exists
 */
public class Require extends AbstractRequirement {

    private final String requiredComponent;

    public Require(String requiredComponent) {
        this.requiredComponent = requiredComponent;
    }

    public boolean isSatisfiedBy(Topology topology) {
        return topology.isActive(requiredComponent);
    }
    
        @Override
    public String toString() {
        return String.format("%s", requiredComponent);
    }

    public static Require require(String component) {
        return new Require(component);
    }
}
