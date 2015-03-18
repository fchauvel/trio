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

package eu.diversify.trio.core.requirements;

import eu.diversify.trio.core.AssemblyVisitor;
import eu.diversify.trio.simulation.Topology;

/**
 * Logical disjunction between requirements
 */
public final class Disjunction extends BinaryOperator {

    public Disjunction(Requirement... operands) {
        super(LogicalOperator.OR, operands);
    }

    public void begin(AssemblyVisitor visitor) {
        visitor.enter(this);
    }

    public void end(AssemblyVisitor visitor) {
        visitor.exit(this);
    }

    public boolean isSatisfiedBy(Topology topology) {
        for (Requirement anyOperand : getOperands()) {
            if (anyOperand.isSatisfiedBy(topology)) {
                return true;
            }
        }
        return false;
    }

    private String formatted;

    @Override
    public String toString() {
        if (formatted == null) {
            StringBuilder buffer = new StringBuilder();
            buffer.append('(');
            for (int index = 0; index < getOperands().size(); index++) {
                final Requirement eachOperand = getOperands().get(index);
                buffer.append(eachOperand.toString());
                if (index < getOperands().size() - 1) {
                    buffer.append(OR_TEXT);
                }
            }
            buffer.append(')');
            formatted = buffer.toString();
        }
        return formatted;
    }
    
    private static final String OR_TEXT = " or ";

}
