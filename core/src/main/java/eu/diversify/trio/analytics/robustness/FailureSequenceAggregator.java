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
import eu.diversify.trio.simulation.events.Channel;
import eu.diversify.trio.simulation.events.Listener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Aggregate messages from the simulation and publish the robustness of
 * particular sequences on statistics channel
 */
public class FailureSequenceAggregator {

    private final Channel simulation;
    private final eu.diversify.trio.analytics.events.Listener statistics;
    private final Map<Integer, FailureSequence> sequences;

    public FailureSequenceAggregator(
            Channel simulation,
            eu.diversify.trio.analytics.events.Listener statistics) {
        this.simulation = simulation;
        this.simulation.subscribe(new Subscription());

        this.statistics = statistics;
        this.sequences = new HashMap<Integer, FailureSequence>();
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

    private class Subscription implements Listener {

        public void sequenceInitiated(int simulationId, int sequenceId, List<String> observed, List<String> controlled) {
            checkSequenceId(sequenceId);
            sequences.put(sequenceId, new FailureSequence(sequenceId, observed.size(), controlled.size()));
        }

        public void failure(int simulationId, int sequenceId, String failedComponent, List<String> survivors) {
            FailureSequence sequence = sequenceWithId(sequenceId);
            sequence.record(failedComponent, survivors.size());
        }

        public void sequenceComplete(int simulationId, int sequenceId) {
            FailureSequence sequence = sequences.remove(sequenceId);
            statistics.statisticReady(new Statistic(simulationId, sequenceId, KEY_FAILURE_SEQUENCE), sequence);
        }

        public void simulationInitiated(int simulationId) {
            // Nothing to be done
        }

        public void simulationComplete(int simulationId) {
            // Nothing to be done
        }

    }
    
    public static final String KEY_FAILURE_SEQUENCE = "robustness";

}