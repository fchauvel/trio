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

import eu.diversify.trio.util.Require;
import eu.diversify.trio.core.SystemPart;
import eu.diversify.trio.core.SystemVisitor;
import eu.diversify.trio.simulation.Topology;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * The logical negation
 */
public final class Negation extends Requirement {

    private final Requirement operand;

    public Negation(Requirement operand) {
        Require.notNull(operand, "'null' given as operand!");
        this.operand = operand;
    }

    public Collection<SystemPart> subParts() {
        final List<SystemPart> parts = new ArrayList<SystemPart>(1);
        parts.add(operand);
        return parts;
    }

    public void begin(SystemVisitor visitor) {
        visitor.enter(this);
    }

    public void end(SystemVisitor visitor) {
        visitor.exit(this);
    }

    public Requirement getOperand() {
        return operand;
    }

    public boolean isSatisfiedBy(Topology topology) {
        return !operand.isSatisfiedBy(topology);
    }

    private int hash = 0;

    @Override
    public int hashCode() {
        if (hash == 0) {
            hash = Objects.hash(operand);
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Negation other = (Negation) obj;
        if (this.operand != other.operand && (this.operand == null || !this.operand.equals(other.operand))) {
            return false;
        }
        return true;
    }

    private String formatted;

    @Override
    public String toString() {
        if (formatted == null) {
            this.formatted = String.format("(not %s)", operand.toString());
        }
        return formatted;
    }

}
