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

package eu.diversify.trio.core.requirements.stats;

import eu.diversify.trio.core.DefaultAssemblyVisitor;


/**
 * Count the number of nodes in a requirements, including: Conjunction,
 * Negation, Disjunction, Require, and nothing.
 */
public class NodeCount extends DefaultAssemblyVisitor {

    private int count = 0;

    @Override
    protected void defaultVisit() {
        count++;
    }

    public int get() {
        return count;
    }
}
