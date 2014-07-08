
package eu.diversify.trio;

import eu.diversify.trio.actions.AbstractAction;

/**
 * Listen changes made on topologies
 */
public class Listener {
    
    private final Trace trace;

    public Listener(Trace trace) {
        this.trace = trace;
    }
    
    public void inactivate(String component, Topology topology) {
        trace.record(AbstractAction.inactivate(component), topology.countActive());
    }

    
    public void activate(String component, Topology topology) {
        trace.record(AbstractAction.activate(component), topology.countActive());
    }
        
}
