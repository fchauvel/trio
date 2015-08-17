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
package eu.diversify.trio.unit.analytics.threats;

import eu.diversify.trio.analytics.threats.Threat;
import eu.diversify.trio.analytics.events.StatisticListener;
import eu.diversify.trio.analytics.robustness.FailureSequenceAggregator;
import eu.diversify.trio.analytics.events.Statistic;
import eu.diversify.trio.analytics.threats.ThreatRanking;
import eu.diversify.trio.SimulationDispatcher;
import eu.diversify.trio.analytics.events.IdleStatisticListener;
import static java.util.Arrays.asList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import org.junit.Test;

/**
 * Specification of the the threat ranking behavior
 */
public class ThreatRankingTest {

    @Test
    public void shouldRankThreatsProperly() {
        final SimulationDispatcher simulation = new SimulationDispatcher();
        final Collector results = new Collector();

        final eu.diversify.trio.StatisticDispatcher statistics = new eu.diversify.trio.StatisticDispatcher();

        final FailureSequenceAggregator robustness = new FailureSequenceAggregator(statistics);
        simulation.register(robustness.getSimulationListener());

        final ThreatRanking threats = new ThreatRanking(results);
        simulation.register(threats.getSimulationHandler());
        statistics.register(threats.getStatisticHandler());

        simulation.simulationInitiated(1);
        simulation.sequenceInitiated(1, 1, asList("X", "Y", "Z"), 5);
        simulation.failure(1, 1, 1D, "A", asList("X", "Y", "Z"));
        simulation.failure(1, 1, 2D, "B", asList("X", "Y", "Z"));
        simulation.failure(1, 1, 3D, "C", asList("X", "Y", "Z"));
        simulation.failure(1, 1, 4D, "D", asList("X", "Y", "Z"));
        simulation.sequenceComplete(1, 1);
        simulation.simulationComplete(1);

        results.verifyThreatOf(1, "[A, B, C, D]", 1, 0D);

    }

    /**
     * A dummy listener which collects the statistics that are published for
     * later check
     */
    private class Collector extends IdleStatisticListener {

        private List<Threat> threats;

        public Collector() {
        }

        @Override
        public void onThreatRanking(Statistic context, List<Threat> indicator) {
            threats = indicator;
        }

        public void verifyThreatOf(int scenarioId, String threat, int position, double expected) {
            assertThat(threats, is(not(nullValue())));
            assertThat(threats.get(position - 1).failureSequence(), is(equalTo(threat)));
            assertThat(threats.get(position - 1).threatLevel(), is(closeTo(expected, 1e-9)));
        }

    }

}
