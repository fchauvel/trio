package eu.diversify.trio.performance.util;

/**
 * Run a sequence of tasks and measure their duration. It send
 */
public class MicroBenchmark {

    private final int warmupCount;
    private final int sampleCount;
    private final TaskFactory factory;

    public MicroBenchmark(int sampleCount, int warmUpCount, TaskFactory factory) {
        warmupCount = warmUpCount;
        this.sampleCount = sampleCount;
        this.factory = factory;
    }
    
    public void run(Recorder output) {
        warmUp();
        factory.reset();
        benchmark(output);
    }

    private void warmUp() {
        for(int sampleIndex = 0 ; sampleIndex < warmupCount ; sampleIndex++) {
            Task task = factory.nextTask();
            task.execute();
            EventBroker.instance().taskCompleted(sampleIndex, warmupCount-1, true);
        }
    }
    
    private void benchmark(Recorder output) {
        for (int index = 0; index < sampleCount; index++) {
            Task task = factory.nextTask();
            Performance performance = monitorExecutionOf(task);
            EventBroker.instance().taskCompleted(index, sampleCount-1, false);
            output.record(index, task, performance);
        }
    }

    private Performance monitorExecutionOf(Task task) {
        long start = System.nanoTime();
        task.execute();
        long end = System.nanoTime();
        long duration = end - start;
        return new Performance(duration);
    }

}
