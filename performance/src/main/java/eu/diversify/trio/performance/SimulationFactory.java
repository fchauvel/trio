package eu.diversify.trio.performance;

import eu.diversify.trio.Trio;
import eu.diversify.trio.analysis.Analysis;
import eu.diversify.trio.analysis.RelativeRobustness;
import eu.diversify.trio.core.Assembly;
import static eu.diversify.trio.core.Evaluation.evaluate;
import eu.diversify.trio.generator.Generator;
import eu.diversify.trio.core.statistics.AverageNodalDegree; 
import eu.diversify.trio.core.statistics.Density;
import eu.diversify.trio.data.DataSet;
import eu.diversify.trio.performance.util.Task;
import eu.diversify.trio.performance.util.TaskFactory;
import eu.diversify.trio.simulation.RandomFailureSequence;
import eu.diversify.trio.simulation.Scenario;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generate simulation of random models
 */
public class SimulationFactory implements TaskFactory {

    private final Setup setup;
    private final Random random;
    private final Generator generate;
    private final Trio trio;

    public SimulationFactory() {
        this(new Setup());
    }

    public SimulationFactory(Setup setup) {
        this.setup = setup;
        this.random = new Random();
        this.generate = new Generator();
        this.trio = new Trio();
    }

    @Override
    public Task prepareNewTask() {
        Logger.getAnonymousLogger().log(Level.FINEST, "MAX SIZE = {0}", setup.getMaximumAssemblySize());
        int size = setup.getMinimumAssemblySize() + random.nextInt(setup.getMaximumAssemblySize() - setup.getMinimumAssemblySize());
        double edgeProbability = setup.getMinimumEdgeProbability() + (setup.getMaximumEdgeProbability() - setup.getMinimumEdgeProbability()) * random.nextDouble();
        final Assembly assembly = generate.assembly(size, edgeProbability);
        return new SimulationTask(trio, assembly);
    }

    /**
     * Run one simulation of the given assembly
     */
    private static class SimulationTask implements Task {

        private static final String SIZE = "size";
        private static final String AVERAGE_NODAL_DEGREE = "average nodal degree";
        private static final String DENSITY = "density";
        private static final String ROBUSTNESS = "robustness";

        private final Trio trio;
        private final Scenario scenario;
        private final Map<String, Object> properties;
        private DataSet result;

        public SimulationTask(Trio trio, Assembly assembly) {
            this.scenario = new RandomFailureSequence(assembly);
            this.trio = trio;

            this.properties = new HashMap<>();
            measureSize(assembly);
            measureDensity(assembly);
            measureAverageNodalDegree(assembly);
        }

        private void measureAverageNodalDegree(Assembly assembly) {
            AverageNodalDegree averageNodalDegree = new AverageNodalDegree();
            evaluate(averageNodalDegree).on(assembly);
            this.properties.put(AVERAGE_NODAL_DEGREE, averageNodalDegree.getValue());
        }

        private void measureDensity(Assembly assembly) {
            Density density = new Density();
            evaluate(density).on(assembly);
            this.properties.put(DENSITY, density.getValue());
        }

        private void measureSize(Assembly assembly) {
            this.properties.put(SIZE, assembly.size());
        }

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
