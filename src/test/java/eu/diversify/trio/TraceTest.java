package eu.diversify.trio;

import eu.diversify.trio.Trace;

import static eu.diversify.trio.actions.AbstractAction.*;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Specification of the trace observed during simulation
 */
@RunWith(JUnit4.class)
public class TraceTest extends TestCase {

    @Test
    public void shouldRecordActivityLevel() {
        final Trace trace = new Trace(10);

        trace.record(inactivate("A"), 8);
        trace.record(inactivate("B"), 6);
        trace.record(inactivate("C"), 0);

        assertThat(trace.length(), is(equalTo(3)));
    }
    
     @Test
    public void shouldGiveAccessToAnyDisruptionLevel() {
        final Trace trace = new Trace(10);

        trace.record(inactivate("A"), 8); 
        trace.record(inactivate("B"), 6);
        trace.record(inactivate("C"), 0);

        assertThat(trace.afterDisruption(2).getActivityLevel(), is(equalTo(6)));
    }

}
