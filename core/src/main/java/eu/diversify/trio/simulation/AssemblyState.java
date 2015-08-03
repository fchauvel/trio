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
 * One particular configuration of the system
 */
public class AssemblyState implements Topology { 

    public static final boolean ACTIVE = true;
    public static final boolean INACTIVE = !ACTIVE;

    private final Assembly system;
    private final BitSet isActive;

    public AssemblyState(Assembly system) {
        this.system = system;
        this.isActive = new BitSet(system.size());
        this.isActive.set(0, system.size(), true);
    }

    @Override
    public int getCapacity() {
        return system.size();
    }

    @Override
    public boolean isActive(String componentName) {
        return isActive.get(system.indexOf(componentName));
    }

    @Override
    public boolean isInactive(String componentName) {
        return !isActive(componentName);
    }

    public void setStatusOf(String component, boolean isActive) {
        this.isActive.set(system.indexOf(component), isActive);
    }

    @Override
    public void inactivate(String component) {
        setStatusOf(component, INACTIVE);
        propagateChanges();
    }

    @Override
    public void activate(String component) {
        setStatusOf(component, ACTIVE);
        propagateChanges();
    }

    private void propagateChanges() {
        boolean updated = true;
        while (updated) {
            updated = false;
            final BitSet remainActive = new BitSet(system.size());
            for (int i = isActive.nextSetBit(0);
                    i >= 0;
                    i = isActive.nextSetBit(i + 1)) {
                remainActive.set(i, isActive.get(i) && system.getComponent(i).isSatisfiedIn(this));
                updated |= isActive.get(i) != remainActive.get(i);
            }
            this.isActive.and(remainActive);
        }
    }
    
    public Topology select(Filter selector) {
        final Set<String> selection = selector.resolve(system);
        final BitSet bitset = new BitSet(system.size());
        for(String eachComponent: selection) {
            bitset.set(system.indexOf(eachComponent));
        }
        return new TopologyView(system, this, bitset);
    }

    public boolean hasActiveComponents() {
        return !isActive.isEmpty();
    }

    public List<String> activeComponents() {
        final List<String> activeComponents = new ArrayList<String>(system.size());
        for (int index = isActive.nextSetBit(0);
                index >= 0;
                index = isActive.nextSetBit(index + 1)) {
            activeComponents.add(system.getComponent(index).getName());
        }
        return activeComponents;
    }

    public BitSet asBitSet() {
        return (BitSet) this.isActive.clone();
    }


}
