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

/**
 * The normal distribution
 */
public class Gaussian extends Distribution {

    private final double mean;
    private final double variance;
    private final double min;
    private final double max;

    public Gaussian(double mean, double variance) {
        this(mean, variance, mean - LIMIT * variance, mean + LIMIT * variance);
    }
    
    public Gaussian(double mean, double variance, double min, double max) {
        super();
        
        assert variance > 0D: String.format("Variance should be positive!");
        
        this.mean = mean;
        this.variance = variance;
        this.min = min;
        this.max = max;
    }
    private static final int LIMIT = 4;

    @Override
    public double sample() {
        final double draw = mean + generate().nextGaussian() * variance;
        return secure(draw);
    }
    
    private double secure(double draw) {
        if (draw < min) {
            return min;
        }
        if (draw > max) {
            return max;
        }
        return draw;
    }
    
    
}
