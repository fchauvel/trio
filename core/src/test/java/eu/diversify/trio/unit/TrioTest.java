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
package eu.diversify.trio.unit;

import static eu.diversify.trio.Samples.*;

import eu.diversify.trio.simulation.RandomFailureSequence;
import eu.diversify.trio.simulation.Scenario;
import java.util.ArrayList;
import java.util.Collection;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 */
@RunWith(Parameterized.class)
public class TrioTest extends TestCase {

    private final String name;
    private final Scenario scenario;
    private final double expectedValue;

    public TrioTest(String name, Scenario scenario, double expectedValue) {
        this.name = name;
        this.scenario = scenario;
        this.expectedValue = expectedValue;
    }

    @Test
    public void test() {
        final TrioService sut = new TrioService();
        TrioService.TrioResponse response = sut.run(scenario, 1000);
        assertThat(response.robustness(), is(closeTo(expectedValue, 1e-1)));
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> examples() {
        final Collection<Object[]> results = new ArrayList<Object[]>();

        results.add(new Object[]{
            "NR(A -> B or C)",
            new RandomFailureSequence(1, A_require_B_or_C()),
            (3D / 9) * (4D / 6) + (2D / 9) * (2D / 6)
        });

        results.add(new Object[]{
            "NR(A -> B and C)",
            new RandomFailureSequence(1, A_require_B_and_C()),
            (2D / 3) * (1D / 9) + (1D / 3) * (3D / 9)
        });

        results.add(new Object[]{
            "NR(A -> B -> C -> A)",
            new RandomFailureSequence(1, ABC_with_circular_dependencies()),
            0D});

        results.add(new Object[]{
            "NR(A -> B -> C)",
            new RandomFailureSequence(1, ABC_with_linear_dependencies()),
            (1D / 6) * (3D / 9) + (1D / 6) * (2D / 9) + (1D / 3) * (1D / 9)});

        return results;
    }

}
