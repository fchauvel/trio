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

/**
 * The logical negation
 */
public class Negation extends AbstractRequirement {
    
    private final Requirement operand;

    public Negation(Requirement operand) {
        this.operand = operand;
    }

    public boolean isSatisfiedBy(Topology topology) {
        return !operand.isSatisfiedBy(topology);
    }
    
    @Override
    public int getComplexity() {
        return 1 + operand.getComplexity();
    }
    
    @Override
    public String toString() {
        return String.format("(not %s)".format(operand.toString()));
    }

}
