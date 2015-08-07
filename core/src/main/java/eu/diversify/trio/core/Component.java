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
package eu.diversify.trio.core;

import eu.diversify.trio.core.requirements.Requirement;
import eu.diversify.trio.simulation.AssemblyState;
import eu.diversify.trio.core.requirements.Nothing;
import eu.diversify.trio.util.Require;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A component is an entity of the system, whose status might be active or
 * inactive depending on others component's status.
 */
public class Component implements AssemblyPart {

    public static final double DEFAULT_MTTF = 1D;

    private final String name;
    private double mttf; // Must be final
    private final Requirement requirement;

    public Component(String name) {
        this(name, Nothing.getInstance());
    }

    public Component(String name, Requirement requirement) {
        this(name, DEFAULT_MTTF, requirement);
    }

    public Component(String name, double mttf) {
        this(name, mttf, Nothing.getInstance());
    }

    public Component(String name, double mttf, Requirement requirement) {
        Require.notNull(name, "Illegal 'null' value given as name");
        this.name = name;

        this.mttf = checkMTTF(mttf);

        Require.notNull(requirement, "Illegal 'null' value given as requirement");
        this.requirement = requirement;
    }

    public Collection<AssemblyPart> subParts() {
        final List<AssemblyPart> subparts = new ArrayList<AssemblyPart>(1);
        subparts.add(requirement);
        return subparts;
    }

    private double checkMTTF(double mttf) {
        if (mttf <= 0) {
            final String error = String.format("MTTF must be positive (found %f)", mttf);
            throw new IllegalArgumentException(error);
        }
        if (Double.isNaN(mttf)) {
            final String error = "MTTF must be positive (found NaN)";
            throw new IllegalArgumentException(error);
        }
        return mttf;
    }

    public double meanTimeToFailure() {
        return mttf;
    }
    
    public void setMeanTimeToFailure(double mttf) {
        this.mttf = mttf;
    }

    public void begin(AssemblyVisitor visitor) {
        visitor.enter(this);
    }

    public void end(AssemblyVisitor visitor) {
        visitor.exit(this);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + this.name.hashCode();
        hash = 83 * hash + this.requirement.hashCode();
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
        final Component other = (Component) obj;
        return other.name.equals(name) && other.requirement.equals(requirement) && Math.abs(mttf - other.mttf) < TOLERANCE;
    }
    
    private static final double TOLERANCE = 1e-9;

    public String getName() {
        return name;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public boolean isSatisfiedIn(AssemblyState topology) {
        return requirement.isSatisfiedBy(topology);
    }

    @Override
    public String toString() {
        return String.format("%s [%f]: %s", name, mttf, requirement.toString());
    }

}
