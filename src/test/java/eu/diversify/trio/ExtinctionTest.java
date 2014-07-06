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

import static eu.diversify.trio.Action.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Specification of the extinction sequence
 */
@RunWith(JUnit4.class)
public class ExtinctionTest extends TestCase {

    @Test
    public void impactShouldBeCorrect() {
        final int headcount = 3;
        Extinction sequence = new Extinction(headcount);

        sequence.record(reviveAll(), 3);
        sequence.record(kill("x"), 2);
        sequence.record(kill("y"), 0);

        assertThat(sequence.impactOf(kill("x")), is(equalTo(1)));
        assertThat(sequence.impactOf(kill("y")), is(equalTo(2)));
    }
    
    
     @Test
    public void impactsShouldProvideACorrectMapping() {
        final int headcount = 3;
        Extinction sequence = new Extinction(headcount);

        sequence.record(reviveAll(), 3);
        sequence.record(kill("x"), 2);
        sequence.record(kill("y"), 0);

        assertThat(sequence.impacts().get(kill("x")), is(equalTo(1)));
        assertThat(sequence.impacts().get(kill("y")), is(equalTo(2)));
    }

    @Test
    public void robustnessOf30ShouldBeHalf() {
        int headcount = 3;
        final Extinction sequence = new Extinction(headcount);

        sequence.record(reviveAll(), headcount);
        sequence.record(kill("A"), 0);

        assertThat("Robustness of " + sequence.toString(), sequence.robustness(), is(closeTo(100D / 2, 1e-6)));
    }

    @Test
    public void robustnessOf320ShouldBeFiveSixth() {
        int headcount = 3;
        final Extinction sequence = new Extinction(headcount);

        sequence.record(reviveAll(), headcount);
        sequence.record(kill("A"), 2);
        sequence.record(kill("B"), 0);

        assertThat("Robustness of " + sequence.toString(), sequence.robustness(), is(closeTo(500D / 6, 1e-6)));
    }

    @Test
    public void robustnessOf321ShouldBe100() {
        int headcount = 3;
        final Extinction sequence = new Extinction(headcount);

        sequence.record(reviveAll(), headcount);
        sequence.record(kill("A"), 2);
        sequence.record(kill("B"), 1);
        sequence.record(kill("C"), 0);

        assertThat("Robustness of " + sequence.toString(), sequence.robustness(), is(closeTo(100D, 1e-6)));
    }

}
