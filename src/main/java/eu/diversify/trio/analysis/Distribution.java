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

import java.util.Collections;
import java.util.List;

/**
 * Represent the distribution of a set of values
 */
public class Distribution {

    private final List<Double> values;
    
    public Distribution(List<Double> values) {
        this.values = values;
    }
    
    public double minimum() {
        return Collections.min(values);
    }
    
    public double maximum() {
        return Collections.max(values);
    }
    
    public double mean() {
        double sum = 0;
        for (double eachValue: values) {
            sum += eachValue;
        }
        return sum / values.size();
    }
    
    public double variance() {
        final double mean = mean();
        double sum = 0;
        for (double eachValue: values) {
            sum += Math.pow(mean - eachValue, 2);
        }
        return sum / values.size();
    }
    
    public double standardDeviation() {
        return Math.sqrt(variance());
    }
    
}
