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
 * Compute a relative robustness
 */
public class RelativeRobustness extends Metric {

    private final Robustness robustness;
    
    public RelativeRobustness(Robustness robustness) {
        super(NAME, "[0,1]");
        this.robustness = robustness;
    }
    public static final String NAME = "norm. robustness";
    
    @Override
    public void exitTrace(Trace trace) {
        final double min = trace.getObservationCapacity();
        final double max = trace.getObservationCapacity() * (trace.getControlCapacity() + 1);
        final double normalizedRobustness = (robustness.valueOf(trace.label()) - min) / (max - min);
        distribution().record(trace.label(), normalizedRobustness);
        
    }    
    
}