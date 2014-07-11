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
package eu.diversify.trio.data;

import eu.diversify.trio.codecs.DataFormat;
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
    private final int activity;
    private final int loss;

    public State(Action trigger, int disruption, int activity, int loss) {
        this.trigger = trigger;
        this.disruption = disruption;
        this.activity = activity;
        this.loss = loss;
    }
    
    public <T> T accept(DataSetVisitor<T> visitor) {
        return visitor.visitState(this);
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

    public int getActivityLevel() {
        return activity;
    }

    public int getLoss() {
        return loss;
    }

    public State update(Action action, int activityLevel) {
        return new State(action, disruption + 1, activityLevel, activity - activityLevel);
    }

    public String to(DataFormat format, int sequenceIndex) { 
        return format.convert(sequenceIndex, trigger.toString(), disruption, activity, loss);
    }
}
