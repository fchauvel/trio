package eu.diversify.trio.analytics;

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
public class Robustness {

    private final Channel simulation;
    private final eu.diversify.trio.analytics.events.Listener statistics;
    private final Map<Integer, Sequence> sequences;

    public Robustness(
            Channel simulation,
            eu.diversify.trio.analytics.events.Listener statistics) {
        this.simulation = simulation;
        this.simulation.subscribe(new Subscription());

        this.statistics = statistics;
        this.sequences = new HashMap<Integer, Sequence>();
    }

    private Sequence sequenceWithId(int sequenceId) throws IllegalStateException {
        final Sequence sequence = sequences.get(sequenceId);
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

        public void sequenceInititated(int simulationId, int sequenceId, List<String> observed, List<String> controlled) {
            checkSequenceId(sequenceId);
            sequences.put(sequenceId, new Sequence(observed.size(), controlled.size()));
        }

        public void failure(int simulationId, int sequenceId, String failedComponent, List<String> impactedComponents) {
            Sequence sequence = sequenceWithId(sequenceId);
            sequence.record(impactedComponents.size());
        }

        public void sequenceComplete(int simulationId, int sequenceId) {
            Sequence sequence = sequences.remove(sequenceId);
            statistics.statisticReady(new Statistic(simulationId, sequenceId, KEY_ROBUSTNESS), sequence.robustness());
            statistics.statisticReady(new Statistic(simulationId, sequenceId, KEY_NORMALIZED_ROBUSTNESS), sequence.normalizedRobustness());
        }
        
        private static final String KEY_NORMALIZED_ROBUSTNESS = "normalized robustness";
        private static final String KEY_ROBUSTNESS = "robustness";

    }

    private static class Sequence {

        private final int min;
        private final int max;
        private int alive;
        private int robustness;

        public Sequence(int observed, int controlled) {
            min = observed;
            max = observed * controlled;
            alive = observed;
        }

        public void record(int killed) {
            alive -= killed;
            robustness += alive;
        }

        public int robustness() {
            return robustness;
        }

        public double normalizedRobustness() {
            return (double) (robustness - min) / (max - min);
        }

    }
}
