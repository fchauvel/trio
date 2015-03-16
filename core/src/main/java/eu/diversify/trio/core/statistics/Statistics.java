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
package eu.diversify.trio.core.statistics;

import eu.diversify.trio.core.Dispatcher;
import eu.diversify.trio.core.requirements.stats.OperatorDistribution;

/**
 * Gather various statistics about the system
 */
public class Statistics extends Dispatcher {

    private final Density density;
    private final OperatorDistribution distribution;

    public Statistics() {
       this(new Density(), new OperatorDistribution());
    }

    private Statistics(Density density, OperatorDistribution distribution) {
        super(density, distribution);
        this.density = density;
        this.distribution = distribution;
    }
    
    public String getCSVHeader() {
        return "density,disjunction,conjunction,negation";
    }
    
    public String toCsvENtry() {
        final String SEPARATOR = ",";
        return density.getValue() 
                + SEPARATOR + distribution.disjunctionRatio() 
                + SEPARATOR + distribution.conjunctionRatio()
                + SEPARATOR + distribution.negationRatio()
                + System.lineSeparator();
    }

}
