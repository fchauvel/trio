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

import eu.diversify.trio.core.System;
import eu.diversify.trio.filter.All;
import eu.diversify.trio.filter.Filter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;

/**
 * One particular configuration of the system
 */
public class Topology {

    public static final boolean ACTIVE = true;
    public static final boolean INACTIVE = !ACTIVE;

    private final System system;
    private final BitSet isActive;
    private final BitSet isObserved;
    private final BitSet isControlled;
    private final List<Listener> listeners;

    public Topology(System system) {
        this(system, All.getInstance(), All.getInstance(), new Listener[]{});
    }

    public Topology(System system, Filter observation, Filter control) {
        this(system, observation, control, new Listener[]{});
    }

    public Topology(System system, Filter observation, Filter control, Listener... listeners) {
        this.system = system;
        this.isActive = new BitSet(system.size());
        this.isActive.set(0, system.size(), true);
        this.isObserved = new BitSet(system.size());
        for (String name: observation.resolve(system)) {
            isObserved.flip(system.indexOf(name));
        }
        this.isControlled = new BitSet(system.size());
        for (String name: control.resolve(system)) {
            isControlled.flip(system.indexOf(name));
        }
        this.listeners = new ArrayList<Listener>(Arrays.asList(listeners));
    }

    public int getCapacity() {
        return system.size();
    }

    public boolean isActive(String componentName) {
        return statusOf(componentName);
    }

    private boolean statusOf(String componentName) {
        return isActive.get(system.indexOf(componentName));
    }

    public boolean isInactive(String componentName) {
        return !isActive(componentName);
    }

    public int countActiveAndObserved() {
        BitSet isActiveCopy = activeAndObserved();
        return isActiveCopy.cardinality();
    }

    private BitSet activeAndObserved() {
        final BitSet isActiveCopy = (BitSet) isActive.clone();
        isActiveCopy.and(isObserved);
        return isActiveCopy;
    }

    public Collection<String> activeAndObservedComponents() {
        final Collection<String> selection = new ArrayList<String>(isObserved.cardinality());
        final BitSet activeAndObserved = activeAndObserved();
        for (int i = activeAndObserved.nextSetBit(0);
                i >= 0;
                i = activeAndObserved.nextSetBit(i + 1)) {
            selection.add(system.getComponent(i).getName());
        }
        return selection;
    }

    private BitSet activeAndControlled() {
        final BitSet isActiveCopy = (BitSet) isActive.clone();
        isActiveCopy.and(isControlled);
        return isActiveCopy;
    }

    public Collection<String> activeAndControlledComponents() {
        final Collection<String> selection = new ArrayList<String>(isControlled.cardinality());
        final BitSet activeAndControlled = activeAndControlled();
        for (int i = activeAndControlled.nextSetBit(0);
                i >= 0;
                i = activeAndControlled.nextSetBit(i + 1)) {
            selection.add(system.getComponent(i).getName());
        }
        return selection;
    }

    public boolean isObserved(String componentName) {
        return isObserved.get(system.indexOf(componentName));
    }

    public boolean isControlled(String componentName) {
        return isControlled.get(system.indexOf(componentName));
    }

    public boolean hasActiveAndObservedComponents() {
        return isActive.intersects(isObserved);
    }

    public boolean hasActiveAndControlledComponents() {
        return isActive.intersects(isControlled);
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

    public void setStatusOf(String component, boolean isActive) {
        this.isActive.set(system.indexOf(component), isActive);
    }

    public void inactivate(String component) {
        setStatusOf(component, INACTIVE);
        propagateChanges();
        for (Listener each: listeners) {
            each.inactivate(component, this);
        }
    }

    public void activate(String component) {
        setStatusOf(component, ACTIVE);
        propagateChanges();
        for (Listener each: listeners) {
            each.activate(component, this);
        }
    }

}
