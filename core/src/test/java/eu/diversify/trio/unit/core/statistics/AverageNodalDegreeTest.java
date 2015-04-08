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
package eu.diversify.trio.unit.core.statistics;

import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.core.Component;
import static eu.diversify.trio.core.Evaluation.evaluate;
import static eu.diversify.trio.core.requirements.Factory.require;
import eu.diversify.trio.core.statistics.AverageNodalDegree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Specification of the average node degree (k-bar), commonly defined as 2*m /
 * n, where m is the number of link and n is the number of node is a graph.
 */
@RunWith(Parameterized.class)
public class AverageNodalDegreeTest {

    private static final double ERROR_TOLERANCE = 1e-6;

    private final String testName;
    private final Assembly assembly;
    private final AverageNodalDegree averageNodalDegree;
    private final double expectedValue;

    public AverageNodalDegreeTest(String testName, Assembly assembly, double expectedValue) {
        this.testName = testName;
        this.assembly = assembly;
        this.averageNodalDegree = new AverageNodalDegree();
        this.expectedValue = expectedValue;
    }

    @Test
    public void shouldComputeAverageNodalDegreeProperly() {
        evaluate(averageNodalDegree).on(assembly);
        assertThat(averageNodalDegree.getValue(), is(closeTo(expectedValue, ERROR_TOLERANCE)));
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> examples() {
        final List<Object[]> examples = new ArrayList<Object[]>();

        examples.add(new Object[]{
            "A",
            new Assembly(
            new Component("A")
            ),
            0D
        });

        examples.add(new Object[]{
            "A requires B",
            new Assembly(
            new Component("A", require("B")),
            new Component("B")
            ),
            1
        });

        examples.add(new Object[]{
            "A requires B or C",
            new Assembly(
            new Component("A", require("B").or(require("C"))),
            new Component("B"),
            new Component("C")
            ),
            2D * 2 / 3
        });

        examples.add(new Object[]{
            "Ring topology (n=3)",
            new Assembly(
            new Component("A", require("B")),
            new Component("B", require("C")),
            new Component("C", require("A"))
            ),
            2
        });

        return examples;
    }
}
