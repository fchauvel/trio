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
/**
 *
 * This file is part of TRIO.
 *
 * TRIO is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * TRIO is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO. If not, see <http://www.gnu.org/licenses/>.
 */
package eu.diversify.trio.core.requirements;

import eu.diversify.trio.core.AssemblyVisitor;
import eu.diversify.trio.simulation.Topology;

/**
 * Logical conjunction between two requirements
 */
public final class Conjunction extends BinaryOperator {

    public Conjunction(Requirement left, Requirement right) {
        super(LogicalOperator.AND, left, right);
    }

    public void begin(AssemblyVisitor visitor) {
        visitor.enter(this);
    }

    public void end(AssemblyVisitor visitor) {
        visitor.exit(this);
    }

    public boolean isSatisfiedBy(Topology topology) {
        return getLeft().isSatisfiedBy(topology) && getRight().isSatisfiedBy(topology);
    }

    private String formatted;

    @Override
    public String toString() {
        if (formatted == null) {
            this.formatted = String.format("(%s and %s)", getLeft().toString(), getRight().toString());
        }
        return formatted;
    }

}