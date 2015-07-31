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
import eu.diversify.trio.simulation.filter.All;
import eu.diversify.trio.simulation.filter.Filter;

/**
 * The scenario taken as input by Trio
 */
public abstract class Scenario {

    private final int scenarioId;
    private final Assembly assembly;
    private final Filter observation;
    private final Filter control;
    private int sequenceId;

    public Scenario(int id, Assembly system) {
        this(id, system, All.getInstance(), All.getInstance());
    }

    public Scenario(int id, Assembly assembly, Filter observation, Filter control) {
        this.scenarioId = id;
        this.assembly = assembly;
        this.observation = observation;
        this.control = control;
        this.sequenceId = 0;
    }

    public int id() {
        return this.scenarioId;
    }

    public Filter observed() {
        return observation;
    }

    public Filter controlled() {
        return control;
    }

    public Assembly assembly() {
        return assembly;
    }

    protected int sequenceId() {
        return sequenceId;
    }

    protected void nextSequence() {
        sequenceId++;
    }

    /**
     * Run this scenario while publishing events to the given listener
     *
     * @param listener the listener which will be notified of progress of the
     * this scenario
     * @return the topology at the end of the scenario
     */
    public abstract Topology run(Listener listener);

}
