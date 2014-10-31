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
import eu.diversify.trio.codecs.SyntaxError;
import eu.diversify.trio.core.System;
import eu.diversify.trio.core.random.Generator;
import eu.diversify.trio.core.statistics.Statistics;
import eu.diversify.trio.filter.TaggedAs;
import eu.diversify.trio.simulation.*;
import eu.diversify.trio.util.random.Distribution;
import java.io.*;
import java.util.Random;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static eu.diversify.trio.core.Evaluation.evaluate;
import static eu.diversify.trio.simulation.actions.AbstractAction.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Monitor regression regarding performance, i.e., response time
 */
@RunWith(JUnit4.class)
@Ignore
public class PerformanceIT {

    private final Trio trio;

    public PerformanceIT() {
        this.trio = new Trio();
    }

    @Test
    public void responseTimeOfFixedSequence() throws IOException, FileNotFoundException, SyntaxError {

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
    public void responseTimeOfRandomSequence() throws IOException, FileNotFoundException, SyntaxError {

        System system = trio.loadSystemFrom("src/test/resources/samples/sensapp_topo4.trio");
        RandomFailureSequence scenario = new RandomFailureSequence(system, new TaggedAs("service"), new TaggedAs("platform"));

        final double ONE_MS = 1D;
        final double averageDuration = averageDurationOf(scenario, 10000);
        assertThat(averageDuration, is(lessThan(ONE_MS)));
        java.lang.System.out.printf("Random sequence duration: %.6f ms\r\n", averageDuration);
    }

    @Test
    public void simulationOfOneLargeModel() {
        final int SYSTEM_SIZE = 10000;

        final Generator generate = new Generator();
        final System system = generate.system(SYSTEM_SIZE, Distribution.uniform(0, SYSTEM_SIZE));

        final Scenario scenario = new RandomFailureSequence(system);
        long duration = durationOf(scenario);

        java.lang.System.out.printf("Simulating failure sequences on a system with %d component(s) in %d ms\n", SYSTEM_SIZE, duration);
    }

    @Test
    public void scalability() throws FileNotFoundException {
        final PrintStream log = new PrintStream(new FileOutputStream("target/scalability.csv"));
        log.println("duration,size,density,disjunction,conjunction,negation");

        final Generator generate = new Generator();

        final Random random = new Random();

        final int MAX_COMPONENT_COUNT = 1000;
        final int MIN_COMPONENT_COUNT = 1;
        final int runCount = 500; 

        for (int i = 0; i < runCount; i++) {
            int size = MIN_COMPONENT_COUNT + random.nextInt(MAX_COMPONENT_COUNT - MIN_COMPONENT_COUNT);
            final Distribution meanValenceDistribution = Distribution.uniform(0, size);
            final double mean = meanValenceDistribution.sample();
            final Distribution density = Distribution.normal(mean, mean / 4);
            final System system = generate.system(size, density);
            final Scenario scenario = new RandomFailureSequence(system);
            double duration = durationOf(scenario);

            final Statistics stats = new Statistics();
            evaluate(stats).on(system);
            
            log.print(duration);
            log.print(",");
            log.print(system.size());
            log.print(",");
            log.print(stats.toCsvENtry());
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
