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
package eu.diversify.trio.unit.analysis;

import eu.diversify.trio.analysis.Probability;
import eu.diversify.trio.simulation.data.Trace;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static eu.diversify.trio.simulation.actions.AbstractAction.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Specification of the probability metrics
 */
@RunWith(JUnit4.class)
public class ProbabilityTest extends TestCase {

    @Test
    public void certainShouldBe1() {
        Trace trace = new Trace(10, 1);
        trace.record(inactivate("A"), 0, 0);

        final Probability probability = new Probability();
        trace.accept(probability);

        assertThat(probability.distribution().mean(), is(closeTo(1D, 1e-6)));
    }

    @Test
    public void choosingOneAmong2ShouldGiveAProbabilityOf0_5() {
        Trace trace = new Trace(10, 2);
        trace.record(inactivate("A"), 5, 1);
        trace.record(inactivate("B"), 0, 0);

        final Probability probability = new Probability();
        trace.accept(probability);

        assertThat(probability.distribution().mean(), is(both(greaterThan(0D)).and(lessThan(1D))));
        assertThat(probability.distribution().mean(), is(closeTo(0.5, 1e-6)));
    }
    
     @Test
    public void choosing1_4AndThen1_2ShouldGiveAProbabilityOf0_125() {
        Trace trace = new Trace(10, 4);
        trace.record(inactivate("A"), 5, 2);
        trace.record(inactivate("B"), 0, 0);

        final Probability probability = new Probability();
        trace.accept(probability);

        assertThat(probability.distribution().mean(), is(both(greaterThan(0D)).and(lessThan(1D))));
        assertThat(probability.distribution().mean(), is(closeTo(0.125, 1e-6)));
    }

}
