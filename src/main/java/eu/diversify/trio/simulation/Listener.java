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

import eu.diversify.trio.data.Trace;
import eu.diversify.trio.simulation.actions.AbstractAction;

/**
 * Listen changes made on topologies
 */
public class Listener {
    
    private final Trace trace;

    public Listener(Trace trace) {
        this.trace = trace;
    }
    
    public void inactivate(String component, Topology topology) {
        trace.record(AbstractAction.inactivate(component), topology.countActiveAndObserved());
    }

    
    public void activate(String component, Topology topology) {
        trace.record(AbstractAction.activate(component), topology.countActiveAndObserved());
    }
        
}