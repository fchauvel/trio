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

package eu.diversify.trio;

/**
 * Capture the action of kill one individual
 */
public class Kill extends Action {
    
    private final String victim;

    public Kill(String victim) {
        super(1);
        this.victim = victim;
    }

    @Override
    public void applyTo(Simulation simulation) {
        simulation.kill(victim);  
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Kill)) {
            return false;
        }
        Kill otherKill = (Kill) other;
        return otherKill.victim.equals(victim);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.victim != null ? this.victim.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("kill '%s'", victim);
    }
    
    
    
    
    
}
