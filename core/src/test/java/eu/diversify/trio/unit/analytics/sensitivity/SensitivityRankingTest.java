package eu.diversify.trio.unit.analytics.sensitivity;

import eu.diversify.trio.analytics.sensitivity.SensitivityRanking;
import eu.diversify.trio.analytics.events.Listener;
import eu.diversify.trio.analytics.events.Statistic;
import eu.diversify.trio.analytics.sensitivity.Sensitivity;
import eu.diversify.trio.simulation.events.Channel;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import org.junit.Test;

/**
 * Test the ranking of component according to their sensitivity
 */
public class SensitivityRankingTest {

    @Test
    public void shouldRankComponentsAccordingToTheirSensitivity() {
        final Channel simulation = new Channel();
        final Collector results = new Collector();
        final SensitivityRanking ranking = new SensitivityRanking(simulation, results); 

        simulation.simulationInitiated(1);
        simulation.sequenceInitiated(1, 1, asList("X", "Y", "Z"), asList("A", "B", "C"));
        simulation.failure(1, 1, "A", new ArrayList<String>());
        simulation.failure(1, 1, "B", asList("X"));
        simulation.failure(1, 1, "C", asList("Y", "Z"));
        simulation.sequenceComplete(1, 1);
        simulation.simulationComplete(1);

        results.assertSensitivityOf(1, "C", 1, 2);
        results.assertSensitivityOf(1, "B", 2, 1);
        results.assertSensitivityOf(1, "A", 3, 0);
        
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

        public void assertSensitivityOf(int scenarioId, String component, int position, double meanImpact) {
            List<Sensitivity> value = (List<Sensitivity>) values.get(new Statistic(scenarioId, -1, SensitivityRanking.KEY_SENSITIVITY_RANKING));
            assertThat(value, is(not(nullValue())));  
            assertThat(value.isEmpty(), is(false));
            assertThat(value.get(position-1).averageImpact(), is(closeTo(meanImpact, 1e-9)));
        }

    }

}
