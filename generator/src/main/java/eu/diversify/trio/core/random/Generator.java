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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generate randomized system description
 */
public class Generator {

    public static final String DEFAULT_ASSEMBLY_NAME = "randomly generated";
    public static final int DEFAULT_LITERAL_COUNT = 10000;

    private final Logger logger;

    private final RequirementFactory factory;
    private final Random random;

    public Generator() {
        logger = Logger.getLogger(Generator.class.getName());
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
        return new Assembly(DEFAULT_ASSEMBLY_NAME, Arrays.asList(components), tags);
    }

    /**
     * Control the density of the underlying graph adding dependency with a
     * given probability
     *
     * @param componentCount the number of component in the graph
     * @param edgeProbability the probability to build an edge
     * @return a randomly generated assembly
     */
    public Assembly assembly(int componentCount, double edgeProbability) {
        logger.log(
                Level.FINER,
                "Building assembly with {0} components and {1} % edges",
                new Object[]{componentCount, Math.round(edgeProbability * 100)}
        );

        final Component[] components = new Component[componentCount];
        for (int index = 0; index < componentCount; index++) {
            int dependencyCount = 0;
            for (int i = 0; i < componentCount; i++) {
                dependencyCount += (random.nextDouble() <= edgeProbability) ? 1 : 0;
            }
            components[index] = component(index, componentCount, dependencyCount);
        }
        final List<Tag> tags = new ArrayList<Tag>(1);
        return new Assembly(DEFAULT_ASSEMBLY_NAME, Arrays.asList(components), tags);
    }

    public Assembly assembly(int componentCount) {
        final double globalRatio = random.nextDouble();
        int totalDependencyCount = (int) Math.round(globalRatio * Math.pow(componentCount, 2));
        logger.log(Level.FINER, "New component with {0} links ({1} %)", new Object[]{totalDependencyCount, globalRatio});

        double dependencyTotal = 0D;
        final double dependencyRatio[] = new double[componentCount];
        for (int index = 0; index < componentCount; index++) {
            dependencyRatio[index] = random.nextDouble();
            dependencyTotal += dependencyRatio[index];
        }
        final Component[] components = new Component[componentCount];
        for (int index = 0; index < componentCount; index++) {
            final double localRatio = dependencyRatio[index] / dependencyTotal;
            int dependencyCount = (int) Math.round(localRatio * totalDependencyCount);
            if (dependencyCount > componentCount) {
                dependencyCount = componentCount;
            }
            components[index] = component(index, componentCount, dependencyCount);
        }
        final List<Tag> tags = new ArrayList<Tag>(1);
        return new Assembly(DEFAULT_ASSEMBLY_NAME, Arrays.asList(components), tags);

    }

    public Component component(int index, int capacity, int dependencyCount) {
        assert capacity >= dependencyCount : "Illegal component with more dependencies than there are components";

        if (dependencyCount == 0) {
            return new Component(("C" + index).intern(), Nothing.getInstance());
        }

        logger.log(Level.FINEST, "New requirement with {0} variables chosen from {1}", new Object[]{dependencyCount, capacity});
        BuildRandomizer builder = new BuildRandomizer(new FixedSizeBuilder(factory, dependencyCount), random, capacity);
        Requirement dependencies = builder.build();
        return new Component(("C" + index).intern(), dependencies);
    }

}
