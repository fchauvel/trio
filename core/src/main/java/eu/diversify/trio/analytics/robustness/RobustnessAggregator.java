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
package eu.diversify.trio.analytics.robustness;

import eu.diversify.trio.analytics.events.Listener;
import eu.diversify.trio.analytics.events.Publisher;
import eu.diversify.trio.analytics.events.Selection;
import eu.diversify.trio.analytics.events.Statistic;
import eu.diversify.trio.simulation.events.Channel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RobustnessAggregator {

    private final Listener results;
    private final Map<Integer, Robustness> robustnesses;

    public RobustnessAggregator(Channel simulation, Publisher statistics, Listener results) {
        simulation.subscribe(new SimulationHandler());
        statistics.subscribe(new StatisticHandler(), onlyFailureSequences());
        this.results = results;
        this.robustnesses = new HashMap<Integer, Robustness>();
    }

    private Selection onlyFailureSequences() {
        return new Selection() {

            public boolean isSatisfiedBy(Statistic statistic, Object value) {
                return statistic.getName().equals(FailureSequenceAggregator.KEY_FAILURE_SEQUENCE);
            }

        };
    }

    private Robustness robustnessOf(int scenarioId) {
        final Robustness robustness = robustnesses.get(scenarioId);
        if (robustness == null) {
            final String description = String.format("Unknown scenario ID %d", scenarioId);
            throw new IllegalStateException(description);
        }
        return robustness;
    }

    private class StatisticHandler implements Listener {

        public void statisticReady(Statistic statistic, Object value) {
            if (!statistic.getName().equals(FailureSequenceAggregator.KEY_FAILURE_SEQUENCE)) {
                final String description = String.format("Excepted statistic %s but provided with %s", FailureSequenceAggregator.KEY_FAILURE_SEQUENCE, statistic.getName());
                throw new IllegalArgumentException(description);
            }

            final FailureSequence sequence = (FailureSequence) value;
            final Robustness robustness = robustnessOf(statistic.getScenarioId());
            robustness.record(sequence.normalizedRobustness());
        }

    }

    private class SimulationHandler implements eu.diversify.trio.simulation.events.Listener {

        public void simulationInitiated(int simulationId) {
            if (robustnesses.containsKey(simulationId)) {
                final String description = String.format("Duplicatred scenario ID %d", simulationId);
                throw new IllegalStateException(description);
            }
            robustnesses.put(simulationId, new Robustness(simulationId));
        }

        public void sequenceInitiated(int simulationId, int sequenceId, List<String> observed, List<String> controlled) {
        }

        public void failure(int simulationId, int sequenceId, String failedComponent, List<String> impactedComponents) {
        }

        public void sequenceComplete(int simulationId, int sequenceId) {
        }

        public void simulationComplete(int simulationId) {
            final Robustness robustness = robustnessOf(simulationId);
            results.statisticReady(new Statistic(simulationId, -1, KEY_ROBUSTNESS), robustness);
            robustnesses.remove(simulationId);
        }

    }

    public static final String KEY_ROBUSTNESS = "overall robustness";

}