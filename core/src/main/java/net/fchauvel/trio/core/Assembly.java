/**
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
 * This file is part of TRIO :: Core.
 *
 * TRIO :: Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Core.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
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
 * ====
 *     This file is part of TRIO :: Core.
 *
 *     TRIO :: Core is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     TRIO :: Core is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with TRIO :: Core.  If not, see <http://www.gnu.org/licenses/>.
 * ====
 *
 * ====
 *     This file is part of TRIO.
 *
 *     TRIO is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     TRIO is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with TRIO.  If not, see <http://www.gnu.org/licenses/>.
 * ====
 *
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
 * This file is part of TRIO :: Core.
 *
 * TRIO :: Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Core.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
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

package net.fchauvel.trio.core;

import net.fchauvel.trio.util.Require;
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
public class Assembly implements AssemblyPart {

    public static final String DEFAULT_NAME = "Anonymous";

    private final String name;
    private final List<Component> components;
    private final Map<String, Integer> indexByName;
    private final Map<String, Tag> tags;

    public Assembly(Component... components) {
        this(DEFAULT_NAME, Arrays.asList(components), new ArrayList<Tag>());
    }

    public Assembly(Collection<Component> components) {
        this(DEFAULT_NAME, components, new ArrayList<Tag>());
    }

    public Assembly(Collection<Component> components, Collection<Tag> tags) {
        this(DEFAULT_NAME, components, tags);
    }

    public Assembly(String name, Collection<Component> components, Collection<Tag> tags) {
        Require.notNull(name, "Invalid value 'null', given as system name!");
        this.name = name;

        Require.notNull(components, "Invalid value 'null' given as list of components!");
        Require.notEmpty(components, "Invalid value [] given as list of components!");
        this.components = new ArrayList<Component>(components);
        this.indexByName = new HashMap<String, Integer>(components.size());
        int counter = 0;
        for (Component each: components) {
            this.indexByName.put(each.getName(), counter);
            counter++;
        }

        this.tags = new HashMap<String, Tag>();
        for(Tag eachTag: tags) {
            this.tags.put(eachTag.getLabel(), eachTag);
        }
    }

    private String validate(String componentName) throws IllegalArgumentException {
        if (!indexByName.containsKey(componentName)) {
            final String error = String.format("Syntax error: Unknown component '%s' (Components are: %s)", componentName, indexByName.keySet());
            throw new IllegalArgumentException(error);
        }
        return componentName;
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

    /**
     * @param name the name of the component whose index is needed
     * @return the index of the given component, is it exists, or null otherwise
     */
    public int indexOf(String name) {
        return indexByName.get(validate(name));
    }

    /**
     * @param componentName the name of component of interest
     * @return true if there exists a component with the given name, false
     * otherwise.
     */
    public boolean hasComponentNamed(String componentName) {
        return indexByName.containsKey(componentName);
    }

    public Collection<AssemblyPart> subParts() {
        final List<AssemblyPart> subparts = new ArrayList<AssemblyPart>(components.size() + tags.size());
        subparts.addAll(components);
        subparts.addAll(tags.values());
        return subparts;
    }

    public void begin(AssemblyVisitor visitor) {
        visitor.enter(this);
    }

    public void end(AssemblyVisitor visitor) {
        visitor.exit(this);
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
        final Assembly other = (Assembly) obj;
        return other.name.equals(name) && other.components.equals(components) && other.tags.equals(tags);
    }

    public Set<String> getComponentNames() {
        return indexByName.keySet();
    }

    public Component getComponent(int index) {
        return components.get(index);
    }
    
    public Component getComponent(String name) {
        return getComponent(indexOf(name));
    }

    public Component requirementOf(String componentName) {
        return getComponent(indexOf(componentName));
    }

    @Override
    public String toString() {
        return String.format("(%s) %s ; %s", name, components.toString(), tags.toString());
    }

}
