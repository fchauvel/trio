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
package eu.diversify.trio.unit;

import static eu.diversify.trio.Samples.*;

import eu.diversify.trio.Trio;
import eu.diversify.trio.analysis.Analysis;
import eu.diversify.trio.analysis.RelativeRobustness;
import eu.diversify.trio.analysis.Robustness;
import eu.diversify.trio.data.DataSet;
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
    private final Analysis analysis;
    private final String metric;
    private final double expectedValue;

    public TrioTest(String name, Scenario scenario, Analysis analysis, String metric, double expectedValue) {
        this.name = name;
        this.scenario = scenario;
        this.analysis = analysis;
        this.metric = metric;
        this.expectedValue = expectedValue;
    }

    @Test
    public void test() {
        final Trio sut = new Trio();
        DataSet trace = sut.run(scenario, 1000);
        trace.accept(analysis);
        final double actual = analysis.metric(metric).distribution().mean();
        assertThat(actual, is(closeTo(expectedValue, 1e-1)));
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> examples() {
        final Collection<Object[]> results = new ArrayList<Object[]>();

        results.add(new Object[]{
            "R(A -> B or C)",
            new RandomFailureSequence(A_require_B_or_C()),
            robustness(),
            Robustness.NAME,
            6 * (4D / 6) + 5 * (2D / 6)
        });

        results.add(new Object[]{
            "R(A -> B and C)",
            new RandomFailureSequence(A_require_B_and_C()),
            robustness(),
            Robustness.NAME,
            (2D / 3) * 4 + (1D / 3) * 6
        });

        results.add(new Object[]{
            "R(A -> B -> C -> A)",
            new RandomFailureSequence(ABC_with_circular_dependencies()),
            robustness(),
            Robustness.NAME,
            3D});
 
        results.add(new Object[]{
            "R(A -> B -> C)",
            new RandomFailureSequence(ABC_with_linear_dependencies()),
            robustness(),
            Robustness.NAME,
            (1D / 6) * 6 + (1D / 6) * 5 + (1D / 3) * 4 + (1D / 3) * 3});

        //
        //
        // Normalized robustness
        results.add(new Object[]{
            "NR(A -> B or C)",
            new RandomFailureSequence(A_require_B_or_C()),
            normalizedRobustness(),
            RelativeRobustness.NAME,
            (3D / 9) * (4D / 6) + (2D / 9) * (2D / 6)
        });

        results.add(new Object[]{
            "NR(A -> B and C)",
            new RandomFailureSequence(A_require_B_and_C()),
            normalizedRobustness(),
            RelativeRobustness.NAME,
            (2D / 3) * (1D / 9) + (1D / 3) * (3D / 9)
        });

        results.add(new Object[]{
            "NR(A -> B -> C -> A)",
            new RandomFailureSequence(ABC_with_circular_dependencies()),
            normalizedRobustness(),
            RelativeRobustness.NAME,
            0D});

        results.add(new Object[]{
            "NR(A -> B -> C)",
            new RandomFailureSequence(ABC_with_linear_dependencies()),
            normalizedRobustness(),
            RelativeRobustness.NAME,
            (1D / 6) * (3D/9) + (1D / 6) * (2D/9) + (1D / 3) * (1D/9) });
        
        return results;
    }

    public static Analysis robustness() {
        final Robustness robustness = new Robustness();
        return new Analysis(robustness);
    }

    public static Analysis normalizedRobustness() {
        final Robustness robustness = new Robustness();
        final RelativeRobustness normalizedRobustness = new RelativeRobustness(robustness);
        return new Analysis(robustness, normalizedRobustness);
    }

}
