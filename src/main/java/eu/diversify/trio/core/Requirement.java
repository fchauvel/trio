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
package eu.diversify.trio.core;

import eu.diversify.trio.simulation.Topology;
import java.util.Set;

/**
 * Requirements can be combined use classical logical operators (i.e., and, or,
 * not)
 */
public interface Requirement {

    Requirement and(Requirement right);

    Requirement or(Requirement right);

    Requirement not();

    Requirement xor(Requirement right);

    boolean isSatisfiedBy(Topology topology);

    void accept(SystemListener listener); 
        
    int getComplexity();

    Set<String> getVariables();
}
