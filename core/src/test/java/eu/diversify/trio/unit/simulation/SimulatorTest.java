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
/**
 *
 * This file is part of TRIO.
 *
 * TRIO is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * TRIO is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO. If not, see <http://www.gnu.org/licenses/>.
 */
package eu.diversify.trio.unit.simulation;

import eu.diversify.trio.simulation.FixedFailureSequence;
import eu.diversify.trio.Samples;
import eu.diversify.trio.analysis.Robustness;
import eu.diversify.trio.simulation.Topology;
import eu.diversify.trio.data.DataSet;
import eu.diversify.trio.simulation.RandomFailureSequence;

import static eu.diversify.trio.simulation.actions.Inactivate.*;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Specification of a simulation
 */
@RunWith(JUnit4.class)
public class SimulatorTest extends TestCase {

    
    @Test
    public void predefinedSequencesShouldRunJustFine() {
        final FixedFailureSequence simulation = new FixedFailureSequence(Samples.sample1(), inactivate("B"));

        Topology result = simulation.run();

        assertThat("'A' should be inactive", result.isInactive("A"));
        assertThat("active components", result.activeAndObservedComponents(), containsInAnyOrder("C", "D", "E"));
    }

    @Test
    public void test() {
        final FixedFailureSequence simulation = new FixedFailureSequence(Samples.A_require_B(), inactivate("B"));

        final DataSet traces = new DataSet();
        simulation.run(traces);
        assertThat(traces.get(0), is(not(nullValue())));

        final Robustness robustness = new Robustness();
        traces.accept(robustness);

        assertThat(robustness.distribution().mean(), is(closeTo(2D, 1e-6)));
    }

}
