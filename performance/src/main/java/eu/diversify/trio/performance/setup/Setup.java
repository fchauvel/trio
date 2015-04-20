package eu.diversify.trio.performance.setup;

import eu.diversify.trio.generator.AssemblyKind;
import eu.diversify.trio.performance.FileSimulationFactory;
import eu.diversify.trio.performance.GeneratorSetupRandomizer;
import eu.diversify.trio.performance.SimulationFactory;
import eu.diversify.trio.performance.util.MicroBenchmark;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the configuration of the performance test
 */
public class Setup {

    public static final int DEFAULT_SAMPLE_COUNT = 100;
    public static final int DEFAULT_WARMUP_SAMPLE_COUNT = 10;

    private final int sampleCount;
    private final int warmupSampleCount;
    private final GeneratorSetupRandomizer setups;

    public Setup() {
        this(
                DEFAULT_SAMPLE_COUNT,
                DEFAULT_WARMUP_SAMPLE_COUNT,
                new GeneratorSetupRandomizer()
        );
    }

    public Setup(int sampleCount, int warmupSampleCount, GeneratorSetupRandomizer setups) {
        this.sampleCount = sampleCount;
        this.warmupSampleCount = warmupSampleCount;
        this.setups = setups;
    }

    public List<MicroBenchmark> prepareBenchmarks() {
        final List<MicroBenchmark> benchmarks = new ArrayList<>();
        benchmarks.add(new MicroBenchmark(sampleCount, warmupSampleCount, new FileSimulationFactory()));
        return benchmarks;
    }

    public String summary() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("Setup Summary:");
        buffer.append(" - 3 x ").append(sampleCount).append("( + 3 x ").append(warmupSampleCount).append(" as warmup)");
        return buffer.toString();
    }

}
