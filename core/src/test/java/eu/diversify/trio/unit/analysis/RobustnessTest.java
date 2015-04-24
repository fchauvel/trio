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

import eu.diversify.trio.simulation.data.Trace;
import eu.diversify.trio.analysis.Robustness;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import static eu.diversify.trio.simulation.actions.AbstractAction.*;

/**
 * Specification of the robustness metric
 */
@RunWith(JUnit4.class)
public class RobustnessTest extends TestCase {

    @Test
    public void aSingleComponentShouldHaveARobustnessOfOne() {
        Trace trace = new Trace(1);
        trace.record(inactivate("A"), 0);
        
        final Robustness robustness = new Robustness();
        trace.accept(robustness);
                
        assertThat(robustness.distribution().mean(), is(closeTo(1D, 1e-6)));
    }

    @Test
    public void threeIndependentComponentsShouldHaveARobustnessOf6() {
        Trace trace = new Trace(3);
        trace.record(inactivate("A"), 2);
        trace.record(inactivate("B"), 1);
        trace.record(inactivate("C"), 0);

        final Robustness robustness = new Robustness();
        trace.accept(robustness);

        assertThat(robustness.distribution().mean(), is(closeTo(6D, 1e-6)));
    }

}
