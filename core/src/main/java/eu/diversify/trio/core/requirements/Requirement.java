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

import eu.diversify.trio.core.SystemPart;
import eu.diversify.trio.simulation.Topology;

/**
 * Logical expression representing the components whose activity (or inactivity)
 * is required.
 */
public abstract class Requirement implements SystemPart {

    public final Requirement and(Requirement right) {
        return new Conjunction(this, right);
    }

    public final Requirement or(Requirement right) {
        return new Disjunction(this, right);
    }

    public final Requirement not() {
        return new Negation(this);
    }

    public final Requirement xor(Requirement right) {
        return or(right).and(right.not());
    }
     
    public abstract boolean isSatisfiedBy(Topology topology);
   
    @Override
    public abstract String toString();

}
