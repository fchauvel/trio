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

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 *
 */
@RunWith(JUnit4.class)
public class DistributionTest extends TestCase {

    @Test
    public void testMinimum() {
        final Distribution sut = new Distribution(1., 2., 3.);

        assertThat(sut.minimum(), is(closeTo(1., 1e-6)));
    }

    @Test
    public void testMaximum() {
        final Distribution sut = new Distribution(1., 2., 3.);

        assertThat(sut.maximum(), is(closeTo(3., 1e-6)));
    }

    @Test
    public void testMean() {
        final Distribution sut = new Distribution(1., 2., 3.);

        assertThat(sut.mean(), is(closeTo(2., 1e-6)));
    }
    
    @Test
    public void testVariance() {
        final Distribution sut = new Distribution(1., 2., 3.);
        
        assertThat(sut.variance(), is(closeTo(2./3, 1e-6)));
    }

        @Test
    public void testStandardDeviation() {
        final Distribution sut = new Distribution(1., 2., 3.);
        
        assertThat(sut.standardDeviation(), is(closeTo(Math.sqrt(2./3), 1e-6)));
    }
    
}
