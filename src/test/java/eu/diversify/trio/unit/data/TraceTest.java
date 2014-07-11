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
package eu.diversify.trio.unit.data;

import eu.diversify.trio.data.Trace;

import static eu.diversify.trio.simulation.actions.AbstractAction.*;

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
