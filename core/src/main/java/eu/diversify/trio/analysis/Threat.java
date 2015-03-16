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
/*
 */

package eu.diversify.trio.analysis;

import eu.diversify.trio.data.Trace;

/**
 *
 */
public class Threat extends Metric {

    private final RelativeRobustness robustness;
    private final Probability probability;
    
    public Threat(RelativeRobustness robustness, Probability probability) {
        super("fragility", "none");
        this.robustness = robustness;
        this.probability = probability;
    }

    @Override
    public void exitTrace(Trace trace) {
        final String label = trace.label();
        distribution().record(label,1D - robustness.valueOf(label) * probability.valueOf(label));
    }
    
    

}
