/**
 *
 * This file is part of TRIO.
 *
 * TRIO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO.  If not, see <http://www.gnu.org/licenses/>.
 */
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
