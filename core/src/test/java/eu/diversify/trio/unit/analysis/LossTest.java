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
/*
 */

package eu.diversify.trio.unit.analysis;

import eu.diversify.trio.analysis.Loss;
import eu.diversify.trio.data.Trace;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static eu.diversify.trio.simulation.actions.AbstractAction.inactivate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

/**
 *
 */
@RunWith(JUnit4.class)
public class LossTest extends TestCase {

     @Test
    public void aConstantLossesShouldHaveAMeanEqualToTheirValue() {
        Trace trace = new Trace(6);
        trace.record(inactivate("A"), 4);
        trace.record(inactivate("B"), 0);
        
        final Loss loss = new Loss();
        trace.accept(loss);
                
        assertThat(loss.distribution().mean(), is(closeTo(2D, 1e-6)));
    }
    
}
