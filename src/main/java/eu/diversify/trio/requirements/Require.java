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
package eu.diversify.trio.requirements;

import eu.diversify.trio.Topology;

/**
 * Require that another component exists
 */
public class Require extends AbstractRequirement {

    private final String requiredComponent;

    public Require(String requiredComponent) {
        this.requiredComponent = requiredComponent;
    }

    public boolean isSatisfiedBy(Topology topology) {
        return topology.isActive(requiredComponent);
    }
    
        @Override
    public String toString() {
        return String.format("%s", requiredComponent);
    }

    public static Require require(String component) {
        return new Require(component);
    }
}
