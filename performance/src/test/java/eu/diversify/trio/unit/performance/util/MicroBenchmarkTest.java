package eu.diversify.trio.unit.performance.util;

import eu.diversify.trio.performance.util.MicroBenchmark;
import eu.diversify.trio.performance.util.Task;
import eu.diversify.trio.performance.util.TaskStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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

        context.checking(new Expectations() {
            {                
                ignoring(taskStore).fetch(with(any(Integer.class))); will(returnValue(aTask));
                
                ignoring(aTask).id(); will(returnValue(0)); 
                
                ignoring(aTask).properties(); will(returnValue(new Properties())); 
                
                exactly(TASK_COUNT).of(aTask).execute();
            }
        });

        MicroBenchmark benchmark = new MicroBenchmark(taskStore, tasks);

        benchmark.run();

        context.assertIsSatisfied();
    }

}
