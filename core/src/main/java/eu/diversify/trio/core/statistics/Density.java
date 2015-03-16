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

import eu.diversify.trio.core.Component;
import eu.diversify.trio.core.DefaultSystemVisitor;
import eu.diversify.trio.core.requirements.stats.VariableCount;
import eu.diversify.trio.core.Dispatcher;
import eu.diversify.trio.util.Require;

/**
 * Compute the density of the graph underlying the structure of system.
 *
 * Density in this context is the ratio between the number of edges in the graph
 * and the maximum number of edges.
 */
public class Density extends Dispatcher {

    private final DensityCalculator calculator;

    public Density() {
        this(new DensityCalculator(new VariableCount()));
    }

    private Density(DensityCalculator calculator) {
        super(calculator.getVariableCounter(), calculator);
        this.calculator = calculator;
    }

    public double getValue() {
        return calculator.getValue();
    }

    private static class DensityCalculator extends DefaultSystemVisitor {

        private final VariableCount variableCount;
        private int componentCount;
        private int edgeCount;

        public DensityCalculator(VariableCount variableCount) {
            Require.notNull(variableCount, "Invalid value 'null' given as variable counter");

            this.variableCount = variableCount;
        }

        @Override
        public void exit(Component component) {
            componentCount++;
            edgeCount += variableCount.get();
        }

        public VariableCount getVariableCounter() {
            return this.variableCount;
        }

        public double getValue() {
            assert componentCount != 0:
                    "No component where traversed yet!";

            return ((double) edgeCount) / (componentCount * componentCount);
        }

    }

}
