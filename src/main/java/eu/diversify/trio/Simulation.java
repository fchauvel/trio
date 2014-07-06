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

import java.util.List;
import java.util.Random;

/**
 * Represent the simulation of an extinction sequence
 */
public class Simulation {
        
    private final AbstractPopulation subject;
    private final Extinction trace;

    public Simulation(AbstractPopulation subject) {
        this.subject = subject;
        this.trace = new Extinction(subject.headcount());
    }
       
    public boolean hasSurvivors() {
        return !subject.getSurvivorNames().isEmpty();
    }

    public String pickRandomVictim() {
        final List<String> survivors = subject.getSurvivorNames();
        if (survivors.isEmpty()) {
            return null;
        }
        if (survivors.size() == 1) {
            return survivors.get(0);
        }
        int draw = new Random().nextInt(survivors.size());
        return survivors.get(draw);
    }
    
    public void kill(String victim) {
        subject.kill(victim);
        trace.record(Action.kill(victim), subject.survivorCount());
    }
    
    public void reviveAll() {
        subject.reviveAll();
        trace.record(Action.reviveAll(), subject.survivorCount());
    }
    
    public Extinction getTrace() {
        return this.trace;
    }
    
}
