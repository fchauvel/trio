package eu.diversify.trio.performance.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Hold the performance information of a given task
 */
public class Observation {

    private final Map<String, Object> measurements;

    public Observation(long duration) {
        measurements = new HashMap<>();
        measurements.put("duration", duration);
    }

    public Map<String, Object> getProperties() {
        return this.measurements;
    }
}
