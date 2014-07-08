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

package eu.diversify.trio;

import eu.diversify.trio.actions.Inactivate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Simulate extinction sequence on a given system
 */
public class Simulation {
    
    private final System system;
    private final Report report;
 
    public Simulation(System system) {
       this(system, new Report());
    } 
    
     public Simulation(System system, Report report) {
        this.system = system;
        this.report = report;
    }    
    
    public Topology run(Action... sequence) {
        final Topology topology = system.instantiate(report); 
        for (Action eachAction: sequence) {
            eachAction.executeOn(topology);
        }
        return topology;
    }
    
    public Topology randomExtinctionSequence() {
        final Topology topology = system.instantiate(report);
        while (topology.hasActiveComponents()) {
            Action action = new Inactivate(any(topology.activeComponents()));
            action.executeOn(topology);
        }
        return topology;
    }
    
    private String any(Collection<String> candidates) {
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
