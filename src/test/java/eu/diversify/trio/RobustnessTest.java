
package eu.diversify.trio;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import static eu.diversify.trio.actions.AbstractAction.*;

/**
 * Specification of the robustness metric
 */
@RunWith(JUnit4.class)
public class RobustnessTest extends TestCase {
    
    @Test
    public void aSingleComponentShouldHaveARobustnessOfOne() {
        Trace trace = new Trace(1);
        trace.record(inactivate("A"), 0);
        
        double robustness = new Robustness().computeOn(trace);
        
        assertThat(robustness, is(closeTo(1D, 1e-6)));
    }
    
    @Test
    public void threeIndependentComponentsShouldHaveARobustnessOf6() {
        Trace trace = new Trace(3);
        trace.record(inactivate("A"), 2);
        trace.record(inactivate("B"), 1);
        trace.record(inactivate("C"), 0);
        
        double robustness = new Robustness().computeOn(trace);
        
        assertThat(robustness, is(closeTo(6D, 1e-6)));
    }

}
