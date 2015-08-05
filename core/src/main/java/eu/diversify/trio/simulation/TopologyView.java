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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A view of a specific subset of a topology.
 */
public class TopologyView extends TopologyDecorator {

    private final Set<String> selection;

    TopologyView(Topology subject, Set<String> selection) {
        super(subject);
        this.selection = selection;
    }
    
    @Override
    public int size() {
        return selection.size();
    }

    @Override
    public boolean hasActiveComponents() {
        for (String eachName: selection) {
            if (getTopology().isActive(eachName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> activeComponents() {
        final List<String> activeComponents = new ArrayList<String>(size());
        for (String each : selection) {
            if (getTopology().isActive(each)) {
                activeComponents.add(each);
            }
        }
        return activeComponents;
    }

    @Override
    public Topology select(Filter selector) {
        return new TopologyView(this, selector.evaluate(this)); 
    }

}
