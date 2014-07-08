package eu.diversify.trio.actions;

import eu.diversify.trio.Action;
import eu.diversify.trio.Topology;

/**
 * Generalise the behaviour of action and their comparison
 */
public abstract class AbstractAction implements Action {

    public abstract Topology executeOn(Topology topology);

    @Override
    public final boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof AbstractAction) {
            return object.toString().equals(toString());
        }
        return false;
    }

    @Override
    public final int hashCode() {
        return toString().hashCode();
    }

    @Override
    public abstract String toString();

    
    /*
     * Factory methods to ease the construction of actions
     */
    
    public static Action none() {
        return None.getInstance();
    }
    
    public static Action activate(String target) {
        return new Activate(target); 
    }
    
    public static Action inactivate(String target) {
        return new Inactivate(target);
    }

}
