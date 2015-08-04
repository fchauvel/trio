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
package eu.diversify.trio;

import eu.diversify.trio.analytics.events.Listener;
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

import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.core.storage.Storage;
import eu.diversify.trio.core.storage.StorageError;
import eu.diversify.trio.simulation.events.Channel;
import java.util.List;

/**
 * The Trio application
 */
@SuppressWarnings("PMD.UnusedPrivateField")

public class Trio {

    private Storage storage;
    private final Channel simulationListeners;
    private final eu.diversify.trio.analytics.events.Channel analytics;

    public Trio(Storage storage) {
        this(storage, new Channel(), new eu.diversify.trio.analytics.events.Channel());
    }

    public Trio(Storage storage, Channel simulation, eu.diversify.trio.analytics.events.Channel analytics) {
        this.storage = storage;
        this.simulationListeners = simulation;
        this.analytics = analytics;
        new FailureSequenceAggregator(simulation, analytics);
        new RobustnessAggregator(simulation, analytics, analytics);
        new SensitivityRanking(simulation, analytics);
        new ThreatRanking(simulation, analytics, analytics);
    }

    public void run(Simulation simulation, final TrioListener listener) throws StorageError {
        subscribe(listener);
        final Assembly assembly = storage.first();
        simulation.run(assembly, simulationListeners);
    }

    private void subscribe(final TrioListener listener) {
        analytics.subscribe(new Dispatcher(listener), new AllStatistics());
    }

    private static class Dispatcher implements Listener {

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

        public boolean isSatisfiedBy(Statistic statistic, Object value) {
            return true;
        }
    }

}
