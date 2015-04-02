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
 * 
 */
package eu.diversify.trio.core.random;

import eu.diversify.trio.core.Component;
import eu.diversify.trio.core.requirements.Requirement;
import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.core.Tag;
import eu.diversify.trio.core.requirements.Nothing;
import eu.diversify.trio.core.requirements.RequirementFactory;
import eu.diversify.trio.core.requirements.random.BuildRandomizer;
import eu.diversify.trio.core.requirements.random.CachedLiteralFactory;
import eu.diversify.trio.core.requirements.random.FixedSizeBuilder;
import eu.diversify.trio.util.random.Distribution;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Generate randomized system description
 */
public class Generator {

    public static final int DEFAULT_LITERAL_COUNT = 10000;

    private final RequirementFactory factory;
    private final Random random;

    public Generator() {
        random = new Random();
        this.factory = new CachedLiteralFactory(DEFAULT_LITERAL_COUNT);
    }

    public Assembly assembly(int componentCount, Distribution valence) {
        final Component[] components = new Component[componentCount];
        for (int index = 0; index < componentCount; index++) {
            final int dependencyCount = componentCount;
            components[index] = component(index, componentCount, dependencyCount);
        }
        final List<Tag> tags = new ArrayList<Tag>(1);
        return new Assembly("randomly generated", Arrays.asList(components), tags);
    }
    
    public Assembly assembly(int componentCount, double edgeProbability) {
        final Component[] components = new Component[componentCount];
        for (int index = 0; index < componentCount; index++) {
            int total = 0;
            for(int i=0 ; i<componentCount ; i++) {
                total += (random.nextDouble() <= edgeProbability) ? 1 : 0;
            }                
            components[index] = component(index, componentCount, total);
        }
        final List<Tag> tags = new ArrayList<Tag>(1);
        return new Assembly("randomly generated", Arrays.asList(components), tags);
    }

    public Component component(int index, int capacity, int size) {
        if (size == 0) {
            return new Component(("C" + index).intern(), Nothing.getInstance());
        }
        BuildRandomizer builder = new BuildRandomizer(new FixedSizeBuilder(factory, size), random, capacity);
        Requirement dependencies = builder.build(); 
        return new Component(("C" + index).intern(), dependencies);
    }
    

}
