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
package eu.diversify.trio.core;

import eu.diversify.trio.data.DataSet;
import eu.diversify.trio.simulation.Listener;
import eu.diversify.trio.simulation.Topology;
import eu.diversify.trio.data.Trace;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Represent the specification of a system under study. A collection component,
 * uniquely identified by their name.
 */
public class System {

    public static final String DEFAULT_NAME = "Anonymous";

    private final String name;
    private final Map<String, Component> components;
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
        this.components = new HashMap<String, Component>();
        for (Component each: components) {
            this.components.put(each.getName(), each);
        }
        this.tags = validateTags(tags);
    }

    private Map<String, Tag> validateTags(Collection<Tag> tags) {
        final Map<String, Tag> results = new HashMap<String, Tag>();
        for (Tag eachTag: tags) {
            for (String eachTarget: eachTag.getTargets()) {
                if (!components.containsKey(eachTarget)) {
                    final String error = String.format("Unable to find the component '%s' refered by tag '%s'", eachTarget, eachTag.getLabel());
                    throw new IllegalArgumentException(error);
                }
            }
            results.put(eachTag.getLabel(), eachTag);
        }
        return results;
    }

    public String getName() {
        return name;
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

    public Collection<String> getComponentNames() {
        return components.keySet();
    }

    public Topology instantiate() {
        return new Topology(this);
    }

    public Topology instantiate(DataSet report) {
        final Trace trace = new Trace(components.size());
        report.include(trace);
        return new Topology(this, new Listener(trace));
    }

    public Component requirementOf(String eachComponent) {
        return components.get(eachComponent);
    }

    @Override
    public String toString() {
        return String.format("(%s) %s ; %s",name, components.toString(), tags.toString());
    }

}
