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
package eu.diversify.trio.unit.analytics.robustness;

import eu.diversify.trio.analytics.robustness.FailureSequence;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

/**
 * Failure Sequence test
 */
public class FailureSequenceTest {

    @Test
    public void noImpactShouldLeadToARobustnessOfOne() {
        FailureSequence sequence = new FailureSequence(1, 5, 5);
        sequence.record("A", 5);
        sequence.record("B", 5);
        sequence.record("C", 5);
        sequence.record("D", 5);
        sequence.record("E", 5);
        
        assertThat(sequence.normalizedRobustness(), is(closeTo(1D, 1e-6)));
    }
    
    @Test
    public void fullImpactShouldLeadToARobustnessOfZero() {
        FailureSequence sequence = new FailureSequence(1, 5, 5);
        sequence.record("A", 0);
        
        assertThat(sequence.normalizedRobustness(), is(closeTo(0D, 1e-6)));
    }
    
    
}
