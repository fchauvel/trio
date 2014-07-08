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
package eu.diversify.trio.actions;

import eu.diversify.trio.Topology;

/**
 * Activate a given component
 */
public class Activate extends AbstractAction {

    private final String target;

    public Activate(String target) {
        this.target = target;
    }

    public Topology executeOn(Topology topology) {
        topology.activate(target); 
        return topology;
    }

    @Override
    public String toString() {
        return String.format("activate %s", target);
    }

}
