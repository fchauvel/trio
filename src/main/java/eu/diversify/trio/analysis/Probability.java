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

package eu.diversify.trio.analysis;

import eu.diversify.trio.data.AbstractDataSetListener;
import eu.diversify.trio.data.State;
import eu.diversify.trio.data.Trace;
import java.util.ArrayList;
import java.util.List;

/**
 * Compute the probability
 */
public class Probability extends Metric {

    private int numberOfAlternatives;

    public Probability() {
        super("Probability", "[0, 1]");
    }


    @Override
    public void exitState(State state) {
        if (state.getDisruptionLevel() > 0) {
            final double probability = value();
            updateCurrent(getTraceId(), probability * (1D / numberOfAlternatives));
        } else {
            updateCurrent(getTraceId(), 1D);
        }
        numberOfAlternatives = state.getControlledActivityLevel();
    }


}
