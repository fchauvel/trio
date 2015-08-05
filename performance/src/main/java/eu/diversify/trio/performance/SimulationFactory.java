package eu.diversify.trio.performance;

import eu.diversify.trio.Trio;
import eu.diversify.trio.TrioListener;
import eu.diversify.trio.analytics.robustness.Robustness;
import eu.diversify.trio.analytics.sensitivity.Sensitivity;
import eu.diversify.trio.analytics.threats.Threat;
import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.core.storage.InMemoryStorage;
import eu.diversify.trio.core.storage.StorageError;
import eu.diversify.trio.simulation.data.DataSet;
import eu.diversify.trio.generator.Generator2;
import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.performance.util.Task;
import eu.diversify.trio.performance.util.TaskStore;
import eu.diversify.trio.simulation.RandomFailureSequence;
import eu.diversify.trio.simulation.filter.All;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Build task that simulate assembly build after graphs from CSV file on disks.
 */
public class SimulationFactory implements TaskStore {

    private final Generator2 generator;
    private final GraphStore graphs;

    public SimulationFactory(GraphStore graphs) {
        this.generator = new Generator2();
        this.graphs = graphs;
    }

    @Override
    public Task fetch(int id) {
        try {
            final Graph data = graphs.fetch(id);
            final Assembly assembly = generator.nextAssembly(data);
            final Trio trio = new Trio(new InMemoryStorage(assembly));
            return new SimulationTask(id, trio, assembly);

        } catch (IOException ex) {
            throw new IllegalArgumentException("Unknown task ID " + id, ex);

        }
    }

    private static class SimulationTask implements Task {

        private static final String ROBUSTNESS_KEY = "robustness";

        private final int id;
        private final Trio trio;
        private final Assembly assembly;
        private final Properties properties;
        private DataSet result;

        public SimulationTask(int id, Trio trio, Assembly assembly) {
            this.id = id;
            this.trio = trio;
            this.assembly = assembly;
            this.properties = new Properties();
        }

        @Override
        public int id() {
            return id;
        }

        @Override
        public void execute() {
            final RandomFailureSequence simulation = new RandomFailureSequence(All.getInstance(), All.getInstance());
            try {
                trio.run(simulation, RUN_COUNT, listener());
            
            } catch (StorageError ex) {
                throw new RuntimeException("Unable to access storage", ex);
            
            }
        }

        public TrioListener listener() {
            return new TrioListener() {
                
                @Override
                public void onRobustness(Robustness indicator) {
                    properties.put(ROBUSTNESS_KEY, indicator.average());
                }

                @Override
                public void onSensitivityRanking(List<Sensitivity> indicator) {
                }

                @Override
                public void onThreatRanking(List<Threat> indicator) {
                }
            };
        }

        @Override
        public Properties properties() {
            properties.put("id", id);
            return properties;
        }

        private static final int RUN_COUNT = 500;

    }

}
