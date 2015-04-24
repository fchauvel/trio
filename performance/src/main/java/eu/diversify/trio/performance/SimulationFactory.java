package eu.diversify.trio.performance;

import eu.diversify.trio.Trio;
import eu.diversify.trio.analysis.Analysis;
import eu.diversify.trio.analysis.RelativeRobustness;
import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.simulation.data.DataSet;
import eu.diversify.trio.generator.AssemblyKind;
import eu.diversify.trio.generator.Generator2;
import eu.diversify.trio.generator.Setup;
import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.graph.statistics.Degree;
import eu.diversify.trio.graph.statistics.Statistics;
import eu.diversify.trio.performance.util.Task;
import eu.diversify.trio.performance.util.TaskFactory;
import eu.diversify.trio.simulation.RandomFailureSequence;
import eu.diversify.trio.simulation.Scenario;
import java.util.HashMap;
import java.util.Map;

/**
 * Generate simulation of random models
 */
public class SimulationFactory implements TaskFactory {

    private final GeneratorSetupRandomizer setups;
    private final AssemblyKind kind;
    private final Trio trio;

    public SimulationFactory(AssemblyKind kind, GeneratorSetupRandomizer setups) {
        this.kind = kind;
        this.setups = setups;
        this.trio = new Trio();
    }

    @Override
    public Task prepareNewTask() {
        final Setup setup = setups.next();
        final Generator2 generate = new Generator2(setup);
        final Graph graph = generate.nextGraph(kind);
        final Assembly assembly = generate.nextAssembly(graph);
        return new SimulationTask(trio, kind, new Statistics(graph), assembly);
    }

    @Override
    public void reset() {
        // nothing to be done
    }
    
    

    private static class SimulationTask implements Task {

        private static final String SIZE = "size";
        private static final String DENSITY = "density";
        private static final String ROBUSTNESS = "robustness";
        private static final String KIND = "kind";
        private static final String DIAMETER = "diameter";
        private final Trio trio;
        private final Scenario scenario;
        private final Map<String, Object> properties;
        private DataSet result;

        public SimulationTask(Trio trio, AssemblyKind kind, Statistics graph, Assembly assembly) {
            this.properties = new HashMap<>();
            this.properties.put(KIND, kind.name());
            this.properties.put(SIZE, graph.nodeCount());
            this.properties.put(DENSITY, graph.density());
            this.properties.put("average node degree", graph.averageNodeDegree(Degree.OUT));
            //this.properties.put(DIAMETER, graph.diameter());
            this.scenario = new RandomFailureSequence(assembly);
            this.trio = trio;
        } //this.properties.put(DIAMETER, graph.diameter());

        @Override
        public void execute() {
            result = trio.run(scenario);
        }

        @Override
        public Map<String, Object> getProperties() {
            if (result != null) {
                Analysis analysis = trio.analyse(result);
                double robustness = analysis.metric(RelativeRobustness.NAME).distribution().mean();
                this.properties.put(ROBUSTNESS, robustness);
            }
            return this.properties;
        }

    }

}
