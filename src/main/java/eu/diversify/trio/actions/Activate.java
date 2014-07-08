/*
 */
package eu.diversify.trio.actions;

import eu.diversify.trio.Topology;

/**
 * Activate a given component
 */
public class Activate extends AbstractAction {

    private final String target;

    public Activate(String target) {
        this.target = target;
    }

    public Topology executeOn(Topology topology) {
        topology.activate(target); 
        return topology;
    }

    @Override
    public String toString() {
        return String.format("activate %s", target);
    }

}
