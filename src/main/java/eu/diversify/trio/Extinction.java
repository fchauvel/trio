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
package eu.diversify.trio;

import java.util.*;

/**
 * Represent an extinction sequence, as a list of subsequent status
 */
public class Extinction implements Iterable<State> {

    private final int headcount;
    private final List<State> states;

    public Extinction(int headCount) {
        this.headcount = headCount;
        this.states = new ArrayList<State>();
    }

    public void record(Action action, int survivorCount) {
        final int killedCount = killedCount() + action.killedCount();
        final int loss = survivorCount() - survivorCount;
        this.states.add(new State(action, killedCount, survivorCount, loss));
    }

    @Override
    public Iterator<State> iterator() {
        return states.iterator();
    }

    public int killedCount() {
        if (states.isEmpty()) {
            return 0;
        }
        return currentState().killedCount();
    }

    public boolean hasSurvivors() {
        return currentState().hasSurvivor();
    }

    private State currentState() {
        if (states.isEmpty()) {
            throw new IllegalStateException("This extinction sequence was not initialized!");
        }
        int last = states.size() - 1;
        return states.get(last);
    }

    public int length() {
        return states.size();
    }

    public int deadCount() {
        return currentState().killedCount();
    }

    public int survivorCount() {
        if (states.isEmpty()) {
            return headcount;
        }
        return currentState().survivorCount();
    }

    public Map<Action, Integer> impacts() {
        final Map<Action, Integer> results = new HashMap<Action, Integer>();
        for (State eachState: states) {
            results.put(eachState.getTrigger(), eachState.loss());
        }
        return results;
    }

    public int impactOf(Action action) {
        final Integer impact = impacts().get(action);
        return (impact == null) ? -1 : impact;
    }

    public int absoluteRobustness() {
        int result = 0;
        for (int i = 1; i < states.size(); i++) {
            final State current = states.get(i);
            final State previous = states.get(i - 1);
            result += (current.killedCount() - previous.killedCount()) * previous.survivorCount();
        }
        return result;
    }

    public double robustness() {
        int size = states.get(0).survivorCount();
        double maxRobustness = (size * (size + 1)) / 2;
        return absoluteRobustness() / maxRobustness * 100D;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("[");
        for (State eachState: states) {
            buffer.append(eachState);
            if (states.indexOf(eachState) < (states.size() - 1)) {
                buffer.append(", ");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }

    public State after(int killedCount) {
        if (killedCount < 0) {
            final String error = String.format("killed count must be a positive number (found %d)", killedCount);
            throw new IllegalArgumentException(error);
        }
        for (State each: states) {
            if (each.killedCount() == killedCount) {
                return each;
            }
        }
        return new ExtinctState(killedCount);
    }
}
