package eu.diversify.trio.performance;

import eu.diversify.trio.Trio;
import eu.diversify.trio.analysis.Analysis;
import eu.diversify.trio.analysis.RelativeRobustness;
import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.simulation.data.DataSet;
import eu.diversify.trio.generator.Generator2;
import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.performance.util.Task;
import eu.diversify.trio.performance.util.TaskStore;
import eu.diversify.trio.simulation.RandomFailureSequence;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Build task that simulate assembly build after graphs from CSV file on disks.
 */
public class SimulationFactory implements TaskStore {

    private final Trio trio;
    private final Generator2 generator;
    private final GraphStore graphs;

    public SimulationFactory(GraphStore graphs) {
        this.trio = new Trio();
        this.generator = new Generator2();
        this.graphs = graphs;
    } 

    @Override
    public Task fetch(int id) {
        try {
            final Graph data = graphs.fetch(id);
            final Assembly assembly = generator.nextAssembly(data);
            return new SimulationTask(id, trio, assembly);
        
        } catch (IOException ex) {
            throw new IllegalArgumentException("Unknown task ID " + id, ex);
        
        }
    }
    
    private static class SimulationTask implements Task {

        private static final String ROBUSTNESS = "robustness";

        private final int id;
        private final Trio trio;
        private final Assembly assembly;
        private final Map<String, Object> properties;
        private DataSet result;

        public SimulationTask(int id, Trio trio, Assembly assembly) {
            this.id = id;
            this.trio = trio;
            this.assembly = assembly;
            this.properties = new HashMap<>();
        }

        @Override
        public int id() {
            return id;
        }
        
        @Override
        public Map<String, Object> getProperties() {
            properties.put("id", id);

            Analysis analysis = trio.analyse(result);
            double robustness = analysis.metric(RelativeRobustness.NAME).distribution().mean();
            this.properties.put(ROBUSTNESS, robustness);

            return properties;
        }

        @Override
        public void execute() {
            result = trio.run(new RandomFailureSequence(assembly), RUN_COUNT);
        }
        
        private static final int RUN_COUNT = 500;

    }

}
