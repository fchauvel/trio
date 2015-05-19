package eu.diversify.trio.unit.analytics;

import eu.diversify.trio.analytics.robustness.Robustness;
import eu.diversify.trio.analytics.events.Listener;
import eu.diversify.trio.analytics.events.Statistic;
import eu.diversify.trio.analytics.robustness.FailureSequence;
import eu.diversify.trio.simulation.events.Channel;

import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import org.junit.Test;

/**
 * Specification of the robustness calculator
 */
public class RobustnessTest {

    @Test
    public void shouldComputeTheRobustnessProperly() {
        final Channel simulation = new Channel();
        final Collector results = new Collector();
        final Robustness robustness = new Robustness(simulation, results);

        simulation.simulationInitiated(1);
        simulation.sequenceInitiated(1, 1, asList("X", "Y", "Z"), asList("A", "B", "C", "D"));
        simulation.failure(1, 1, "A", new ArrayList<String>());
        simulation.failure(1, 1, "B", new ArrayList<String>());
        simulation.failure(1, 1, "C", new ArrayList<String>());
        simulation.failure(1, 1, "D", new ArrayList<String>());
        simulation.sequenceComplete(1, 1);
        simulation.simulationComplete(1);

        results.assertEquals(1, 1, 12, 1D);

    }

    /**
     * A dummy listener which collects the statistics that are published for
     * later check
     */
    private class Collector implements Listener {

        private final Map<Statistic, Object> values;

        public Collector() {
            values = new HashMap<Statistic, Object>();
        }

        public void statisticReady(Statistic statistic, Object value) {
            values.put(statistic, value);
        }

        public void assertEquals(int scenarioId, int sequenceId, int expected, double normalizedExpected) {
            final FailureSequence sequence = (FailureSequence) values.get(new Statistic(scenarioId, sequenceId, Robustness.KEY_FAILURE_SEQUENCE));
            assertThat(sequence, is(not(nullValue())));
            assertThat(sequence.robustness(), equalTo(expected));
            assertThat(sequence.normalizedRobustness(), equalTo(normalizedExpected));
        }

    }

}
