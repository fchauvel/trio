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

import eu.diversify.trio.core.System;
import eu.diversify.trio.filter.All;
import eu.diversify.trio.filter.Filter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * One particular configuration of the system
 */
public class Topology {

    public static final boolean ACTIVE = true;
    public static final boolean INACTIVE = !ACTIVE;

    private final System system;
    private final Map<String, Boolean> status;
    private final Set<String> observed;
    private final Set<String> controlled;
    private final List<Listener> listeners;

    public Topology(System system) {
        this(system, All.getInstance(), All.getInstance(), new Listener[]{});
    }

    public Topology(System system, Filter observation, Filter control) {
        this(system, observation, control, new Listener[]{});
    }

    public Topology(System system, Filter observation, Filter control, Listener... listeners) {
        this.system = system;
        this.status = new HashMap<String, Boolean>();
        this.controlled = control.resolve(system);
        this.observed = observation.resolve(system);
        
        for (String eachName: system.getComponentNames()) {
            this.status.put(eachName, ACTIVE);
        }
        this.listeners = new ArrayList<Listener>(Arrays.asList(listeners));
    }

    public int getCapacity() {
        return status.size();
    }

    public boolean isActive(String name) {
        return statusOf(name);
    }

    private boolean statusOf(String component) {
        checkName(component);
        return status.get(component);
    }

    public boolean isInactive(String name) {
        return !isActive(name);
    }

    public int countActiveAndObserved() {
        return activeAndObservedComponents().size();
    }

    public Collection<String> activeAndObservedComponents() {
        final Collection<String> selection = new ArrayList<String>();
        for (String eachComponent: observed) {
            if (isActive(eachComponent)) {
                selection.add(eachComponent);
            }
        }
        return selection;
    }
    
     public Collection<String> activeAndControlledComponents() {
        final Collection<String> selection = new ArrayList<String>();
        for (String eachComponent: controlled) {
            if (isActive(eachComponent)) {
                selection.add(eachComponent);
            }
        }
        return selection;
    }
    
    public boolean isObserved(String component) {
         return observed.contains(component);
    }
    
    public boolean isControlled(String component) {
        return controlled.contains(component);
    }

    public boolean hasActiveAndObservedComponents() {
        return countActiveAndObserved() > 0;
    }
    
     public boolean hasActiveAndControlledComponents() {
        return !activeAndControlledComponents().isEmpty();
    }

    private void propagateChangesIn(Topology topology) {
        boolean updated = true;
        while (updated) {
            updated = false;
            for (String eachComponent: status.keySet()) {
                final boolean isActive = topology.isActive(eachComponent);
                boolean remainActive = isActive && system.requirementOf(eachComponent).isSatisfiedIn(topology);
                setStatusOf(eachComponent, remainActive);
                updated |= isActive != remainActive;
            }
        }
    }

    public void setStatusOf(String component, boolean isActive) {
        checkName(component);
        this.status.put(component, isActive);
    }

    public void inactivate(String component) {
        checkName(component);
        this.status.put(component, INACTIVE);
        propagateChangesIn(this);
        for (Listener each: listeners) {
            each.inactivate(component, this);
        }
    }

    public void activate(String component) {
        checkName(component);
        this.status.put(component, ACTIVE);
        propagateChangesIn(this);
        for (Listener each: listeners) {
            each.activate(component, this);
        }
    }

    private void checkName(String component) throws IllegalArgumentException {
        if (!status.containsKey(component)) {
            final String error = String.format("Unknown component '%s' (Components are: %s)", component, status.keySet());
            throw new IllegalArgumentException(error);
        }
    }

}
