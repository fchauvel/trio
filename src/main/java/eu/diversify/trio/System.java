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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Represent the specification of a system under study. A collection component,
 * uniquely identified by their name.
 */
public class System {

    private final Map<String, Component> components;

    public System(Component... components) {
        this(Arrays.asList(components));
    }

    public System(Collection<Component> components) {
        this.components = new HashMap<String, Component>();
        for (Component each: components) {
            this.components.put(each.getName(), each);
        }
    }

    public Collection<String> getComponentNames() {
        return components.keySet();
    }

    public Topology instantiate() {
        return new Topology(this);
    }

    public Topology instantiate(Report report) {
        final Trace trace = new Trace(components.size());
        report.include(trace);
        return new Topology(this, new Listener(trace));        
    }

    public Component requirementOf(String eachComponent) {
        return components.get(eachComponent);
    }

}
