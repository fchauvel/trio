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
import eu.diversify.trio.core.SystemListener;
import eu.diversify.trio.simulation.Topology;
import java.util.Set;

/**
 * Logical conjunction between two requirements
 */
public class Conjunction extends AbstractRequirement {

    private final Requirement left;
    private final Requirement right;

    public Conjunction(Requirement left, Requirement right) {
        this.left = left;
        this.right = right;
    }

    public void accept(SystemListener listener) {
        listener.enterConjunction(this);
        left.accept(listener);
        right.accept(listener);
        listener.exitConjunction(this);
    }
    
    

    public boolean isSatisfiedBy(Topology topology) {
        return left.isSatisfiedBy(topology) && right.isSatisfiedBy(topology);
    }
    
    public int getComplexity() {
        return 1 + left.getComplexity() + right.getComplexity();
    }

    public Set<String> getVariables() {
       Set<String> result = left.getVariables();
       result.addAll(right.getVariables());
       return result;
    }    
    
    @Override
    public String toString() {
        return String.format("(%s and %s)", left.toString(), right.toString());
    }   
    
}
