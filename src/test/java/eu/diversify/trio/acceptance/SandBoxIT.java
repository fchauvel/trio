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
package eu.diversify.trio.acceptance;

import eu.diversify.trio.Trio;
import eu.diversify.trio.core.Generator;
import eu.diversify.trio.simulation.RandomFailureSequence;
import eu.diversify.trio.simulation.Scenario;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *
 */
@RunWith(JUnit4.class)
public class SandBoxIT extends TestCase {

    private final Trio trio;

    public SandBoxIT() {
        this.trio = new Trio();
    }

    @Test
    public void onOneLargeModel() {
        final Generator generate = new Generator(0.75);
        final eu.diversify.trio.core.System system = generate.randomSystem(10000);
        final Scenario scenario = new RandomFailureSequence(system);
        long duration = 0;
        final int RUN_COUNT = 5;
        for (int i = 0; i < RUN_COUNT; i++) {
            duration += durationOf(scenario);
        }
        java.lang.System.out.printf("Duration: %.3f ms\n", ((double) duration) / RUN_COUNT);
    }

    public long durationOf(Scenario scenario) {
        long start = java.lang.System.currentTimeMillis();
        trio.run(scenario, 1);
        long end = java.lang.System.currentTimeMillis();
        long duration = end - start;
        return duration;
    }

    public double averageDurationOf(Scenario scenario, int runCount) {
        long total = 0L;
        for (int i = 0; i < runCount; i++) {
            total += durationOf(scenario);
        }
        return total * (1D / runCount);
    }
}
