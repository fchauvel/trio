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

import eu.diversify.trio.data.State;
import eu.diversify.trio.data.Trace;
import java.util.List;

/**
 * Calculate the robustness associated with a given trace
 */
public class Robustness implements Metric {

    public String getName() {
        return "Robustness";
    }

    public String getUnit() {
        return "none";
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
