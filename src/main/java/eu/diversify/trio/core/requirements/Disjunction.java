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

import eu.diversify.trio.core.Requirement;
import eu.diversify.trio.simulation.Topology;
import eu.diversify.trio.core.requirements.AbstractRequirement;

/**
 * Logical disjunction between requirements
 */
public class Disjunction extends AbstractRequirement {

    private final Requirement left;
    private final Requirement right;

    public Disjunction(Requirement left, Requirement right) {
        this.left = left;
        this.right = right;
    }

    public boolean isSatisfiedBy(Topology topology) {
        return left.isSatisfiedBy(topology) || right.isSatisfiedBy(topology);
    }
    
        @Override
    public String toString() {
        return String.format("(%s or %s)", left.toString(), right.toString());
    }


}