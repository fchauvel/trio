package eu.diversify.trio.analytics.sensitivity;

import eu.diversify.trio.analytics.events.Listener;
import eu.diversify.trio.analytics.events.Statistic;
import eu.diversify.trio.simulation.events.Channel;
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
public class SensitivityRanking implements eu.diversify.trio.simulation.events.Listener {

    public static final String KEY_SENSITIVITY_RANKING = "sensitivity ranking";

    private final Listener statistics;
    private final Map<Integer, Ranking> rankings;

    public SensitivityRanking(Channel simulation, Listener statistics) {
        simulation.subscribe(this);
        this.statistics = statistics;
        this.rankings = new HashMap<Integer, Ranking>();
    }

    public void sequenceInititated(int simulationId, int sequenceId, List<String> observed, List<String> controlled) {
        if (rankings.containsKey(simulationId)) {
            final String description = String.format("Duplicated simulation ID %d", simulationId);
            throw new IllegalStateException(description);
        }

        final Ranking newRanking = new Ranking();
        rankings.put(simulationId, newRanking);
    }

    public void failure(int simulationId, int sequenceId, String failedComponent, List<String> impactedComponents) {
        final Ranking ranking = rankings.get(simulationId);
        if (ranking == null) {
            final String description = String.format("Unknown simulation ID %d.", simulationId);
            throw new IllegalStateException(description);
        }
        ranking.accountFor(failedComponent, impactedComponents.size());
    }

    public void sequenceComplete(int simulationId, int sequenceId) {
        final Ranking ranking = rankings.get(simulationId);
        if (ranking == null) {
            final String description = String.format("Unknown simulation ID %d", simulationId);
            throw new IllegalStateException(description);
        }

        statistics.statisticReady(new Statistic(simulationId, -1, KEY_SENSITIVITY_RANKING), ranking.rank());
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
