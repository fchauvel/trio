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
package eu.diversify.trio.acceptance;

import eu.diversify.trio.Trio;
import eu.diversify.trio.core.Generator;
import eu.diversify.trio.filter.TaggedAs;

import static eu.diversify.trio.simulation.actions.AbstractAction.*;

import eu.diversify.trio.simulation.FixedFailureSequence;
import eu.diversify.trio.core.System;
import eu.diversify.trio.simulation.RandomFailureSequence;
import eu.diversify.trio.simulation.Scenario;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;
import org.junit.Ignore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Monitor regression regarding performance, i.e., response time
 */
@RunWith(JUnit4.class)
public class PerformanceIT {

    private final Trio trio;

    public PerformanceIT() {
        this.trio = new Trio();
    }

    @Test
    public void responseTimeOfFixedSequence() throws IOException {
        final Trio trio = new Trio();

        System system = trio.loadSystemFrom("src/test/resources/samples/sensapp_topo4.trio");
        FixedFailureSequence scenario = new FixedFailureSequence(system, new TaggedAs("service"), new TaggedAs("platform"),
                                                                 inactivate("JRE1"),
                                                                 inactivate("DB2"),
                                                                 inactivate("DB1"),
                                                                 inactivate("SC2"));

        final double ONE_MS = 1D;
        final double averageDuration = averageDurationOf(scenario, 10000);
        assertThat(averageDuration, is(lessThan(ONE_MS)));
        java.lang.System.out.printf("Fixed sequence duration: %.6f ms\r\n", averageDuration);
    }

    @Test
    public void responseTimeOfRandomSequence() throws IOException {
        final Trio trio = new Trio();

        System system = trio.loadSystemFrom("src/test/resources/samples/sensapp_topo4.trio");
        RandomFailureSequence scenario = new RandomFailureSequence(system, new TaggedAs("service"), new TaggedAs("platform"));

        final double ONE_MS = 1D;
        final double averageDuration = averageDurationOf(scenario, 10000);
        assertThat(averageDuration, is(lessThan(ONE_MS)));
        java.lang.System.out.printf("Random sequence duration: %.6f ms\r\n", averageDuration);
    }

    @Test
    public void onOneLargeModel() {
        final Generator generate = new Generator(0.75);
        final System system = generate.randomSystem(10000);
        final Scenario scenario = new RandomFailureSequence(system);
        long duration = durationOf(scenario);
        java.lang.System.out.printf("Duration: %d ms\n", duration);

    }

    @Test
    public void scalability() throws FileNotFoundException {
        final PrintStream log = new PrintStream(new FileOutputStream("target/scalability.csv"));
        log.println("size,duration,complexity");

        final Random random = new Random();

        for (int i = 0; i < 500; i++) {
            int size = 10 + random.nextInt(10000);
            final double alpha = 0.5 + random.nextDouble() * 0.4;
            final Generator generate = new Generator(alpha);
            final System system = generate.randomSystem(size);
            final Scenario scenario = new RandomFailureSequence(system);
            double duration = averageDurationOf(scenario, 50);
            log.print(size);
            log.print(",");
            log.print(duration);
            log.print(",");
            log.println(system.getMeanComplexity());
        }

        log.close();
    }

    public double averageDurationOf(Scenario scenario, int runCount) {
        long total = 0L;
        for (int i = 0; i < runCount; i++) {
            total += durationOf(scenario);
        }
        return total * (1D / runCount);
    }

    public long durationOf(Scenario scenario) {
        long start = java.lang.System.currentTimeMillis();
        trio.run(scenario, 1);
        long end = java.lang.System.currentTimeMillis();
        long duration = end - start;
        return duration;
    }

}
