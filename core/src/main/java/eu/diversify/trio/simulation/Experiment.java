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

public class Experiment {

    private final int id;
    private final int runCount;
    private int currentRun;
    private final Simulation simulation;
    private final Assembly subject;
    private final Listener listener;

    public Experiment(int id, Simulation simulation, Assembly subject, int runCount, Listener listener) {
        this.id = id;
        this.runCount = runCount;
        this.currentRun = 0;
        this.simulation = simulation;
        this.subject = subject;
        this.listener = listener;
    }

    public void execute() {
        listener.simulationInitiated(id);
        while (currentRun < runCount) {
            replicate();
        }
        listener.simulationComplete(id);
    }

    /**
     * Run this experiment once.
     *
     * @param index the id of the execution
     */
    private void replicate() {
        currentRun++;
        final Controller controller = simulation.prepareController();
        final Topology state = new MonitoredTopology(new AssemblyState(subject));
        final Topology observed = simulation.observed(state);
        final Topology controlled = simulation.controlled(state);
        listener.sequenceInitiated(id, currentRun, observed.activeComponents(), controlled.activeComponents());
        while (controller.hasMoreAction(state)) {
            final Action action = controller.nextAction(state);
            action.executeOn(state);
        }
        listener.sequenceComplete(id, currentRun);
    }

    
    private class MonitoredTopology extends TopologyDecorator {

        private final Topology observed;

        public MonitoredTopology(Topology topology) {
            super(topology);        
            observed = simulation.observed(getTopology());
        }

        @Override
        public void inactivate(String component) {
            super.inactivate(component);
            listener.failure(id, currentRun, component, observed.activeComponents());
        }
    }

}
