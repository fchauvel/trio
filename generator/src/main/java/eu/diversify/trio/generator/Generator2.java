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
import eu.diversify.trio.graph.Graph;
import eu.diversify.trio.graph.Node;
import eu.diversify.trio.graph.generator.GraphGenerator;
import static eu.diversify.trio.graph.queries.SuccessorOf.successorOf;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        final List<Component> components = new ArrayList<>(graph.nodes().size());
        for (Node eachNode : graph.nodes()) {
            List<Node> dependencies = graph.nodes(successorOf(eachNode));
            components.add(component(eachNode.index(), indices(dependencies)));
        }

        return new Assembly(DEFAULT_ASSEMBLY_NAME, components, defaultTags());
    }

    private List<Integer> indices(List<Node> nodes) {
        final List<Integer> indices = new ArrayList<>(nodes.size());
        for (Node eachNode : nodes) {
            indices.add(eachNode.index());
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
