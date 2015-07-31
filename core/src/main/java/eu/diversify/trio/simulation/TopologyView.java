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
import eu.diversify.trio.simulation.filter.Filter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Set;

/**
 * A view of a specific subset of a topology.
 */
public class TopologyView implements Topology {

    private final Assembly assembly;
    private final Topology topology;
    private final BitSet selection;

    TopologyView(Assembly assembly, Topology subject, BitSet bitset) {
        this.topology = subject;
        this.selection = bitset;
        this.assembly = assembly;
    }

    public int getCapacity() {
        return selection.cardinality();
    }

    public void activate(String component) {
        topology.activate(component);
    }

    public void inactivate(String component) {
        topology.inactivate(component);
    }

    public boolean isActive(String componentName) {
        return topology.isActive(componentName);
    }

    public boolean isInactive(String componentName) {
        return topology.isInactive(componentName);
    }

    public boolean contains(String componentName) {
        return selection.get(assembly.indexOf(componentName));
    }

    public boolean hasActiveComponents() {
        return !asBitSet().isEmpty();
    }

    public List<String> activeComponents() {
        final List<String> activeComponents = new ArrayList<String>(getCapacity());
        for (String eachComponent : topology.activeComponents()) {
            if (contains(eachComponent)) {
                activeComponents.add(eachComponent);
            }
        }
        return activeComponents;
    }

    public BitSet asBitSet() {
        BitSet states = topology.asBitSet();
        states.and(selection);
        return states;
    }

    public Topology select(Filter selector) {
        final Set<String> selection = selector.resolve(assembly);
        final BitSet bitset = new BitSet(assembly.size());
        for (String eachComponent : selection) {
            bitset.set(assembly.indexOf(eachComponent));
        }
        bitset.and(asBitSet());
        return new TopologyView(assembly, this, bitset);
    }

}
