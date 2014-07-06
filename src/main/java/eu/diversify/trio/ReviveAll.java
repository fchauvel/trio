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
 * Revive all individual, so that there is no dead ones. Used to start or
 * restart extinction sequence.
 *
 * Note: Singleton object
 */
public class ReviveAll extends Action {

    private static ReviveAll instance;

    public static ReviveAll getInstance() {
        if (instance == null) {
            instance = new ReviveAll();
        }
        return instance;
    }

    private ReviveAll() {
        super(0);
    }

    @Override
    public void applyTo(Simulation simulation) {
        simulation.reviveAll();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ReviveAll)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "revive all";
    }

}
