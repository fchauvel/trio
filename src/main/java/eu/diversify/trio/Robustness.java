package eu.diversify.trio;

import java.util.List;

/**
 * Calculate the
 */
public class Robustness implements Metric {

    public String getName() {
        return "robustness";
    }

    public double computeOn(Trace trace) {
        double robustness = 0;
        final List<Integer> disruptions = trace.disruptionLevels();
        for (int i=1 ; i<disruptions.size() ; i++) {
            final State previous = trace.afterDisruption(i-1);
            final State current = trace.afterDisruption(i);
            int step = current.getDisruptionLevel() - previous.getDisruptionLevel();
            robustness += step * previous.getActivityLevel();
        }
        return robustness;

    }

}
