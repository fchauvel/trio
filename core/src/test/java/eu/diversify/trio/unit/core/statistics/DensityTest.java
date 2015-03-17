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

import eu.diversify.trio.core.Component;
import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.core.statistics.Density;
import java.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static eu.diversify.trio.core.Evaluation.evaluate;
import static eu.diversify.trio.core.requirements.Factory.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Specifications of the density calculator
 */
@RunWith(Parameterized.class)
public class DensityTest {

    private final String testName;
    private final Assembly system;
    private final Density density;
    private final double expectedDensity;

    public DensityTest(String testName, Assembly system, double expectedDensity) {
        this.testName = testName;
        this.system = system;
        this.density = new Density();
        this.expectedDensity = expectedDensity;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> examples() {
        final List<Object[]> examples = new ArrayList<Object[]>();

        examples.add(new Object[]{
            "Maximum",
            new Assembly(
            new Component("A", require("A").and(require("B"))),
            new Component("B", require("B").and(require("A")))
            ),
            1D});

        examples.add(new Object[]{
            "Minimum",
            new Assembly(
            new Component("A", nothing()),
            new Component("B", nothing())
            ),
            0D});
        
        examples.add(new Object[]{
            "Middle range value",
            new Assembly(
            new Component("A", require("B")),
            new Component("B", nothing())
            ),
            0.25});

        return examples;
    }

    private static final double TOLERANCE = 1e-8;
    
    @Test
    public void densityShouldBeCorrectlyComputed() {

        evaluate(density).on(system);

        assertThat(density.getValue(), is(closeTo(expectedDensity, TOLERANCE)));
    }

}
