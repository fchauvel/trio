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
import eu.diversify.trio.core.requirements.stats.VariableCount;

/**
 * The average node degree (k-bar), commonly defined as 2*m / n, where m is the
 * number of link and n is the number of node is a graph.
 */
public class AverageNodeDegree extends Dispatcher {

    private final GraphSizeCalculator calculator;

    public AverageNodeDegree() {
        this(new GraphSizeCalculator(new VariableCount()));
    }

    private AverageNodeDegree(GraphSizeCalculator calculator) {
        super(calculator.getVariableCounter(), calculator);
        this.calculator = calculator;
    }

    public double getValue() {
        return (2 * calculator.getEdgeCount()) / (double) calculator.getNodeCount();
    }

}
