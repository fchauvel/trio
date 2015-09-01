/**
 * This file is part of TRIO :: Graph.
 *
 * TRIO :: Graph is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Graph is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Graph.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
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

package eu.diversify.trio.graph.util;

/**
 * A value object to represent counts
 */
public class Count {
    
    private final int value;

    public Count(int value) {
        this.value = validate(value);
    }
    
    private int validate(int value) {
        if (value < 0) {
            final String description = String.format("A count must be positive (found %d)", value);
            throw new IllegalArgumentException(description);
        }
        return value;
    }
    
    public int value() {
        return this.value;
    }
    
    public boolean isEven() {
        return value % 2 == 0;
    }
    
    public boolean isOdd() {
        return !isEven();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.value;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Count other = (Count) obj;
        if (this.value != other.value) {
            return false;
        }
        return true;
    }
 
    
    
}
