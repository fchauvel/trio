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
 * This file is part of TRIO :: Generator.
 *
 * TRIO :: Generator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Generator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Generator.  If not, see <http://www.gnu.org/licenses/>.
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
 * This file is part of TRIO :: Generator.
 *
 * TRIO :: Generator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Generator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Generator.  If not, see <http://www.gnu.org/licenses/>.
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
package net.fchauvel.trio.generator;

import net.fchauvel.trio.core.Assembly;
import net.fchauvel.trio.core.Component;
import net.fchauvel.trio.core.Tag;
import net.fchauvel.trio.core.requirements.Requirement;
import net.fchauvel.trio.core.requirements.RequirementFactory;
import net.fchauvel.trio.generator.requirements.BuildRandomizer;
import net.fchauvel.trio.generator.requirements.CachedLiteralFactory;
import net.fchauvel.trio.generator.requirements.FixedSizeBuilder;
import net.fchauvel.trio.graph.model.Graph;
import net.fchauvel.trio.graph.model.Vertex;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Generate various type of architecture models
 */
public class Generator {

    public static final String DEFAULT_ASSEMBLY_NAME = "randomly generated";
    public static final int DEFAULT_LITERAL_COUNT = 10000;

    private final RequirementFactory factory;
    private final Random random;

    public Generator() {
        this.random = new Random();
        this.factory = new CachedLiteralFactory(DEFAULT_LITERAL_COUNT);
    }

    /**
     * Build an assembly whose dependencies are specified by the given graph.
     * The logical expression that govern the local failure propagation will be
     * randomized, but will reference only the nodes specified in the graph.
     *
     * @param graph the graph describing the dependencies between components
     *
     * @return a randomized assembly, whose dependency graph matches the given
     * graph.
     */
    public Assembly nextAssembly(Graph graph) {
        final List<Component> components = new ArrayList<>(graph.vertexCount());
        for (Vertex eachVertex : graph.vertexes()) {
            Set<Vertex> dependencies = eachVertex.successors();
            components.add(component(eachVertex.id(), indices(dependencies)));
        }

        return new Assembly(DEFAULT_ASSEMBLY_NAME, components, defaultTags());
    }

    private List<Integer> indices(Set<Vertex> nodes) {
        final List<Integer> indices = new ArrayList<>(nodes.size());
        for (Vertex eachVertex : nodes) {
            indices.add(eachVertex.id());
        }
        return indices;
    }

    private Component component(int index, List<Integer> dependencies) {
        if (dependencies.isEmpty()) {
            return new Component("C" + index);
        }

        final FixedSizeBuilder builder = new FixedSizeBuilder(factory, dependencies.size());
        BuildRandomizer randomizer = new BuildRandomizer(builder, random, dependencies);
        Requirement requirement = randomizer.build();
        return new Component("C" + index, requirement);
    }

    private List<Tag> defaultTags() {
        final List<Tag> tags = new ArrayList<>(1);
        return tags;
    }

}
