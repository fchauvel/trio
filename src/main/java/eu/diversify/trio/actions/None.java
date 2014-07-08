/*
 */

package eu.diversify.trio.actions;

import eu.diversify.trio.Topology;

/**
 * The 'do nothing' action
 */
public class None extends AbstractAction {

    private static None instance;
    
    public static None getInstance() {
        if (instance == null) {
            instance = new None();
        }
        return instance;
    }
    
    private None() {}

    @Override
    public Topology executeOn(Topology topology) {
        return topology;
    }
    
    @Override
    public String toString() {
        return "none";
    }
    
    
}
