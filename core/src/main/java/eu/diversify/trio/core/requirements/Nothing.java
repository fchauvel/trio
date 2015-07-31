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
package eu.diversify.trio.core.requirements;

import eu.diversify.trio.core.AssemblyPart;
import eu.diversify.trio.core.AssemblyVisitor;
import eu.diversify.trio.simulation.AssemblyState;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The true boolean value. Singleton value
 *
 */
public class Nothing extends Requirement {

    private static final Object lock = new Object();
    private static Nothing instance = null;

    public static Nothing getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new Nothing();
            }
        }
        return instance;
    }

    public void begin(AssemblyVisitor visitor) {
        visitor.enter(this);
    }

    public void end(AssemblyVisitor visitor) {
        visitor.exit(this);
    }

    public final Collection<AssemblyPart> subParts() {
        return new ArrayList<AssemblyPart>(0);
    }

    public boolean isSatisfiedBy(AssemblyState population) {
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash;
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
        return true;
    }

    @Override
    public String toString() {
        return String.format("none");
    }

}
