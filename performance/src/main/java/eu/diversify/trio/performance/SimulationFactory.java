package eu.diversify.trio.performance;

import eu.diversify.trio.Trio;
import eu.diversify.trio.core.Assembly;
import static eu.diversify.trio.core.Evaluation.evaluate;
import eu.diversify.trio.core.random.Generator;
import eu.diversify.trio.core.statistics.Density;
import eu.diversify.trio.performance.util.Task;
import eu.diversify.trio.performance.util.TaskFactory;
import eu.diversify.trio.simulation.RandomFailureSequence;
import eu.diversify.trio.simulation.Scenario;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
        this.setup = new Setup();
        this.random = new Random();
        this.generate = new Generator();
        this.trio = new Trio();
    }

    @Override
    public Task prepareNewTask() {
        int size = setup.getMinimumAssemblySize() + random.nextInt(setup.getMaximumAssemblySize() - setup.getMinimumAssemblySize());
        final double margin = 0.2;
        double edgeProbability = margin + (1. - 2 * margin) * random.nextDouble();
//        final Distribution meanValenceDistribution = Distribution.uniform(0, size);
//        final double mean = meanValenceDistribution.sample();
        //final Distribution density = Distribution.normal(size / 2 + 1, size / 6 + 1);
        final Assembly assembly = generate.assembly(size, edgeProbability);
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
            Density density = new Density();
            evaluate(density).on(system);
            properties.put("density", density.getValue());
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
