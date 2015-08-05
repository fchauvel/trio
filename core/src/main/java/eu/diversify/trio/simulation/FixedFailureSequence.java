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

package eu.diversify.trio.simulation;

import eu.diversify.trio.simulation.filter.Filter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * A predefined sequence of failure
 */
public class FixedFailureSequence extends Simulation {

    private final List<Action> actions;

    public FixedFailureSequence(Action... actions) {
        super();
        this.actions = Arrays.asList(actions);
    }

    public FixedFailureSequence(Filter observation, Filter control, Action... actions) {
        super(observation, control);
        this.actions = Arrays.asList(actions);
    }

    @Override
    Controller prepareController() {
        return new IterationController();
    }

    /**
     * Control the iteration on list of predefined actions
     */
    private class IterationController implements Controller {

        private final Iterator<Action> actions;

        public IterationController() {
            this.actions = FixedFailureSequence.this.actions.iterator();
        }

        public boolean hasMoreAction(Topology state) {
            return actions.hasNext()
                    && observed(state).hasActiveComponents()
                    && controlled(state).hasActiveComponents();
        }

        public Action nextAction(Topology state) {
            if (!hasMoreAction(state)) {
                throw new IllegalStateException("No more action");
            }
            return actions.next();
        }
    }

}
