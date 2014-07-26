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
package eu.diversify.trio.filter;

import eu.diversify.trio.core.System;
import java.util.HashSet;
import java.util.Set;

/**
 * Logical conjunction
 */
public class And extends Filter {

    private final Filter left;
    private final Filter right;

    public And(Filter left, Filter right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Set<String> resolve(System system) {
        final Set<String> results = new HashSet<String>();
        results.addAll(left.resolve(system));
        results.retainAll(right.resolve(system));
        return results;
    }

    @Override
    public String toString() {
        return String.format("(%s and %s)", left.toString(), right.toString());
    }

}