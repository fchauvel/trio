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
    private final int min;
    private final int max;
    private int robustness;
    private final List<String> sequence;

    public FailureSequence(int id, int observed, int controlled) {
        checkObserved(observed);
        checkControlled(controlled);
        this.id = id;
        min = observed;
        max = observed * (controlled + 1);
        robustness = observed;
        sequence = new ArrayList<String>(controlled);
    }

    private void checkControlled(int controlled) throws IllegalArgumentException {
        if (controlled <= 0) {
            String error = String.format("The number of controlled components must be positive (found %d)", controlled);
            throw new IllegalArgumentException(error);
        }
    }

    private void checkObserved(int observed) throws IllegalArgumentException {
        if (observed <= 0) {
            String error = String.format("The number of observed components must be positive (found %d)", observed);
            throw new IllegalArgumentException(error);
        }
    }

    public void record(String failedComponent, int survivorCount) {
        assert !sequence.contains(failedComponent) : "Component '" + failedComponent + "' has already been failed";
        assert survivorCount <= robustness: failedComponent + " Too many survivors (" + survivorCount + " > " + robustness + ")";

        sequence.add(failedComponent);
        robustness += survivorCount;
        
    }

    public int id() {
        return id;
    }

    /**
     * @return the list of components that failed in this sequence, in th order
     * where they failed.
     */
    public List<String> sequence() {
        return Collections.unmodifiableList(sequence);
    }

    public int robustness() {
        return robustness;
    }

    public double normalizedRobustness() {
        assert robustness >= min && robustness <= max :
                String.format("Invalid robustness %d (must be in [%d, %d])", robustness, min, max);

        final double result = (double) (robustness - min) / (max - min);

        return result;
    }

}
