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
package eu.diversify.trio.core;

import eu.diversify.trio.data.DataSet;
import eu.diversify.trio.simulation.Listener;
import eu.diversify.trio.simulation.Topology;
import eu.diversify.trio.data.Trace;
import eu.diversify.trio.filter.All;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represent the specification of a system under study. A collection component,
 * uniquely identified by their name.
 */
public class System {

    public static final String DEFAULT_NAME = "Anonymous";

    private final String name;
    private final List<Component> components;
    private final Map<String, Integer> indexByName;
    private final Map<String, Tag> tags;

    public System(Component... components) {
        this(DEFAULT_NAME, Arrays.asList(components), new ArrayList<Tag>());
    }

    public System(Collection<Component> components) {
        this(DEFAULT_NAME, components, new ArrayList<Tag>());
    }

    public System(Collection<Component> components, Collection<Tag> tags) {
        this(DEFAULT_NAME, components, tags);
    }

    public System(String name, Collection<Component> components, Collection<Tag> tags) {
        if (components == null) {
            throw new IllegalArgumentException("Invalid value 'null' given as list of components!");
        }
        if (components.isEmpty()) {
            throw new IllegalArgumentException("Invalid value [] given as list of components!");
        }
        this.name = name;
        this.components = new ArrayList<Component>(components);
        this.indexByName = new HashMap<String, Integer>(components.size());
        int counter = 0;
        for (Component each: components) {
            this.indexByName.put(each.getName(), counter++);
        }
        this.tags = validateTags(tags);
    }

    private Map<String, Tag> validateTags(Collection<Tag> tags) {
        final Map<String, Tag> results = new HashMap<String, Tag>();
        for (Tag eachTag: tags) {
            for (String eachTarget: eachTag.getTargets()) {
                validate(eachTarget);
            }
            results.put(eachTag.getLabel(), eachTag);
        }
        return results;
    }

    public String getName() {
        return name;
    }

    /**
     * @return the number of components in the systems
     */
    public int size() {
        return this.components.size();
    }

    public int indexOf(String name) {
        return indexByName.get(validate(name));
    }

    private String validate(String componentName) throws IllegalArgumentException {
        if (!indexByName.containsKey(componentName)) {
            final String error = String.format("Unknown component '%s' (Components are: %s)", componentName, indexByName.keySet());
            throw new IllegalArgumentException(error);
        }
        return componentName;
    }

    /**
     * Traversal of the system by a listener
     *
     * @param listener the listener that should be triggered during the
     * traversal
     */
    public void accept(SystemListener listener) {
        listener.enterSystem(this);
        for (Component eachComponent: components) {
            eachComponent.accept(listener);
        }
        for (Tag eachTag: tags.values()) {
            eachTag.accept(listener);
        }
        listener.exitSystem(this);
    }

    public Set<String> taggedAs(String tag) {
        return tags.get(tag).getTargets();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.name.hashCode();
        hash = 41 * hash + this.components.hashCode();
        hash = 41 * hash + this.tags.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final System other = (System) obj;
        return other.name.equals(name) && other.components.equals(components) && other.tags.equals(tags);
    }

    public Set<String> getComponentNames() {
        return indexByName.keySet();
    }

    public Component getComponent(int index) {
        return components.get(index);
    }

    public Topology instantiate() {
        return new Topology(this);
    }

    public Topology instantiate(DataSet report) {
        final Trace trace = new Trace(size());
        report.include(trace);
        return new Topology(this, All.getInstance(), All.getInstance(), new Listener(trace));
    }

    public Component requirementOf(String componentName) {
        return getComponent(indexOf(componentName));
    }

    public int getTotalComplexity() {
        int total = 0;
        for (Component eachComponent: components) {
            total += eachComponent.getRequirement().getComplexity();
        }
        return total;
    }

    public double getMeanComplexity() {
        return ((double) getTotalComplexity()) / size();
    }

    public double getDensity() {
        double total = 0;
        for (Component eachComponent: components) {
            total += eachComponent.getValency();
        }
        return total / Math.pow(size(), 2);
    }

    @Override
    public String toString() {
        return String.format("(%s) %s ; %s", name, components.toString(), tags.toString());
    }

}
