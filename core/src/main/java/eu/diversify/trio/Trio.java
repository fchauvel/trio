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

package eu.diversify.trio;

import eu.diversify.trio.analytics.events.StatisticListener;
import eu.diversify.trio.analytics.events.Selection;
import eu.diversify.trio.analytics.events.Statistic;
import eu.diversify.trio.analytics.robustness.FailureSequenceAggregator;
import eu.diversify.trio.analytics.robustness.Robustness;
import eu.diversify.trio.analytics.robustness.RobustnessAggregator;
import eu.diversify.trio.analytics.sensitivity.Sensitivity;
import eu.diversify.trio.analytics.sensitivity.SensitivityRanking;
import eu.diversify.trio.analytics.threats.Threat;
import eu.diversify.trio.analytics.threats.ThreatRanking;
import eu.diversify.trio.simulation.Simulation;

import eu.diversify.trio.core.storage.Storage;
import eu.diversify.trio.core.storage.StorageError;
import eu.diversify.trio.simulation.Experiment;
import java.util.List;

/**
 * The Trio application
 */
public class Trio {

    private Storage storage;
    private final SimulationDispatcher simulation;
    private final eu.diversify.trio.StatisticDispatcher statistics;

    public Trio(Storage storage) {
        this(storage, new SimulationDispatcher(), new StatisticDispatcher());
    }

    public Trio(Storage storage, SimulationDispatcher simulation, StatisticDispatcher analytics) {
        this.storage = storage;
        this.simulation = simulation;
        this.statistics = analytics;
        wireFailureSequenceAggregator();
        wireRobustnessAggregator();
        wireSensitiviyRanking();
        wireThreatRanking();
    }

    private void wireSensitiviyRanking() {
        SensitivityRanking sensitivity = new SensitivityRanking(statistics);
        simulation.register(sensitivity.getSimulationHandler());
    }

    private void wireThreatRanking() {
        ThreatRanking threats = new ThreatRanking(statistics);
        simulation.register(threats.getSimulationHandler());
        statistics.register(threats.getStatisticHandler(), threats.selection());
    }

    private void wireRobustnessAggregator() {
        RobustnessAggregator robustness = new RobustnessAggregator(statistics);
        simulation.register(robustness.getSimulationHandler());
        statistics.register(robustness.getStatisticsHandler(), robustness.getStatistics());
    }

    private void wireFailureSequenceAggregator() {
        FailureSequenceAggregator failureSequences = new FailureSequenceAggregator(statistics);
        this.simulation.register(failureSequences.getSimulationListener());
    }

    public void run(Simulation simulation, int runCount, final TrioListener listener) throws StorageError {
        final int id = nextId();
        subscribe(listener, id);
        Experiment experiment = new Experiment(id, simulation, storage.first(), runCount, this.simulation);
        experiment.execute();
    }

    private static int nextId() {
        runCounter += 1;
        return runCounter;
    }
    
    private static int runCounter = 0;

    private void subscribe(final TrioListener listener, int id) {
        statistics.register(new Dispatcher(listener), new AllStatistics(id));
    }

    private static class Dispatcher implements StatisticListener {

        private final TrioListener listener;

        public Dispatcher(TrioListener listener) {
            this.listener = listener;
        }

        public void statisticReady(Statistic statistic, Object value) {
            if (statistic.getName().equals(RobustnessAggregator.KEY_ROBUSTNESS)) {
                listener.onRobustness((Robustness) value);

            } else if (statistic.getName().equals(SensitivityRanking.KEY_SENSITIVITY_RANKING)) {
                listener.onSensitivityRanking((List<Sensitivity>) value);

            } else if (statistic.getName().equals(ThreatRanking.KEY_THREAT_RANKING)) {
                listener.onThreatRanking((List<Threat>) value);

            }
        }
    }

    private static class AllStatistics implements Selection {

        private final int id;

        public AllStatistics(int id) {
            this.id = id;
        }

        public boolean isSatisfiedBy(Statistic statistic, Object value) {
            return statistic.getScenarioId() == id;
        }

    }

}
