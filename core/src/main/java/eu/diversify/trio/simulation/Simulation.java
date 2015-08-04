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
import eu.diversify.trio.simulation.events.Listener;
import eu.diversify.trio.simulation.filter.All;
import eu.diversify.trio.simulation.filter.Filter;
import java.util.Random;

/**
 * The scenario taken as input by Trio
 */
public abstract class Simulation {

    public static final int DEFAULT_RUN_COUNT = 5000;

    private final int runCount;
    private final Filter observation;
    private final Filter control;

    public Simulation() {
        this(DEFAULT_RUN_COUNT, All.getInstance(), All.getInstance());
    }
    
    public Simulation(int runCount) {
        this(runCount, All.getInstance(), All.getInstance());
    }

    public Simulation(int runCount, Filter observation, Filter control) {
        this.runCount = runCount;
        this.observation = observation;
        this.control = control;
    }

    public Filter observed() {
        return observation;
    }

    public Filter controlled() {
        return control;
    }

    public void run(Assembly assembly, Listener listener) {
        final int simulationId = generateId();

        listener.simulationInitiated(simulationId);
        for (int runId = 0; runId < runCount; runId++) {
            Topology initialState = new AssemblyState(assembly);
            runOnce(simulationId, runId, initialState, listener);
        }
        listener.simulationComplete(simulationId);
    }

    private static int generateId() {
        return RANDOM.nextInt();
    }
    
    private static final Random RANDOM = new Random();

    /**
     * Run this scenario while publishing events to the given listener
     *
     * @param simulationId the unique ID of the simulation including this run
     * @param target the topology against which the scenario should be simulated
     * @param listener the listener which will be notified of progress of the
     * this scenario
     * @return the topology at the end of the scenario
     */
    public abstract Topology runOnce(int simulationId, int runId, Topology target, Listener listener);

}
