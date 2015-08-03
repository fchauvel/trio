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

import eu.diversify.trio.simulation.events.Listener;
import eu.diversify.trio.simulation.filter.Filter;
import java.util.Arrays;
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

    public FixedFailureSequence(int runCount, Filter observation, Filter control, Action... actions) {
        super(runCount, observation, control);
        this.actions = Arrays.asList(actions);
    }

    @Override
    public Topology runOnce(int simulationId, int runId, Topology topology, Listener listener) {

        final Topology controlledGroup = topology.select(controlled());
        final Topology observedGroup = topology.select(observed());

        listener.sequenceInitiated(simulationId, runId, observedGroup.activeComponents(), controlledGroup.activeComponents());

        for (Action eachAction : actions) {
            eachAction.executeOn(topology);
        }

        listener.sequenceComplete(simulationId, runId);

        return topology;
    }

}
