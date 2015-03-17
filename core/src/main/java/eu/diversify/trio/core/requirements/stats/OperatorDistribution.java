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
package eu.diversify.trio.core.requirements.stats;

import eu.diversify.trio.core.DefaultAssemblyVisitor;
import eu.diversify.trio.core.requirements.*;
import java.util.Arrays;

/**
 * Count logical operators in a given requirement
 */
public class OperatorDistribution extends DefaultAssemblyVisitor {

    private final int[] counts;

    public OperatorDistribution() {
        counts = new int[LogicalOperator.values().length];
    }

    public double disjunctionRatio() {
        return ((double) counts[LogicalOperator.OR.index]) / operatorCount();
    }

    public double conjunctionRatio() {
        return ((double) counts[LogicalOperator.AND.index]) / operatorCount();
    }

    public double negationRatio() {
        return ((double) counts[LogicalOperator.NOT.index]) / operatorCount();
    }

    public int operatorCount() {
        int total = 0;
        for (LogicalOperator operator: LogicalOperator.values()) {
            total += counts[operator.index];
        }
        return total;
    }

    @Override
    public void exit(Nothing nothing) {
        counts[LogicalOperator.NOTHING.index]++;
    }

    @Override
    public void exit(Require require) {
        counts[LogicalOperator.REQUIRE.index]++;
    }

    @Override
    public void exit(Negation negation) {
        counts[LogicalOperator.NOT.index]++;
    }

    @Override
    public void exit(Disjunction disjunction) {
        counts[LogicalOperator.OR.index]++;
    }

    @Override
    public void exit(Conjunction conjunction) {
        counts[LogicalOperator.AND.index]++;
    }

    public int[] counts() {
        return Arrays.copyOf(counts, counts.length);
    }

}
