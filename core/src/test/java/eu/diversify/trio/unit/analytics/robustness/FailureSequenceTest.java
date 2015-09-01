/**
 * ====
 *     This file is part of TRIO :: Core.
 *
 *     TRIO :: Core is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     TRIO :: Core is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with TRIO :: Core.  If not, see <http://www.gnu.org/licenses/>.
 * ====
 *
 * ====
 *     This file is part of TRIO.
 *
 *     TRIO is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     TRIO is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with TRIO.  If not, see <http://www.gnu.org/licenses/>.
 * ====
 *
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
 * This file is part of TRIO :: Core.
 *
 * TRIO :: Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Core.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
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
    
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNegativeLength() {
        new FailureSequence(1, 5, -6);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectIncreasingSurvivorCount() {
        FailureSequence sequence = new FailureSequence(1, 5, 6);
        sequence.record("A", 1D, 4);
        sequence.record("B", 2D, 5);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectRecordExceedingTheDuration() {
        FailureSequence sequence = new FailureSequence(1, 5, 6);
        sequence.record("A", 1D, 4);
        sequence.record("B", 6+1, 5);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectAComponentFailingTwice() {
        FailureSequence sequence = new FailureSequence(1, 5, 6);
        sequence.record("A", 1D, 4);
        sequence.record("A", 2D, 4);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectTimeMovingBackwards() {
        FailureSequence sequence = new FailureSequence(1, 5, 6);
        sequence.record("A", 2D, 4);
        sequence.record("B", 1D, 4);
    }

    @Test
    public void noImpactShouldLeadToARobustnessOfOne() {
        FailureSequence sequence = new FailureSequence(1, 4, 5);
        sequence.record("A", 1D, 4);
        sequence.record("B", 2D, 4);
        sequence.record("C", 3D, 4);
        sequence.record("D", 4D, 4);

        assertThat(sequence.robustness(), is(closeTo(20D, 1e-6)));
        assertThat(sequence.normalizedRobustness(), is(closeTo(1D, 1e-6)));
    }

    @Test
    public void someImpactShouldLeadToARobustness() {
        FailureSequence sequence = new FailureSequence(1, 2, 3);
        sequence.record("A", 1D, 1);
        sequence.record("B", 2D, 0);

        assertThat(sequence.normalizedRobustness(), is(closeTo(0.25, 1e-6)));
    }

    @Test
    public void fullImpactShouldLeadToARobustnessOfZero() {
        FailureSequence sequence = new FailureSequence(1, 5, 5);
        sequence.record("A", 1D, 0);

        assertThat(sequence.normalizedRobustness(), is(closeTo(0D, 1e-6)));
    }

}
