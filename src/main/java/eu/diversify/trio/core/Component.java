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

package eu.diversify.trio.core;

import eu.diversify.trio.simulation.Topology;
import eu.diversify.trio.core.requirements.Nothing;

/**
 * The definition of a component in the system
 */
public class Component {
    
    private final String name;
    private final Requirement requirement;

    public Component(String name) {
        this(name, Nothing.getInstance()); 
    }
    
    public Component(String name, Requirement requirement) {
        this.name = rejectIfNull(name);
        this.requirement = rejectIfNull(requirement);
    }
    
    private String rejectIfNull(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Illegal 'null' value given as name");
        }
        return name;
    }
    
    private Requirement rejectIfNull(Requirement requirement) {
        if (requirement == null) {
            throw new IllegalArgumentException("Illegal 'null' value given as requirement");
        }
        return requirement;
    }
    
    public void accept(SystemListener listener) {
        listener.enterComponent(this);
        requirement.accept(listener);
        listener.exitComponent(this);
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
        return other.name.equals(name) && other.requirement.equals(requirement);
    }
    
  
    public String getName() {
        return name;
    }
    
    public int getValency() {
        return requirement.getVariables().size();
    }

    public Requirement getRequirement() {
        return requirement;
    }
    
    public boolean isSatisfiedIn(Topology topology) {
        return requirement.isSatisfiedBy(topology);
    }
    
    @Override
    public String toString() {
        return String.format("%s: %s", name, requirement.toString());
    }
    
}
