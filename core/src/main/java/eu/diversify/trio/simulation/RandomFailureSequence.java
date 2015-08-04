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

import eu.diversify.trio.simulation.filter.Filter;
import eu.diversify.trio.simulation.events.Listener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Random sequence of failure
 */
public class RandomFailureSequence extends Simulation {
    
    public RandomFailureSequence() {
        super();
    }

    public RandomFailureSequence(int runCount) {
        super(runCount);
    }
    
    public RandomFailureSequence(int runCount, Filter observation, Filter control) {
        super(runCount, observation, control);
    }

    @Override 
    public Topology runOnce(int simulationId, int runId, Topology topology, Listener listener) {        
        final Topology controlledGroup = topology.select(controlled());
        final Topology observedGroup = topology.select(observed());

        listener.sequenceInitiated(simulationId, runId, observedGroup.activeComponents(), controlledGroup.activeComponents());
        
        while (observedGroup.hasActiveComponents()
                && controlledGroup.hasActiveComponents()) {
            
            final String selection = chooseAny(controlledGroup.activeComponents());
            
            controlledGroup.inactivate(selection);
            
            listener.failure(simulationId, runId, selection, observedGroup.activeComponents());
        }
        
        listener.sequenceComplete(simulationId, runId);

        return topology;
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
