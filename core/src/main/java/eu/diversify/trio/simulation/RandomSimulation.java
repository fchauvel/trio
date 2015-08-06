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

import eu.diversify.trio.simulation.actions.Inactivate;
import eu.diversify.trio.simulation.filter.Filter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Random sequence of failure
 */
public class RandomSimulation extends Simulation {

    public RandomSimulation() {
    }

    public RandomSimulation(Filter observation, Filter control) {
        super(observation, control);
    }

    @Override
    Controller prepareController(Topology state, Clock clock) {
        return new SimulationController(this, state, clock);
    }

    /**
     * Drive the execution of random failure sequence.
     *
     * Select randomly a component to fail, amongst the one under control that
     * are active.
     */
    private static class SimulationController extends Controller {

        private final Topology controlled;
        private final Topology observed;

        public SimulationController(RandomSimulation simulation, Topology state, Clock clock) {
            super(state, clock); 
            controlled = simulation.controlled(state);
            observed = simulation.observed(state);
        }

        public boolean hasMoreAction() {
            return observed.hasActiveComponents()
                    && controlled.hasActiveComponents();
        }

        public Action nextAction() {
            final String selection = chooseAny(controlled.activeComponents());
            clock.increment(1);
            return new Inactivate(selection);
        }

        private String chooseAny(Collection<String> candidates) {
            if (candidates.isEmpty()) {
                throw new IllegalArgumentException("Unable to choose from an empty collection!");
            }
            final List<String> candidateList = new ArrayList<String>(candidates);
            if (candidateList.size() == 1) {
                return candidateList.get(0);
            }
            return candidateList.get(RANDOM.nextInt(candidateList.size()));
        }

        @Override
        public double duration() {
            return controlled.size() + 1;
        }

    }
    
    private static final Random RANDOM = new Random();

}
