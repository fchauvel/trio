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

import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.data.DataSet;
import eu.diversify.trio.filter.Filter;
import java.util.Arrays;
import java.util.List;

/**
 * A predefined sequence of failure
 */
public class FixedFailureSequence extends Scenario {

    private final List<Action> actions;

    public FixedFailureSequence(Assembly system, Action... actions) {
        super(system);
        this.actions = Arrays.asList(actions);
    }

    public FixedFailureSequence(Assembly system, Filter observation, Filter control, Action... actions) {
        super(system, observation, control);
        this.actions = Arrays.asList(actions);
    }

    @Override
    public Topology run(DataSet collector) {
        final Topology topology = instantiate(collector);
        for (Action eachAction: actions) {
            eachAction.executeOn(topology);
        }
        return topology;
    }

}
