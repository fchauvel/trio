package eu.diversify.trio.performance;

import eu.diversify.trio.Trio;
import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.core.random.Generator;
import eu.diversify.trio.performance.util.Task;
import eu.diversify.trio.performance.util.TaskFactory;
import eu.diversify.trio.simulation.RandomFailureSequence;
import eu.diversify.trio.simulation.Scenario;
import eu.diversify.trio.util.random.Distribution;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Generate simulation of random models
 */
public class SimulationFactory implements TaskFactory {

    private static final int DEFAULT_MAX_ASSEMBLY_SIZE = 500;
    private static final int DEFAULT_MIN_ASSEMBLY_SIZE = 10;

    private final int minAssemblySize;
    private final int maxAssemblySize;
    private final Random random;
    private final Generator generate;
    private final Trio trio;

    public SimulationFactory() {
        this(DEFAULT_MIN_ASSEMBLY_SIZE, DEFAULT_MAX_ASSEMBLY_SIZE);
    }

    public SimulationFactory(int minAssemblySize, int maxAssemblySize) {
        this.minAssemblySize = minAssemblySize;
        this.maxAssemblySize = maxAssemblySize;
        this.random = new Random();
        this.generate = new Generator();
        this.trio = new Trio();
    }

    @Override
    public Task prepareNewTask() {
        int size = minAssemblySize + random.nextInt(maxAssemblySize - minAssemblySize);
        final Distribution meanValenceDistribution = Distribution.uniform(0, size);
        final double mean = meanValenceDistribution.sample();
        final Distribution density = Distribution.normal(mean, mean / 4);
        final Assembly assembly = generate.assembly(size, density);
        return new SimulationTask(trio, assembly);
    }

    /**
     * Run one simulation of the given assembly
     */
    private static class SimulationTask implements Task {

        private final Trio trio;
        private final Scenario scenario;
        private final Map<String, Object> properties;

        public SimulationTask(Trio trio, Assembly system) {
            this.scenario = new RandomFailureSequence(system);
            this.trio = trio;

            properties = new HashMap<>();
            properties.put("size", system.size());
        }

        @Override
        public void execute() {
            trio.run(scenario);
        }

        @Override
        public Map<String, Object> getProperties() {
            return this.properties;
        }

    }
}