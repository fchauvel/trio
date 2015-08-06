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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Aggregate events from the simulation into a meaningful failure sequences
 */
public class FailureSequence {

    private final int id;
    private double duration;
    private final List<Record> sequence;
    private int robustness;

    /**
     * Create a new failure sequence
     * @param id the unique ID of this sequence
     * @param observed the number of component under observation
     * @param duration the total duration of this sequence
     */
    public FailureSequence(int id, int observed, double duration) {
        this.id = id;
        this.duration = checkDuration(duration);
        this.sequence = new ArrayList<Record>();
        this.sequence.add(new Record(0, checkObserved(observed)));
        this.robustness = 0;
    }

    private double checkDuration(double duration) {
        if (duration <= 0) {
            final String error = String.format("Failure sequence duration must be positive (found %.2f)", duration);
            throw new IllegalArgumentException(error);
        }
        if (Double.isNaN(duration)) {
            final String error = "Failure sequence duration must be a number (found NaN)";
            throw new IllegalArgumentException(error);
        }
        if (Double.isInfinite(duration)) {
            final String error = "Failure sequence duration must be a number (found infinity)";
            throw new IllegalArgumentException(error);
        }
        return duration;
    }

    private int checkObserved(int observed) throws IllegalArgumentException {
        if (observed <= 0) {
            String error = String.format("The number of observed components must be positive (found %d)", observed);
            throw new IllegalArgumentException(error);
        }
        return observed;
    }

    /**
     * Record a new failure in this sequence
     *
     * @param component the component that failed
     * @param time the time when the component failed
     * @param survivorCount the number of remaining active components
     */
    public void record(String component, double time, int survivorCount) {
        checkRunning(component);
        checkMonotony(survivorCount);
        checkTime(time);

        robustness += activity() * (time - enlapsedTime());
        sequence.add(new Record(time, component, survivorCount));
    }

    private void checkTime(double time) throws IllegalArgumentException {
        if (time <= enlapsedTime()) {
            final String error = String.format("Time is moving backward (now: %.2f, new: %.2f)", enlapsedTime(), time);
            throw new IllegalArgumentException(error);
        }
        if (time > duration) {
            final String error = String.format("Duration exceeded (duration: %.2f, time: %.2f)", duration, time);
            throw new IllegalArgumentException(error);
        }
    }

    private void checkMonotony(int survivorCount) throws IllegalArgumentException {
        if (survivorCount > activity()) {
            final String error = String.format("Too many survivors (now: %d, newt: %d)", activity(), survivorCount);
            throw new IllegalArgumentException(error);
        }
    }

    private void checkRunning(String component) throws IllegalArgumentException {
        if (alreadyFailed(component)) {
            final String error = String.format("Component '%s' has already failed", component);
            throw new IllegalArgumentException(error);
        }
    }

    private double enlapsedTime() {
        checkSequenceNotEmpty();

        return lastRecord().getTime();
    }

    private void checkSequenceNotEmpty() {
        assert !sequence.isEmpty() : "Failure sequence must not be empty!";
    }

    private boolean alreadyFailed(String failedComponent) {
        for (Record each : sequence) {
            if (each.isFailureOf(failedComponent)) {
                return true;
            }
        }
        return false;
    }

    private int activity() {
        checkSequenceNotEmpty();
        return lastRecord().getSurvivorCount();
    }

    private Record lastRecord() {
        final int lastIndex = sequence.size() - 1;
        return sequence.get(lastIndex);
    }

    private int initialActivityLevel() {
        checkSequenceNotEmpty();
        return sequence.get(0).getSurvivorCount();
    }

    private Record firstFailure() {
        for (Record each : sequence) {
            if (each.isFailure()) {
                return each;
            }
        }
        return null;
    }

    public int id() {
        return id;
    }

    /**
     * @return the list of components that failed in this sequence, in th order
     * where they failed.
     */
    public List<String> sequence() {
        final List<String> result = new ArrayList<String>(sequence.size() - 1);
        for (Record each : sequence) {
            if (each.isFailure()) {
                result.add(each.getComponent());
            }
        }
        return Collections.unmodifiableList(result);
    }

    /**
     * @return the absolute robustness, as the integral of the number of
     * survivors over time.
     */
    public double robustness() {
        return robustness + activity() * (duration - enlapsedTime()); 
    }

    /**
     * @return the normalized robustness as a value between 0 and 1
     */
    public double normalizedRobustness() {
        double min = minimumRobustness();
        double max = maximumRobustness();
        
        assert robustness() >= min && robustness() <= max :
                String.format("Invalid robustness %d (must be in [%.2f, %.2f])", robustness, min, max);
        
        final double result = (robustness() - min) / (max - min);
        return result;
    }

    private double maximumRobustness() {
        return duration * initialActivityLevel();
    }

    private double minimumRobustness() {
        return initialActivityLevel() * firstFailure().getTime();
    }

    /**
     * Hold one entry in the sequence
     */
    private static class Record {

        private final String component;
        private final double time;
        private final int survivorCount;

        public Record(double time, int survivorCount) {
            this(time, null, survivorCount);
        }

        public Record(double time, String component, int survivorCount) {
            this.time = time;
            this.component = component;
            this.survivorCount = survivorCount;
        }

        public String getComponent() {
            return component;
        }

        public double getTime() {
            return time;
        }

        public int getSurvivorCount() {
            return survivorCount;
        }

        public boolean isFailure() {
            return getComponent() != null;
        }

        public boolean isFailureOf(String component) {
            return this.isFailure() && this.getComponent().equals(component);
        }

    }

}
