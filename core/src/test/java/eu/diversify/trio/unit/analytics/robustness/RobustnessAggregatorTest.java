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
package eu.diversify.trio.unit.analytics.robustness;

import eu.diversify.trio.analytics.events.StatisticListener;
import eu.diversify.trio.analytics.events.Statistic;
import eu.diversify.trio.analytics.robustness.RobustnessAggregator;
import eu.diversify.trio.analytics.robustness.FailureSequence;
import eu.diversify.trio.analytics.robustness.FailureSequenceAggregator;
import eu.diversify.trio.analytics.robustness.Robustness;
import eu.diversify.trio.SimulationDispatcher;
import eu.diversify.trio.analytics.events.IdleStatisticListener;
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
        SimulationDispatcher simulation = new SimulationDispatcher();
        eu.diversify.trio.StatisticDispatcher statistics = new eu.diversify.trio.StatisticDispatcher();

        Collector results = new Collector();
        RobustnessAggregator robustness = new RobustnessAggregator(results);
        simulation.register(robustness.getSimulationHandler());
        statistics.register(robustness.getStatisticsHandler());
        final int scenarioId = 1;

        simulation.simulationInitiated(scenarioId);
        statistics.onFailureSequence(failureSequence(scenarioId), noImpactSequence());
        statistics.onFailureSequence(failureSequence(scenarioId), oneKillAllSequence());
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

    private static class Collector extends IdleStatisticListener {

        private Robustness robustness;

        public Collector() {
        }

        @Override
        public void onRobustness(Statistic context, Robustness indicator) {
            robustness = indicator;
        }

        public void verifyRobustness(int scenarioId, double expectedRobustness) {
            assertThat(robustness, is(not(nullValue())));
            assertThat(robustness.average(), is(closeTo(expectedRobustness, 1e-6)));
        }

    }
}
