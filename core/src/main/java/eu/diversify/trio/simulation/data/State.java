/**
 *
 * This file is part of TRIO.
 *
 * TRIO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.diversify.trio.simulation.data;

import eu.diversify.trio.core.storage.DataFormat;
import eu.diversify.trio.simulation.Action;

/**
 * One state in the trace. It gathers the action that trigger this state, the
 * strength of the disruption, the activity level observed in the system (number
 * of active component) and the loss of activity (the number of component
 * inactivated by the associated action).
 * 
 */
public class State {

    private final Action trigger;
    private final int disruption;
    private final int observedActivity;
    private final int controlledActivity;
    private final int loss;

    public State(Action trigger, int disruption, int observedActivity, int controlledActivity, int loss) {
        this.trigger = trigger;
        this.disruption = disruption;
        this.observedActivity = observedActivity;
        this.controlledActivity = controlledActivity;
        this.loss = loss;
    }
   
    public void accept(DataSetListener listener) {
        listener.enterState(this);
        listener.exitState(this); 
    }
    
    public Action getTrigger() {
        return trigger;
    }

    public int getDisruptionLevel() {
        return disruption;
    }

    public int getObservedActivityLevel() {
        return observedActivity;
    }
    
    public int getControlledActivityLevel() {
        return controlledActivity;
    }

    public int getLoss() {
        return loss;
    }

    public State update(Action action, int observedAndActive, int controlledAndActive) {
        return new State(action, disruption + 1, observedAndActive, controlledAndActive, observedActivity - observedAndActive);
    }

    public String to(DataFormat format, int sequenceIndex) { 
        return format.convert(sequenceIndex, trigger.toString(), disruption, observedActivity, loss);
    }
}
