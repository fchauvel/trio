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
import eu.diversify.trio.core.storage.SyntaxError;
import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.generator.Generator;
import eu.diversify.trio.simulation.RandomFailureSequence;
import eu.diversify.trio.simulation.Scenario;
import eu.diversify.trio.util.random.Distribution;
import java.io.IOException;
import java.util.BitSet;
import java.util.Random;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * A single test to play with
 */
@RunWith(JUnit4.class)
//@Ignore
public class SandBoxIT {

    private final Trio trio;

    public SandBoxIT() {
        this.trio = new Trio();
    }

    @Test
    public void onOneLargeModel() {
        final Generator aRandom = new Generator();
        final Assembly assembly = aRandom.assembly(5000, Distribution.uniform(0D, 10000D));
        final Scenario scenario = new RandomFailureSequence(assembly);
        long duration = 0;
        final int RUN_COUNT = 10;
        for (int i = 0; i < RUN_COUNT; i++) {
            System.out.print(".");
            duration += durationOf(scenario);
        }
        java.lang.System.out.printf("\nDuration: %.3f ms\n", ((double) duration) / RUN_COUNT);
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

    @Test
    public void testAdgacencyMatrixGenerationSpeed() {
        final int SIZE = 10000;
        final int LENGTH = SIZE * SIZE;
        Random random = new Random();
        final int RUN_COUNT = 100;

        long totalDuration = 0;
        for (int run = 0; run < RUN_COUNT; run++) {
            final long start = System.currentTimeMillis();
            final BitSet adjacency = new BitSet(LENGTH);
            for (int i = 0; i < LENGTH; i++) {
                adjacency.set(i, random.nextBoolean());
            }
            final long end = System.currentTimeMillis();
            totalDuration += (end - start);
        }
        System.out.println("Duration [S=" + SIZE + "]: " + totalDuration / RUN_COUNT);

    }
//    @Test
//    public void openstackShouldLoadJustFine() throws IOException, SyntaxError {
//        Trio trio = new Trio();
//        
//        trio.loadSystemFrom("src/test/resources/samples/openstack.trio");
//    }
}
