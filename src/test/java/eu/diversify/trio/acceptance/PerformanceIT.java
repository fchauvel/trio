package eu.diversify.trio.acceptance;

import eu.diversify.trio.Trio;
import eu.diversify.trio.filter.TaggedAs;

import static eu.diversify.trio.simulation.actions.AbstractAction.*;

import eu.diversify.trio.simulation.FixedFailureSequence;
import eu.diversify.trio.simulation.actions.AbstractAction;
import eu.diversify.trio.core.System;
import eu.diversify.trio.simulation.RandomFailureSequence;
import eu.diversify.trio.simulation.Scenario;
import java.io.IOException;

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

    public double averageDurationOf(Scenario scenario, int runCount) {
        long total = 0L;
        for (int i = 0; i < runCount; i++) {
            total += durationOf(trio, scenario);
        }
        return total * (1D / runCount);
    }

    public long durationOf(final Trio trio, Scenario scenario) {
        long start = java.lang.System.currentTimeMillis();
        trio.run(scenario, 1);
        long end = java.lang.System.currentTimeMillis();
        long duration = end - start;
        return duration;
    }

}
