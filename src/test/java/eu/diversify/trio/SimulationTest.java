package eu.diversify.trio;

import static eu.diversify.trio.actions.Inactivate.*;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static eu.diversify.trio.requirements.Require.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


/**
 * Specification of a simulation
 */
@RunWith(JUnit4.class)
public class SimulationTest extends TestCase {

    
    @Test
    public void predefinedSequencesShouldRunJustFine() {
        final Simulation simulation = new Simulation(Samples.sample1());

        Topology result = simulation.run(inactivate("B"));

        assertThat("'A' should be inactive", result.isInactive("A"));
        assertThat("active components", result.activeComponents(), containsInAnyOrder("C", "D", "E"));
    }

    @Test
    public void test() {
        final System system = Samples.A_require_B();
        final Report traces = new Report();
        final Simulation simulation = new Simulation(system, traces);
 
        final Robustness robustness = new Robustness();
        
        simulation.run(inactivate("B"));
        
        assertThat(robustness.computeOn(traces.get(0)), is(closeTo(2D, 1e-6)));
    }

}
