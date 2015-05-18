package eu.diversify.trio.unit.analytics;

import eu.diversify.trio.analytics.Robustness;
import eu.diversify.trio.analytics.events.Listener;
import eu.diversify.trio.analytics.events.Statistic;
import eu.diversify.trio.simulation.events.Channel;
import eu.diversify.trio.simulation.events.Failure;
import eu.diversify.trio.simulation.events.SequenceComplete;
import eu.diversify.trio.simulation.events.SequenceInitiated;
import eu.diversify.trio.unit.simulation.SimulatorTest;
import java.util.ArrayList;
import java.util.Arrays;
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

        simulation.publish(new SequenceInitiated(1, 1, Arrays.asList(new String[]{"X", "Y", "Z"}), Arrays.asList(new String[]{"A", "B", "C", "D"})));
        simulation.publish(new Failure(1, 1, "A", new ArrayList<String>()));
        simulation.publish(new Failure(1, 1, "B", new ArrayList<String>()));
        simulation.publish(new Failure(1, 1, "C", new ArrayList<String>()));
        simulation.publish(new Failure(1, 1, "D", new ArrayList<String>()));
        simulation.publish(new SequenceComplete(1, 1));

        results.assertEquals(1, 1, "normalized robustness", 1D);
        results.assertEquals(1, 1, "robustness", 12D);
        
    }

   
    
    /**
     * A dummy listener which collects the statistics that are published for later check
     */
    private class Collector implements Listener {

        private final Map<Statistic, Double> values;

        public Collector() {
            values = new HashMap<Statistic, Double>();
        }

        public void statisticReady(Statistic statistic, double value) {
            values.put(statistic, value);
        }

        public void assertEquals(int scenarioId, int sequenceId, String statistic, double expected) {
            final Double robustness = values.get(new Statistic(scenarioId, sequenceId, statistic));
            assertThat(robustness, is(not(nullValue())));
            assertThat(robustness, equalTo(expected));
        }

    }

}
