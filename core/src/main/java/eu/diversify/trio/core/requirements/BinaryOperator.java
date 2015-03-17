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
/*
 */
package eu.diversify.trio.core.requirements;

import eu.diversify.trio.util.Require;
import eu.diversify.trio.core.AssemblyPart;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public abstract class BinaryOperator extends Requirement {


    private final LogicalOperator operator;
    private final Requirement left;
    private final Requirement right;

    public BinaryOperator(LogicalOperator operator, Requirement left, Requirement right) {
        Require.notNull(operator, "'null' given as operator!");
        this.operator = operator;

        Require.notNull(left, "'null' given as left operand!");
        this.left = left;

        Require.notNull(right, "'null' given as right operator");
        this.right = right;
    }

    public Requirement getLeft() {
        return left;
    }

    public Requirement getRight() {
        return right;
    }

    public final Collection<AssemblyPart> subParts() {
        final List<AssemblyPart> parts = new ArrayList<AssemblyPart>(2);
        parts.add(left);
        parts.add(right);
        return parts;
    }

    private int hash = 0;

    @Override
    public final int hashCode() {
        if (hash == 0) {
            hash = Objects.hash(operator, left, right);
        }
        return hash;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BinaryOperator other = (BinaryOperator) obj;
        if (this.operator != other.operator) {
            return false;
        }
        if (this.left != other.left && (this.left == null || !this.left.equals(other.left))) {
            return false;
        }
        if (this.right != other.right && (this.right == null || !this.right.equals(other.right))) {
            return false;
        }
        return true;
    }

}
