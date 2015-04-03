package eu.diversify.trio.performance.util;

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

    // TODO should reject wrong sample count
    
    @Test
    public void testBehaviour() {
        final int SAMPLE_COUNT = 10;
        final int WARMUP_COUNT = 5;
        final int TOTAL_COUNT = SAMPLE_COUNT + WARMUP_COUNT;

        final Task aTask = context.mock(Task.class);
        final TaskFactory tasks = context.mock(TaskFactory.class);
        final Recorder trace = context.mock(Recorder.class);

        context.checking(new Expectations() {
            {
                exactly(TOTAL_COUNT).of(tasks).prepareNewTask(); will(returnValue(aTask));

                exactly(TOTAL_COUNT).of(aTask).execute();

                exactly(SAMPLE_COUNT).of(trace).record(with(any(Integer.class)), with(aTask), with(any(Performance.class)));
            }
        });
        
        MicroBenchmark benchmark = new MicroBenchmark(SAMPLE_COUNT, WARMUP_COUNT, tasks);
        benchmark.run(trace);
        
        context.assertIsSatisfied();
    }

}
