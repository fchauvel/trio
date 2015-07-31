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
import eu.diversify.trio.simulation.filter.Filter;
import eu.diversify.trio.simulation.events.Listener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Generate a random sequence of failure
 */
public class RandomFailureSequence extends Scenario {
    
    public RandomFailureSequence(int id, Assembly system) {
        super(id, system);
    }

    public RandomFailureSequence(int id, Assembly system, Filter observation, Filter control) {
        super(id, system, observation, control);
    }

    @Override
    public Topology run(Listener listener) {
        nextSequence();
        
        final Topology topology = new AssemblyState(assembly());
        final Topology controlledGroup = topology.select(controlled());
        final Topology observedGroup = topology.select(observed());

        listener.sequenceInitiated(id(), sequenceId(), observedGroup.activeComponents(), controlledGroup.activeComponents());
        
        while (observedGroup.hasActiveComponents()
                && controlledGroup.hasActiveComponents()) {
            
            final String selection = chooseAny(controlledGroup.activeComponents());
            
            controlledGroup.inactivate(selection);
            
            listener.failure(id(), sequenceId(), selection, observedGroup.activeComponents());
        }
        
        listener.sequenceComplete(id(), sequenceId());

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
