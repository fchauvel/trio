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
 * Simulate extinction sequences on a given population
 */
public class Simulator {

    private final AbstractPopulation subject;

    public Simulator(AbstractPopulation subject) {
        this.subject = subject;
    }

    public Extinction run(Action... sequence) {
        Simulation simulation = new Simulation(subject);
        for (Action eachAction: sequence) {
            eachAction.applyTo(simulation);
        }
        return simulation.getTrace();
    }

    public Extinction randomExtinction() {
        final Simulation simulation = new Simulation(subject);
        simulation.reviveAll();
        while (simulation.hasSurvivors()) {
            String victim = simulation.pickRandomVictim();
            simulation.kill(victim);
        }
        return simulation.getTrace();
    }

    public SequenceGroup randomExtinctions(int count) {
        final SequenceGroup result = new SequenceGroup(subject.headcount());
        for (int i = 0; i < count; i++) {
            result.add(randomExtinction());
        }
        return result;
    }

}
