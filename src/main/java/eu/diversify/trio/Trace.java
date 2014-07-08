/*
 */

package eu.diversify.trio;

import java.util.ArrayList;
import java.util.List;

import static eu.diversify.trio.actions.AbstractAction.*;

/**
 * The data collected by the recorder 
 */
public class Trace {

    private final int capacity;
    private final List<State> states;
    
    public Trace(int capacity) {
        this.capacity = capacity;
        this.states = new ArrayList<State>();
        states.add(new State(none(), 0, capacity, 0));
    }
    
    
    public List<Integer> disruptionLevels() {
        final List<Integer> levels = new ArrayList<Integer>();
        for(State eachState: states) {
            levels.add(eachState.getDisruptionLevel());
        }
        return levels;
    }
    
    public void record(Action action, int activityLevel) {
        final State next = current().update(action, activityLevel);
        this.states.add(next);
    }
    
    private State current() {
        if (states.isEmpty()) {
            throw new IllegalStateException("A trace shall have at least one state!");
        }
        final int lastIndex = states.size() - 1;
        return states.get(lastIndex);
    }
    
    public State afterDisruption(int level) {
        for(State eachState: states) {
            if (eachState.getDisruptionLevel() == level) {
                return eachState;
            }
        }
        return null;
    }
    
    public int length() {
        return states.size() - 1;
    }
    
}
