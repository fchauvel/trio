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

import eu.diversify.trio.core.AssemblyPart;
import eu.diversify.trio.core.AssemblyVisitor;
import eu.diversify.trio.simulation.Topology;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Require that another component exists
 */
public class Require extends Requirement {

    private final String requiredComponent;

    public Require(String requiredComponent) {
        eu.diversify.trio.util.Require.notNull(requiredComponent, "'null' given as required component name!");
        this.requiredComponent = requiredComponent;
    }

    public String getRequiredComponent() {
        return this.requiredComponent;
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

    public boolean isSatisfiedBy(Topology topology) {
        return topology.isActive(requiredComponent);
    }

    private int hash = 0;

    @Override
    public int hashCode() {
        if (hash == 0) {
            hash = Objects.hash(requiredComponent);
        }
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
        final Require other = (Require) obj;
        return this.requiredComponent.equals(other.requiredComponent);
    }

    @Override
    public String toString() {
        return requiredComponent;
    }
}
