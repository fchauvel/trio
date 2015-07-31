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

import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.simulation.events.Listener;
import eu.diversify.trio.simulation.filter.Filter;
import java.util.Arrays;
import java.util.List;

/**
 * A predefined sequence of failure
 */
public class FixedFailureSequence extends Scenario {

    private final List<Action> actions;

    public FixedFailureSequence(int id, Assembly system, Action... actions) {
        super(id, system);
        this.actions = Arrays.asList(actions);
    }

    public FixedFailureSequence(int id, Assembly system, Filter observation, Filter control, Action... actions) {
        super(id, system, observation, control);
        this.actions = Arrays.asList(actions);
    }

    @Override
    public AssemblyState run(Listener listener) {

        nextSequence();

        final AssemblyState topology = new AssemblyState(assembly());
        final Topology controlledGroup = topology.select(controlled());
        final Topology observedGroup = topology.select(observed());

        listener.sequenceInitiated(id(), sequenceId(), observedGroup.activeComponents(), controlledGroup.activeComponents());

        for (Action eachAction : actions) {
            eachAction.executeOn(topology);
        }

        listener.sequenceComplete(id(), sequenceId());

        return topology;
    }

}
