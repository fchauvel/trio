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


package eu.diversify.trio;

import java.util.*;

/**
 * Provide statistics on a collection of real numbers
 */
public class Distribution implements Iterable<Double> {
    
    private final List<Double> samples;
    
    public Distribution(Double... samples) {
        this(Arrays.asList(samples));
    }
    
    public Distribution(Collection<Double> samples) {
        this.samples = new ArrayList<Double>(samples.size());
        for (Double eachSample: samples) {
            this.samples.add(eachSample);
        }
        Collections.sort(this.samples);
    }
    
    public Set<Double> values() {
        return new HashSet<Double>(samples);
    }

    @Override
    public Iterator<Double> iterator() {
        return samples.iterator();
    }
    
    public double minimum() {
        return Collections.min(samples);
    }
    
    public double maximum() {
        return Collections.max(samples);
    }
    
    public double mean() {
        double sum = 0;
        for(double eachSample: samples) {
            sum += eachSample;
            
        }
        return sum / samples.size();
    }
    
    public double median() {
        int size = samples.size();
        if (size % 2 == 1) {
            return samples.get((size + 1) / 2);
        } 
        return (samples.get(size / 2) + samples.get((size / 2) + 1)) / 2D;
    }
    
    public double variance() {
        final double mean = mean();
        double sum = 0;
        for(double eachSample: samples) {
            sum += Math.pow(eachSample-mean, 2);
            
        }
        return sum / samples.size();
    }
    
    public double standardDeviation() {
        return Math.sqrt(variance());
    }
    
    @Override
    public String toString() {
        return String.format("%.2f in [%.2f, %.2f] +/- %.2f", mean(), minimum(), maximum(), standardDeviation());
    }

}
