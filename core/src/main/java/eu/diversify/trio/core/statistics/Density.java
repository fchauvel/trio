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

import eu.diversify.trio.core.requirements.stats.VariableCount;
import eu.diversify.trio.core.Dispatcher;

/**
 * Compute the density of the graph underlying the structure of system.
 *
 * Density in this context is the ratio between the number of edges in the graph
 * and the maximum number of edges.
 */
public class Density extends Dispatcher {

    private final GraphSizeCalculator calculator;

    public Density() {
        this(new GraphSizeCalculator(new VariableCount()));
    }

    private Density(GraphSizeCalculator calculator) {
        super(calculator.getVariableCounter(), calculator);
        this.calculator = calculator;
    }

    public double getValue() {
        return ((double) calculator.getEdgeCount()) / Math.pow(calculator.getNodeCount(), 2);

    }
}
