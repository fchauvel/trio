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

        private final Trio trio;
        private final Scenario scenario;
        private final Map<String, Object> properties;

        public SimulationTask(Trio trio, Assembly assembly) {
            this.scenario = new RandomFailureSequence(assembly);
            this.trio = trio;

            properties = new HashMap<>();
            properties.put("size", assembly.size());
            
            Density density = new Density();
            evaluate(density).on(assembly);
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
