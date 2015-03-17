package eu.diversify.trio.performance.util;

/**
 * Generate 'n' models and simulate random extinction failures on them, measuring
 * the time it takes to simulate failures.
 */
public class MicroBenchmark {

    private final int warmupCount;
    private final int sampleCount;
    private final TaskFactory factory;
    private final Recorder output;

    public MicroBenchmark(int sampleCount, int warmUpCount, TaskFactory factory, Recorder output) {
        warmupCount = warmUpCount;
        this.sampleCount = sampleCount;
        this.factory = factory;
        this.output = output;
    }
    
    public void run() {
        warmUp();
        benchmark();
    }

    private void warmUp() {
        for(int sampleIndex = 0 ; sampleIndex < warmupCount ; sampleIndex++) {
            Task task = factory.prepareNewTask();
            task.execute();
        }
    }
    
    private void benchmark() {
        for (int index = 0; index < sampleCount; index++) {
            Task task = factory.prepareNewTask();
            Performance performance = monitorExecutionOf(task);
            output.record(index, task, performance);
        }
    }

    private Performance monitorExecutionOf(Task task) {
        long start = System.currentTimeMillis();
        task.execute();
        long end = System.currentTimeMillis();
        long duration = end - start;
        return new Performance(duration);
    }

}
