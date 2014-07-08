package eu.diversify.trio;

import eu.diversify.trio.Action;

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

}
