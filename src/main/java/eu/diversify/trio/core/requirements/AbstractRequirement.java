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

/**
 *
 */
public abstract class AbstractRequirement implements Requirement {

    public final Requirement and(Requirement right) {
        return new Conjunction(this, right);
    }

    public final Requirement or(Requirement right) {
        return new Disjunction(this, right);
    }

    public final Requirement not() {
        return new Negation(this);
    }
    
    public final Requirement xor(Requirement right) {
        return this.or(right).and(this.and(right).not());
    }

    @Override
    public final boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof AbstractRequirement) {
            return toString().equals(object.toString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
    
    

    @Override
    public abstract String toString();

}
