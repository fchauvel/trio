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

import eu.diversify.trio.analytics.events.Listener;
import eu.diversify.trio.analytics.events.Statistic;
import eu.diversify.trio.analytics.robustness.RobustnessAggregator;
import eu.diversify.trio.analytics.robustness.FailureSequence;
import eu.diversify.trio.analytics.robustness.FailureSequenceAggregator;
import eu.diversify.trio.analytics.robustness.Robustness;
import eu.diversify.trio.simulation.events.Channel;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import org.junit.Test;

/**
 * Test the behavior of the average robustness aggregator
 */
public class RobustnessAggregatorTest {

    @Test
    public void shouldComputeAverageRobustnessProperly() {
        Channel simulation = new Channel();
        eu.diversify.trio.analytics.events.Channel statistics = new eu.diversify.trio.analytics.events.Channel();

        Collector results = new Collector();
        RobustnessAggregator robustness = new RobustnessAggregator(simulation, statistics, results);
        final int scenarioId = 1;

        simulation.simulationInitiated(scenarioId);
        statistics.statisticReady(failureSequence(scenarioId), noImpactSequence());
        statistics.statisticReady(failureSequence(scenarioId), oneKillAllSequence());
        simulation.simulationComplete(scenarioId);

        results.verifyRobustness(scenarioId, 0.5D);
    }

    private static FailureSequence oneKillAllSequence() {
        final FailureSequence sequence = new FailureSequence(2, 4, 5);
        sequence.record("X", 1D, 0);        
        assertThat(sequence.normalizedRobustness(), is(equalTo(0D)));
        return sequence;
    }

    private static FailureSequence noImpactSequence() {
        final FailureSequence sequence = new FailureSequence(1, 4, 5);
        sequence.record("W", 1D, 4);
        sequence.record("X", 2D, 4);
        sequence.record("Y", 3D, 4);
        sequence.record("Z", 4D, 4);
        assertThat(sequence.normalizedRobustness(), is(equalTo(1D)));
        return sequence;
    }

    private static Statistic failureSequence(final int scenarioId) {
        return new Statistic(scenarioId, -1, FailureSequenceAggregator.KEY_FAILURE_SEQUENCE);
    }

    private static class Collector implements Listener {

        private final Map<Statistic, Robustness> results;

        public Collector() {
            results = new HashMap<Statistic, Robustness>();
        }
        
        public void statisticReady(Statistic statistic, Object value) {
            results.put(statistic, (Robustness) value);
        }
        
        public void verifyRobustness(int scenarioId, double expectedRobustness) {
            Robustness robustness = results.get(new Statistic(scenarioId, -1, RobustnessAggregator.KEY_ROBUSTNESS));
            
            assertThat(robustness, is(not(nullValue())));
            assertThat(robustness.average(), is(closeTo(expectedRobustness, 1e-6))); 
        }

    }
}
