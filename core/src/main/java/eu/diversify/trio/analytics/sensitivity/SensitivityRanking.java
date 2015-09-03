/**
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
 * This file is part of TRIO :: Core.
 *
 * TRIO :: Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Core.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
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
 * ====
 *     This file is part of TRIO :: Core.
 *
 *     TRIO :: Core is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     TRIO :: Core is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with TRIO :: Core.  If not, see <http://www.gnu.org/licenses/>.
 * ====
 *
 * ====
 *     This file is part of TRIO.
 *
 *     TRIO is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     TRIO is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with TRIO.  If not, see <http://www.gnu.org/licenses/>.
 * ====
 *
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
 * This file is part of TRIO :: Core.
 *
 * TRIO :: Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Core.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
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

package eu.diversify.trio.analytics.sensitivity;

import eu.diversify.trio.analytics.events.StatisticListener;
import eu.diversify.trio.analytics.events.Statistic;
import eu.diversify.trio.simulation.events.IdleSimulationListener;
import eu.diversify.trio.simulation.events.SimulationListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Listen to events from the simulation and produces a ranking of component
 * according to their mean impact (the number of components that depend on
 * them).
 */
public class SensitivityRanking {

    public static final String KEY_SENSITIVITY_RANKING = "sensitivity ranking";

    private final SimulationHandler simulationHandler;
    private final StatisticListener statistics;
    private final Map<Integer, Ranking> rankings;

    public SensitivityRanking(StatisticListener statistics) {
        this.statistics = statistics;
        this.rankings = new HashMap<Integer, Ranking>();
        this.simulationHandler = new SimulationHandler();
    }

    public SimulationListener getSimulationHandler() {
        return this.simulationHandler;
    }

    /**
     * Handle the simulation events
     */
    private class SimulationHandler extends IdleSimulationListener {

        @Override
        public void failure(int simulationId, int sequenceId, double time, String failedComponent, List<String> impactedComponents) {
            final Ranking ranking = rankings.get(simulationId);
            if (ranking == null) {
                final String description = String.format("Unknown simulation ID %d", simulationId);
                throw new IllegalStateException(description);
            }
            ranking.accountFor(failedComponent, impactedComponents.size());
        }

        @Override
        public void simulationInitiated(int simulationId) {
            if (rankings.containsKey(simulationId)) {
                final String description = String.format("Duplicated simulation ID %d", simulationId);
                throw new IllegalStateException(description);
            }

            final Ranking newRanking = new Ranking();
            rankings.put(simulationId, newRanking);
        }

        @Override
        public void simulationComplete(int simulationId) {
            final Ranking ranking = rankings.get(simulationId);
            if (ranking == null) {
                final String description = String.format("Unknown simulation ID %d", simulationId);
                throw new IllegalStateException(description);
            }

            statistics.onSensitivityRanking(new Statistic(simulationId, -1, KEY_SENSITIVITY_RANKING), ranking.rank());
        }

    }

    private static class Ranking {

        private final Map<String, Sensitivity> sensitivities;

        public Ranking() {
            this.sensitivities = new HashMap<String, Sensitivity>();
        }

        private void accountFor(String failedComponent, int impact) {
            Sensitivity sensitivity = sensitivities.get(failedComponent);
            if (sensitivity == null) {
                sensitivity = new Sensitivity(failedComponent);
                sensitivities.put(failedComponent, sensitivity);
            }
            sensitivity.recordFailure(impact);
        }

        public List<Sensitivity> rank() {
            final List<Sensitivity> sensitivities = new ArrayList<Sensitivity>(this.sensitivities.values());
            Collections.sort(sensitivities);
            return sensitivities;
        }

    }

}
