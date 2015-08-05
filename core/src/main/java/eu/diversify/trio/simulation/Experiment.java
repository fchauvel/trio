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
    private final int replicaCount;
    private int replica;
    private final Simulation simulation;
    private final Assembly subject;
    private final Listener listener;

    public Experiment(int id, Simulation simulation, Assembly subject, int replicaCount, Listener listener) {
        this.id = id;
        this.replicaCount = replicaCount;
        this.replica = 0;
        this.simulation = simulation;
        this.subject = subject;
        this.listener = listener;
    }

    public void execute() {
        listener.simulationInitiated(id);
        while (replica < replicaCount) {
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
        newReplica();
        Controller controller = simulation.prepareController();
        Topology state = new MonitoredTopology(new AssemblyState(subject));
        listener.sequenceInitiated(id, replica, simulation.observed(state).activeComponents(), simulation.controlled(state).activeComponents());
        while (controller.hasMoreAction(state)) {
            final Action action = controller.nextAction(state);
            action.executeOn(state);
        }
        listener.sequenceComplete(id, replica);
    }

    private int newReplica() {
        return replica++;
    }

    private class MonitoredTopology extends TopologyDecorator {

        public MonitoredTopology(Topology topology) {
            super(topology);        
        }

        @Override
        public void inactivate(String component) {
            super.inactivate(component);
            listener.failure(id, replica, component, simulation.observed(getTopology()).activeComponents());
        }
    }

}
