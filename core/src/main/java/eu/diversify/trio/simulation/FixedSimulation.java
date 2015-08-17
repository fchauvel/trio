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
@SuppressWarnings("PMD.UnusedPrivateField")
public class FixedSimulation extends Simulation {

    private final List<Action> actions;

    public FixedSimulation(Action... actions) {
        super();
        this.actions = Arrays.asList(actions);
    }

    public FixedSimulation(Filter observation, Filter control, Action... actions) {
        super(observation, control);
        this.actions = Arrays.asList(actions);
    }

    @Override
    Controller prepareController(Topology state, Clock clock) {
        return new IterationController(this, state, clock);
    }

    /**
     * Control the iteration on list of predefined actions
     */
    private static class IterationController extends Controller {

        private final Iterator<Action> actions;
        private final Topology controlled;
        private final Topology observed;

        public IterationController(FixedSimulation simulation, Topology state, Clock clock) {
            super(clock);
            controlled = simulation.controlled(state);
            observed = simulation.observed(state);
            this.actions = simulation.actions.iterator();
        }

        public boolean hasMoreAction() {
            return actions.hasNext()
                    && observed.hasActiveComponents()
                    && controlled.hasActiveComponents();
        }

        public Action nextAction() {
            if (!hasMoreAction()) {
                throw new IllegalStateException("No more action");
            }
            clock.increment(1);
            return actions.next();
        }

        @Override
        public double duration() {
            return controlled.size() + 1;
        }
    }

}
