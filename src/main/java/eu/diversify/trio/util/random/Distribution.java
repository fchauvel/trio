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


package eu.diversify.trio.util.random;

import java.util.Random;

/**
 * Probability distribution (e.g., normal, uniform, exponential)
 */
public abstract class Distribution {

    
    public static Distribution normal(double mean, double variance) {
        return new Gaussian(mean, variance);
    }
    
    public static Distribution uniform(double min, double max) {
        return new Uniform(min, max);
    }
    
    
    private final Random generator;

    
    public Distribution() {
        this.generator = new Random();
    }
    
    protected Random generate() {
        return this.generator;
    }
    
    public abstract double sample();
    
}
