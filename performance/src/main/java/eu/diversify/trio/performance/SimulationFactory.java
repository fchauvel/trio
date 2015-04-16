package eu.diversify.trio.performance;

import eu.diversify.trio.Trio;
import eu.diversify.trio.analysis.Analysis;
import eu.diversify.trio.analysis.RelativeRobustness;
import eu.diversify.trio.core.Assembly;
import static eu.diversify.trio.core.Evaluation.evaluate;
import eu.diversify.trio.core.statistics.AverageNodalDegree; 
import eu.diversify.trio.core.statistics.Density;
import eu.diversify.trio.data.DataSet;
import eu.diversify.trio.generator.AssemblyKind;
import eu.diversify.trio.generator.Generator2;
import eu.diversify.trio.generator.Setup;
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
        final Assembly assembly = generate.nextAssembly(kind);
        return new SimulationTask(trio, kind, assembly);
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

        public SimulationTask(Trio trio, AssemblyKind kind, Assembly assembly) {
            this.properties = new HashMap<>();
            
            this.properties.put("kind", kind.name());
            
            measureSize(assembly);
            measureDensity(assembly);
            measureAverageNodalDegree(assembly);
            
            this.scenario = new RandomFailureSequence(assembly);
            this.trio = trio;           
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
