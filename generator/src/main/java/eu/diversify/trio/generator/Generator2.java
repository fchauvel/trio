package eu.diversify.trio.generator;

import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.core.Component;
import eu.diversify.trio.core.Tag;
import eu.diversify.trio.core.requirements.Requirement;
import eu.diversify.trio.core.requirements.RequirementFactory;
import static eu.diversify.trio.generator.Generator.DEFAULT_ASSEMBLY_NAME;
import static eu.diversify.trio.generator.Generator.DEFAULT_LITERAL_COUNT;
import eu.diversify.trio.generator.requirements.BuildRandomizer;
import eu.diversify.trio.generator.requirements.CachedLiteralFactory;
import eu.diversify.trio.generator.requirements.FixedSizeBuilder;
import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.graph.model.Vertex;
import eu.diversify.trio.graph.generator.GraphGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Generate various type of architecture models
 */
public class Generator2 {

    private final Setup setup;
    private final RequirementFactory factory;
    private final Random random;

    public Generator2() {
        this(new Setup());
    }
    
    public Generator2(Setup setup) {
        this.setup = setup;
        this.random = new Random();
        this.factory = new CachedLiteralFactory(DEFAULT_LITERAL_COUNT);
    }
    
    
    public Assembly nextAssembly(AssemblyKind kind) {
        GraphGenerator graphs = setup.generatorFor(kind);
        Graph graph = graphs.nextGraph();
        return buildAssembly(graph);
    }

    /**
     * Build an assembly whose dependencies are specified by the given graph.
     * The logical expression that govern the local failure propagation will be
     * randomized, but will reference only the nodes specified in the graph.
     *
     * @param dependencyGraph the graph describing the dependencies between
     * components
     *
     * @return a randomized assembly, whose dependency graph matches the given
     * graph.
     */
    private Assembly buildAssembly(Graph graph) {
        final List<Component> components = new ArrayList<>(graph.vertexCount());
        for (Vertex eachNode : graph.vertexes()) {
            Set<Vertex> dependencies = eachNode.successors();
            components.add(component(eachNode.id(), indices(dependencies)));
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
            return new Component(("C" + index).intern());
        }
        
        final FixedSizeBuilder builder = new FixedSizeBuilder(factory, dependencies.size());
        BuildRandomizer randomizer = new BuildRandomizer(builder, random, dependencies);
        Requirement requirement = randomizer.build();
        return new Component(("C" + index).intern(), requirement);
    }

    private List<Tag> defaultTags() {
        final List<Tag> tags = new ArrayList<>(1);
        return tags;
    }

}
