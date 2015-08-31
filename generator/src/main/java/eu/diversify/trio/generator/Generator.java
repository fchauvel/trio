package eu.diversify.trio.generator;

import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.core.Component;
import eu.diversify.trio.core.Tag;
import eu.diversify.trio.core.requirements.Requirement;
import eu.diversify.trio.core.requirements.RequirementFactory;
import eu.diversify.trio.generator.requirements.BuildRandomizer;
import eu.diversify.trio.generator.requirements.CachedLiteralFactory;
import eu.diversify.trio.generator.requirements.FixedSizeBuilder;
import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.graph.model.Vertex;
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
