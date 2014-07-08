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


package eu.diversify.trio;

/**
 * Action which can be performed on topologies
 */
public interface Action {

    /**
     * Execute this action on the given topology
     *
     * @param topology the topology on which this action shall be performed
     * @return the topology once the action is performed
     */
    Topology executeOn(Topology topology);

    
    /**
     * Compare this action with another object
     * @param object the object to compare with
     * @return  true if the given object is equivalent to this action
     */
    @Override
    boolean equals(Object object);
    
}
