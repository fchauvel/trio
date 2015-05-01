package eu.diversify.trio.performance.util;

import java.util.ArrayList;
import java.util.List;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

/**
 * Test the behaviour of the simulation speed test
 */
public class MicroBenchmarkTest {

    private final Mockery context;

    public MicroBenchmarkTest() {
        context = new Mockery();
    }

    @Test
    public void testBehaviour() {
        final int TASK_COUNT = 10;

        final List<Integer> tasks = new ArrayList<>(TASK_COUNT);
        final Task aTask = context.mock(Task.class);
        for (int i = 0; i < TASK_COUNT; i++) {
            tasks.add(i);
        }

        final TaskStore taskStore = context.mock(TaskStore.class);
        final Recorder trace = context.mock(Recorder.class);

        context.checking(new Expectations() {
            {                
                ignoring(taskStore).fetch(with(any(Integer.class))); will(returnValue(aTask));
                
                ignoring(aTask).id(); will(returnValue(0)); 
                
                exactly(TASK_COUNT).of(aTask).execute();

                exactly(TASK_COUNT).of(trace).record(with(any(Integer.class)), with(aTask), with(any(Observation.class)));
            }
        });

        MicroBenchmark benchmark = new MicroBenchmark(taskStore, tasks);
        benchmark.run(trace);

        context.assertIsSatisfied();
    }

}
