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
public class FailureSequenceAggregatorTest {

    @Test
    public void shouldComputeTheRobustnessProperly() {
        final Channel simulation = new Channel();
        final Collector results = new Collector();
        final FailureSequenceAggregator robustness = new FailureSequenceAggregator(simulation, results);

        simulation.simulationInitiated(1);
        simulation.sequenceInitiated(1, 1, asList("X", "Y", "Z"), asList("A", "B", "C", "D"));
        simulation.failure(1, 1, "A", asList("X", "Y", "Z"));
        simulation.failure(1, 1, "B", asList("X", "Y", "Z"));
        simulation.failure(1, 1, "C", asList("X", "Y", "Z"));
        simulation.failure(1, 1, "D", asList("X", "Y", "Z"));
        simulation.sequenceComplete(1, 1);
        simulation.simulationComplete(1);

        results.assertEquals(1, 1, 15, 1D);

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
            final FailureSequence sequence = (FailureSequence) values.get(new Statistic(scenarioId, sequenceId, FailureSequenceAggregator.KEY_FAILURE_SEQUENCE));
            assertThat(sequence, is(not(nullValue())));
            assertThat(sequence.robustness(), equalTo(expected));
            assertThat(sequence.normalizedRobustness(), equalTo(normalizedExpected));
        }

    }

}