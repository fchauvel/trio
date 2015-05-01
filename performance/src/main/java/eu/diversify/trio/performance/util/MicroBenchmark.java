package eu.diversify.trio.performance.util;

import java.util.Collection;

/**
 * Run a sequence of tasks and measure their duration. It send
 */
public class MicroBenchmark {

    private final int id;
    private final TaskStore store;
    private final Collection<Integer> tasks;

    public MicroBenchmark(TaskStore store, Collection<Integer> tasks) {
        this(0, store, tasks);
    }

    public MicroBenchmark(int id, TaskStore store, Collection<Integer> tasks) {
        this.id = id;
        this.tasks = tasks;
        this.store = store;
    }

    public int id() {
        return this.id;
    }

    public void run(Recorder output) {
        benchmark(output);
    }

    private void benchmark(Recorder output) {
        for (int eachId : tasks) {
            Task task = store.fetch(eachId);
            Observation performance = monitor(task);
            output.record(task.id(), task, performance);
            EventBroker.instance().taskCompleted(id, task.id(), tasks.size());
        }
    }

    private Observation monitor(Task task) {
        long start = System.nanoTime();
        task.execute();
        long end = System.nanoTime();
        long duration = end - start;
        return new Observation(duration);
    }

}
