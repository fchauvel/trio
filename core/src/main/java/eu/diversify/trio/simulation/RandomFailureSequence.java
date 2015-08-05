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
/**
 *
 * This file is part of TRIO.
 *
 * TRIO is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * TRIO is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO. If not, see <http://www.gnu.org/licenses/>.
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
public class RandomFailureSequence extends Simulation {

    public RandomFailureSequence() {
    }

    public RandomFailureSequence(Filter observation, Filter control) {
        super(observation, control);
    }

    @Override
    Controller prepareController() {
        return new SimulationController();
    }

    /**
     * Drive the execution of random failure sequence.
     *
     * Select randomly a component to fail, amongst the one under control that
     * are active.
     */
    private class SimulationController implements Controller {

        public boolean hasMoreAction(Topology state) {
            return observed(state).hasActiveComponents()
                    && controlled(state).hasActiveComponents();
        }

        public Action nextAction(Topology state) {
            final String selection = chooseAny(controlled(state).activeComponents());
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
            return candidateList.get(new Random().nextInt(candidateList.size()));
        }

    }

}
