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
package eu.diversify.trio.core.random;

import eu.diversify.trio.core.Component;
import eu.diversify.trio.core.requirements.Requirement;
import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.core.Tag;
import eu.diversify.trio.core.requirements.RequirementFactory;
import eu.diversify.trio.core.requirements.random.CachedLiteralFactory;
import eu.diversify.trio.core.requirements.random.Goal;
import eu.diversify.trio.util.random.Distribution;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Generate randomised system description
 */
public class Generator {

    private final RequirementFactory factory;
    private final Goal goal;

    public Generator() {
        this.factory = new CachedLiteralFactory(10000);
        this.goal = new Goal(10000, factory);
    }

    public Assembly system(int componentCount, Distribution valence) {
        goal.setVariableCount(componentCount);
        final Component[] components = new Component[componentCount];
        for (int index = 0; index < componentCount; index++) {
            final int dependencyCount = (int) valence.sample();
            components[index] = component(index, dependencyCount);
        }
        final List<Tag> tags = new ArrayList<Tag>(1);
        return new Assembly("randomly generated", Arrays.asList(components), tags);
    }

    public Component component(int index, int dependencyCount) {
        Requirement dependencies = goal.build(dependencyCount); 
        return new Component("C" + index, dependencies);
    }

}
