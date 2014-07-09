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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A 'tag' used to classify the component of the system
 */
public class Tag {

    private final String label;
    private final Set<String> targets;

    public Tag(String label, String... targets) {
        this(label, Arrays.asList(targets));
    }

    public Tag(String label, Collection<String> targets) {
        this.label = abortIfInvalid(label);
        this.targets = new HashSet<String>(abortIfInvalid(targets));
    }
    
    private String abortIfInvalid(String label) {
        if (label == null) {
            throw new IllegalArgumentException("Invalid 'null' value given a tag label!");
        }
        return label;
    }
    
    private Collection<String> abortIfInvalid(Collection<String> targets) {
        if (targets == null) {
            throw new IllegalArgumentException("Invalid 'null' values given as tag's target");
        }
        if (targets.isEmpty()) {
            throw new IllegalArgumentException("Invalid tags without any target ([] found)!");
        }
        return targets;
    }

    public String getLabel() {
        return label;
    }

    public Set<String> getTargets() {
        return Collections.unmodifiableSet(targets);
    }

}
