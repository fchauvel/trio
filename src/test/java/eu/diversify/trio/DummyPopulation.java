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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A sample population for testing purpose
 */
public class DummyPopulation extends AbstractPopulation {

    private final Map<String, Boolean> dead;

    public DummyPopulation(String... individuals) {
        this.dead = new HashMap<String, Boolean>();
        for (String each: individuals) {
            dead.put(each, false);
        }
    }

    @Override
    public void kill(String victim) {
        dead.put(victim, true);
    }

    @Override
    public void reviveAll() {
        for(String each: dead.keySet()) {
            dead.put(each, false);
        }
    }

    @Override
    public List<String> getIndividualNames() {
        return new ArrayList<String>(dead.keySet());
    }


    @Override
    public List<String> getSurvivorNames() {
        final List<String> alive = new ArrayList<String>();
        for (String each: dead.keySet()) {
            if (!dead.get(each)) {
                alive.add(each);
            }
        }
        return alive;
    }   
    

}
