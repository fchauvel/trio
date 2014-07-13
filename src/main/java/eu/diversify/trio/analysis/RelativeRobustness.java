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

import eu.diversify.trio.data.DataSet;
import eu.diversify.trio.data.State;
import eu.diversify.trio.data.Trace;
import java.util.ArrayList;
import java.util.List;

/**
 * Compute a relative robustness
 */
public class RelativeRobustness extends Metric {

    private final Robustness robustness;
    
    public RelativeRobustness(Robustness robustness) {
        super("norm. robustness", "%");
        this.robustness = robustness;
    }
    
    @Override
    public void exitTrace(Trace trace) {
        final double min = trace.getObservationCapacity();
        final double max = trace.getObservationCapacity() * trace.getControlCapacity();
        final double normalizedRobustness = (robustness.value() - min) / (max - min);
        updateCurrent(getTraceId(), normalizedRobustness);
        
    }    
    
}
