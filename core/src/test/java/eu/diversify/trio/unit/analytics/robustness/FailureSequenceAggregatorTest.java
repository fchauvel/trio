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
package eu.diversify.trio.unit.analytics.robustness;

import eu.diversify.trio.analytics.robustness.FailureSequenceAggregator;
import eu.diversify.trio.analytics.events.StatisticListener;
import eu.diversify.trio.analytics.events.Statistic;
import eu.diversify.trio.analytics.robustness.FailureSequence;
import eu.diversify.trio.SimulationDispatcher;

import static java.util.Arrays.asList;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import org.junit.Test;

/**
 * Specification of the robustness calculator
 */
public class FailureSequenceAggregatorTest {

    @Test
    public void shouldComputeTheRobustnessProperly() {
        final SimulationDispatcher simulation = new SimulationDispatcher();
        final Collector results = new Collector();
        final FailureSequenceAggregator aggregator = new FailureSequenceAggregator(results);
        simulation.register(aggregator.getSimulationListener());

        simulation.simulationInitiated(1);
        simulation.sequenceInitiated(1, 1, asList("X", "Y", "Z"), 5);
        simulation.failure(1, 1, 1D, "A", asList("X", "Y", "Z"));
        simulation.failure(1, 1, 2D, "B", asList("X", "Y", "Z"));
        simulation.failure(1, 1, 3D, "C", asList("X", "Y", "Z"));
        simulation.failure(1, 1, 4D, "D", asList("X", "Y", "Z"));
        simulation.sequenceComplete(1, 1);
        simulation.simulationComplete(1);

        results.assertEquals(1, 1, 15, 1D);

    }

    /**
     * A dummy listener which collects the statistics that are published for
     * later check
     */
    private class Collector implements StatisticListener {
 
        private final Map<Statistic, Object> values;

        public Collector() {
            values = new HashMap<Statistic, Object>();
        }

        public void statisticReady(Statistic statistic, Object value) {
            values.put(statistic, value);
        }

        public void assertEquals(int scenarioId, int sequenceId, int expected, double normalizedExpected) {
            final FailureSequence sequence = (FailureSequence) values.get(new Statistic(scenarioId, sequenceId, FailureSequenceAggregator.KEY_FAILURE_SEQUENCE));
            assertThat(sequence, is(not(nullValue())));
            assertThat(sequence.robustness(), is(closeTo(expected, 1e-6)));
            assertThat(sequence.normalizedRobustness(), is(closeTo(normalizedExpected, 1e-6)));
        }

        public boolean accept(Statistic statistic) {
            return true;
        }

    }

}
