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

import eu.diversify.trio.analytics.events.StatisticListener;
import eu.diversify.trio.analytics.events.Selection;
import eu.diversify.trio.analytics.events.Statistic;
import eu.diversify.trio.simulation.events.IdleSimulationListener;
import java.util.HashMap;
import java.util.Map;

public class RobustnessAggregator {

    private final eu.diversify.trio.simulation.events.SimulationListener simulationListener;
    private final StatisticListener statisticsHandler;
    private final StatisticListener results;
    private final Map<Integer, Robustness> robustnesses;

    public RobustnessAggregator(StatisticListener results) {
        this.simulationListener = new SimulationHandler();
        this.statisticsHandler = new StatisticHandler();
        this.results = results;
        this.robustnesses = new HashMap<Integer, Robustness>();
    }
    
    public eu.diversify.trio.simulation.events.SimulationListener getSimulationHandler() {
        return this.simulationListener;
    }
    
    public StatisticListener getStatisticsHandler() {
        return this.statisticsHandler;
    }
    
    public Selection getStatistics() {
        return new OnlyFailureSequences();
    }

    private Robustness robustnessOf(int scenarioId) {
        final Robustness robustness = robustnesses.get(scenarioId);
        if (robustness == null) {
            final String description = String.format("Unknown scenario ID %d", scenarioId);
            throw new IllegalStateException(description);
        }
        return robustness;
    }

    private class StatisticHandler implements StatisticListener {

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

    private class SimulationHandler extends IdleSimulationListener {

        @Override
        public void simulationInitiated(int simulationId) {
            if (robustnesses.containsKey(simulationId)) {
                final String description = String.format("Duplicatred scenario ID %d", simulationId);
                throw new IllegalStateException(description);
            }
            robustnesses.put(simulationId, new Robustness(simulationId));
        }

        @Override
        public void simulationComplete(int simulationId) {
            final Robustness robustness = robustnessOf(simulationId);
            results.statisticReady(new Statistic(simulationId, -1, KEY_ROBUSTNESS), robustness);
            robustnesses.remove(simulationId);
        }

    }

    public static final String KEY_ROBUSTNESS = "overall robustness";

    private static class OnlyFailureSequences implements Selection {

        public boolean isSatisfiedBy(Statistic statistic, Object value) {
            return statistic.getName().equals(FailureSequenceAggregator.KEY_FAILURE_SEQUENCE);
        }
    }

}
