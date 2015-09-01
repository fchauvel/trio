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
package eu.diversify.trio.analytics.robustness;

import eu.diversify.trio.analytics.events.Statistic;
import eu.diversify.trio.simulation.events.IdleSimulationListener;
import eu.diversify.trio.simulation.events.SimulationListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Aggregate messages from the simulation and publish the robustness of
 * particular sequences on statistics channel
 */
public class FailureSequenceAggregator {

    private final eu.diversify.trio.analytics.events.StatisticListener statistics;
    private final Map<Integer, FailureSequence> sequences;
    private final SimulationHandler listener;
    
    public FailureSequenceAggregator(eu.diversify.trio.analytics.events.StatisticListener statistics) {
        this.listener = new SimulationHandler();
        this.statistics = statistics;
        this.sequences = new HashMap<Integer, FailureSequence>();
    }
    
    public SimulationListener getSimulationListener() {
        return this.listener;
    }

    private FailureSequence sequenceWithId(int sequenceId) throws IllegalStateException {
        final FailureSequence sequence = sequences.get(sequenceId);
        if (sequence == null) {
            final String description = String.format("Unknown sequence ID '%d'", sequenceId);
            throw new IllegalStateException(description);
        }
        return sequence;
    }

    private void checkSequenceId(int sequenceId) throws IllegalStateException {
        if (sequences.containsKey(sequenceId)) {
            final String description = String.format("Duplicated sequence ID '%d'", sequenceId);
            throw new IllegalStateException(description);
        }
    }

    private class SimulationHandler extends IdleSimulationListener {

        @Override
        public void sequenceInitiated(int simulationId, int sequenceId, List<String> observed, double duration) {
            checkSequenceId(sequenceId);
            sequences.put(sequenceId, new FailureSequence(sequenceId, observed.size(), duration));
        }

        @Override
        public void failure(int simulationId, int sequenceId, double time, String failedComponent, List<String> survivors) {
            FailureSequence sequence = sequenceWithId(sequenceId);
            sequence.record(failedComponent, time, survivors.size());
        }

        @Override
        public void sequenceComplete(int simulationId, int sequenceId) {
            FailureSequence sequence = sequences.remove(sequenceId);
            statistics.onFailureSequence(new Statistic(simulationId, sequenceId, KEY_FAILURE_SEQUENCE), sequence);
        }
    }
    
    public static final String KEY_FAILURE_SEQUENCE = "robustness";

}
