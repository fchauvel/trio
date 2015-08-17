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
package eu.diversify.trio.analytics.threats;

import eu.diversify.trio.analytics.robustness.FailureSequenceAggregator;
import eu.diversify.trio.analytics.robustness.FailureSequence;
import eu.diversify.trio.analytics.events.StatisticListener;
import eu.diversify.trio.analytics.events.Statistic;
import eu.diversify.trio.simulation.events.IdleSimulationListener;
import eu.diversify.trio.simulation.events.SimulationListener;
import java.util.ArrayList;
import static java.util.Arrays.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Listen to the simulation and to the statistics that are published, and ranks
 * failure sequences with respect to their threat level.
 */
public class ThreatRanking implements StatisticListener {

    private final StatisticListener results;
    private final Map<Integer, Ranking> rankings;
    private final SimulationHandler simulationHandler;

    public ThreatRanking(StatisticListener results) {
        this.results = results;
        this.rankings = new HashMap<Integer, Ranking>();
        this.simulationHandler = new SimulationHandler();
    }

    private final List<String> interests = asList(FailureSequenceAggregator.KEY_FAILURE_SEQUENCE);

    public SimulationListener getSimulationHandler() {
        return this.simulationHandler;
    }

    public StatisticListener getStatisticHandler() {
        return this;
    }

    public boolean accept(Statistic statistic) {
        return interests.contains(statistic.getName());
    }

    /**
     * Handle specific simulation events
     */
    private class SimulationHandler extends IdleSimulationListener {

        @Override
        public void simulationInitiated(int simulationId) {
            if (rankings.containsKey(simulationId)) {
                final String description = String.format("Duplicated scenario ID %d", simulationId);
                throw new IllegalStateException(description);
            }
            final Ranking ranking = new Ranking();
            rankings.put(simulationId, ranking);
        }

        @Override
        public void simulationComplete(int simulationId) {
            Ranking ranking = rankings.get(simulationId);
            if (ranking == null) {
                final String description = String.format("Unknown simulation ID %d", simulationId);
                throw new IllegalStateException(description);
            }
            results.statisticReady(new Statistic(simulationId, -1, KEY_THREAT_RANKING), ranking.rank());
        }
    }

    public void statisticReady(Statistic statistic, Object value) {
        if (statistic.getName().equals(FailureSequenceAggregator.KEY_FAILURE_SEQUENCE)) {
            final Ranking ranking = rankings.get(statistic.getScenarioId());
            if (ranking == null) {
                final String description = String.format("Invalid scenario ID %d", statistic.getScenarioId());
                throw new IllegalStateException(description);
            }
            ranking.record((FailureSequence) value);
        }
    }


    public static final String KEY_THREAT_RANKING = "threat ranking";

    private static class Ranking {

        private final Map<String, Threat> threats;
        private int sequenceCount;

        public Ranking() {
            this.threats = new HashMap<String, Threat>();
            this.sequenceCount = 0;
        }

        private void record(FailureSequence sequence) {
            this.sequenceCount += 1;
            final String failureSequences = sequence.sequence().toString();
            Threat threat = threats.get(failureSequences);
            if (threat == null) {
                threat = new Threat(failureSequences, sequence.normalizedRobustness());
                threats.put(sequence.sequence().toString(), threat);
            }
            threat.newInstance();
        }

        public List<Threat> rank() {
            final List<Threat> results = new ArrayList<Threat>(threats.values());
            for (Threat eachThreat : results) {
                eachThreat.setTotal(sequenceCount);
            }
            Collections.sort(results);
            return results;
        }

    }

}
