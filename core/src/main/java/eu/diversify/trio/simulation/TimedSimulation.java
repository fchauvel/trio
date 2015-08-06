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

import eu.diversify.trio.core.Component;
import eu.diversify.trio.simulation.actions.Inactivate;
import eu.diversify.trio.simulation.filter.Filter;
import java.util.List;

/**
 * Simulation failure according to the MTTF attached to components
 */
public class TimedSimulation extends Simulation {

    public TimedSimulation() {
    }

    public TimedSimulation(Filter observation, Filter control) {
        super(observation, control);
    }

    @Override
    Controller prepareController(Topology state, Clock clock) {
        return new TimedControlled(this, state, clock);
    }

    private static class TimedControlled extends Controller {

        private final Topology controlled;
        private final Topology observed;

        public TimedControlled(TimedSimulation simulation, Topology state, Clock clock) {
            super(state, clock);
            controlled = simulation.controlled(state);
            observed = simulation.observed(state);
        }

        public boolean hasMoreAction() {
            return observed.hasActiveComponents()
                    && controlled.hasActiveComponents();
        }

        public Action nextAction() {
            String selected = minimumMTTF();
            return new Inactivate(selected);
        }
        
        private String minimumMTTF() {
            final List<String> candidates = controlled.activeComponents();
            if (candidates.isEmpty()) {
                throw new IllegalStateException("No more active components under control");
            }
            
            String selected = "";
            double mttf = Double.POSITIVE_INFINITY;
            for (String eachName : candidates) {
                Component eachComponent = state.architecture().getComponent(eachName);
                if (eachComponent.meanTimeToFailure() < mttf) {
                    mttf = eachComponent.meanTimeToFailure();
                    selected = eachName;
                }
            }
            
            assert !selected.isEmpty(): "Minimum MTTF detection failed";
            clock.setTime(mttf);
            return selected;
        } 

        @Override
        public double duration() {
            return maximumMTTF() + 1;
        }

        private double maximumMTTF() {
            final List<String> candidates = controlled.activeComponents();
            if (candidates.isEmpty()) {
                throw new IllegalStateException("No more active components under control");
            }
            
            double maximum = 0;
            for (String eachName : candidates) {
                Component eachComponent = state.architecture().getComponent(eachName);
                if (eachComponent.meanTimeToFailure() > maximum) {
                    maximum = eachComponent.meanTimeToFailure();
                }
            }
            
            return maximum;
        }
    }

}
