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

import eu.diversify.trio.analysis.RelativeRobustness;
import eu.diversify.trio.analysis.Robustness;
import eu.diversify.trio.data.Dispatcher;
import eu.diversify.trio.data.Trace;
import eu.diversify.trio.simulation.actions.AbstractAction;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static eu.diversify.trio.simulation.actions.AbstractAction.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@RunWith(JUnit4.class)
public class RelativeRobustnessTest extends TestCase {

    @Test
    public void idealCaseShouldBe1() {
        final Trace trace = new Trace(10, 5);
        trace.record(inactivate("A"), 10, 4);
        trace.record(inactivate("B"), 10, 3);
        trace.record(inactivate("C"), 10, 2);
        trace.record(inactivate("D"), 10, 1);
        trace.record(inactivate("E"), 10, 0);

        final Robustness robustness = new Robustness();
        final RelativeRobustness relativeRobustness = new RelativeRobustness(robustness);
        final Dispatcher metric = new Dispatcher(robustness, relativeRobustness);
        trace.accept(metric);

        assertThat(relativeRobustness.value(), is(closeTo(1D, 1e-6)));
    }

    @Test
    public void worstCaseShouldBe0() {
        final Trace trace = new Trace(10, 5);
        trace.record(AbstractAction.inactivate("A"), 0, 4);

        final Robustness robustness = new Robustness();
        final RelativeRobustness relativeRobustness = new RelativeRobustness(robustness);
        final Dispatcher metric = new Dispatcher(robustness, relativeRobustness);
        trace.accept(metric);

        assertThat(relativeRobustness.value(), is(closeTo(0D, 1e-6)));
    }

    @Test
    public void regularCaseShouldBeBetween0And1() {
        final Trace trace = new Trace(10, 5);
        trace.record(AbstractAction.inactivate("A"), 8, 4);
        trace.record(AbstractAction.inactivate("B"), 6, 3);
        trace.record(AbstractAction.inactivate("C"), 4, 2);
        trace.record(AbstractAction.inactivate("D"), 2, 1);
        trace.record(AbstractAction.inactivate("E"), 0, 0);

        final Robustness robustness = new Robustness();
        final RelativeRobustness relativeRobustness = new RelativeRobustness(robustness);
        final Dispatcher metric = new Dispatcher(robustness, relativeRobustness);
        trace.accept(metric);

        final double value = relativeRobustness.value();
        assertThat(value, is(both(greaterThan(0D)).and(lessThan(1D))));
        assertThat(value, is(closeTo(.5, 1e-6)));
    }

}
