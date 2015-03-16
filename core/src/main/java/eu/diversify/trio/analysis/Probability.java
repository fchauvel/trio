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

import eu.diversify.trio.data.Trace;

/**
 * Compute the probability
 */
public class Probability extends Metric {


    public Probability() {
        super("Probability", "[0, 1]");
    }

    @Override
    public void exitTrace(Trace trace) {
        double probability = 1;
        int numberOfAlternatives = trace.getControlCapacity();
        for (int level: trace.disruptionLevels()) {
            if (level > 0) {
                probability *= 1D / numberOfAlternatives;
            }
            numberOfAlternatives = trace.afterDisruption(level).getControlledActivityLevel();
        }
        distribution().record(trace.label(), probability);
    }

}
