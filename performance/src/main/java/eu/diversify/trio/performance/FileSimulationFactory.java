package eu.diversify.trio.performance;

import eu.diversify.trio.Trio;
import eu.diversify.trio.analysis.Analysis;
import eu.diversify.trio.analysis.RelativeRobustness;
import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.simulation.data.DataSet;
import eu.diversify.trio.generator.Generator2;
import eu.diversify.trio.performance.util.Task;
import eu.diversify.trio.performance.util.TaskFactory;
import eu.diversify.trio.simulation.RandomFailureSequence;
import java.util.HashMap;
import java.util.Map;

/**
 * Build task that simulate assembly build after graphs from CSV file on disks.

 */
public class FileSimulationFactory implements TaskFactory {

   
    private final Trio trio;
    private final Generator2 generator;
    private GraphFactory graphs;

    public FileSimulationFactory() {
        this.trio = new Trio();
        this.generator = new Generator2();
        this.graphs = new GraphFactory();
    } 

    @Override
    public Task nextTask() {
        if (!graphs.hasNext()) {
            throw new IllegalStateException("No more graph files to process");
        }
        GraphData data = graphs.next();
        Assembly assembly = generator.nextAssembly(data.graph());
        return new SimulationTask(data.id(), trio, assembly); 
    }


    @Override
    public void reset() {
        graphs = new GraphFactory();
    }

    private static class SimulationTask implements Task {

        private static final String ROBUSTNESS = "robustness";

        private final int index;
        private final Trio trio;
        private final Assembly assembly;
        private final Map<String, Object> properties;
        private DataSet result;

        public SimulationTask(int index, Trio trio, Assembly assembly) {
            this.index = index;
            this.trio = trio;
            this.assembly = assembly;
            this.properties = new HashMap<>();
        }

        @Override
        public Map<String, Object> getProperties() {
            properties.put("id", index);

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
