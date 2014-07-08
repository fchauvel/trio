/*
 */
package eu.diversify.trio.actions;

import eu.diversify.trio.Topology;

/**
 * Inactivate the selected component
 */
public class Inactivate extends AbstractAction {

    private final String target;

    public Inactivate(String target) {
        this.target = target;
    }

    public Topology executeOn(Topology topology) {
        topology.inactivate(target);
        return topology;
    }
    
    @Override
    public String toString() {
        return String.format("inactivate %s", target);
    }

}
