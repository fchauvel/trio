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

import eu.diversify.trio.Simulation;
import eu.diversify.trio.TypeLevel;
import eu.diversify.trio.AbstractPopulation;
import eu.diversify.trio.Extinction;
import junit.framework.TestCase;

import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Specification of a simulation given a population
 */
@RunWith(JUnit4.class)
public class SimulationTest extends TestCase {

    @Test
    public void aRandomSequence() {
        final AbstractPopulation population = new DummyPopulation("x", "y", "z");

        final Simulation simulation = new Simulation(population);
        simulation.reviveAll();
        while (simulation.hasSurvivors()) {
            String victim = simulation.pickRandomVictim();
            simulation.kill(victim);
        }
        Extinction sequence = simulation.getTrace();

        assertThat(sequence.length(), is(equalTo(population.headcount() + 1)));
        assertThat(sequence.survivorCount(), is(equalTo(0)));
        assertThat(sequence.deadCount(), is(equalTo(population.headcount())));
    }

    @Test
    public void killingTheOnlyVMShouldTakedownTheAppIfThereIsNoSubstitute() {
        final AbstractPopulation population = new TypeLevel(CloudML.anAppAndNCandidateVMs(1).build());
        final Simulation simulation = new Simulation(population);

        simulation.reviveAll();
        simulation.kill("VM no. 1");
        Extinction sequence = simulation.getTrace();

        assertThat(sequence.toString(), sequence.length(), is(equalTo(2)));
        assertThat("should have no survivors", !sequence.hasSurvivors());
    }

    @Test
    public void killingTheAppShouldLetTheVMalive() {
        final AbstractPopulation population = new TypeLevel(CloudML.anAppAndNCandidateVMs(1).build());
        final Simulation simulation = new Simulation(population);

        simulation.reviveAll();
        simulation.kill("App");
        Extinction sequence = simulation.getTrace();

        assertThat(sequence + " should be a two-step sequence", sequence.length(), is(equalTo(2)));
        assertThat(sequence + " should have 1 survivor", sequence.survivorCount(), is(equalTo(1)));
    }

    @Test
    public void killingTheOnlyVMShouldNotTakedownTheAppIfThereAreSubstitute() {
        final AbstractPopulation population = new TypeLevel(CloudML.anAppAndNCandidateVMs(2).build());
        final Simulation simulation = new Simulation(population);

        simulation.reviveAll();
        simulation.kill("VM no. 1");
        Extinction sequence = simulation.getTrace();

        assertThat(sequence.toString(), sequence.length(), is(equalTo(2)));
        assertThat(sequence + " should have 2 survivor", sequence.survivorCount(), is(equalTo(2)));
    }

    @Test
    public void killingTheOnlyTwoVMShouldNotTakedownTheAppIfThereIsNoOtherSubstitute() {
        final AbstractPopulation population = new TypeLevel(CloudML.anAppAndNCandidateVMs(2).build());
        final Simulation simulation = new Simulation(population);

        simulation.reviveAll();
        simulation.kill("VM no. 1");
        simulation.kill("VM no. 2");
        Extinction sequence = simulation.getTrace();

        assertThat(sequence.toString(), sequence.length(), is(equalTo(3)));
        assertThat(sequence + " should include two killings", sequence.killedCount(), is(equalTo(2)));
        assertThat(sequence + " should have no survivor", sequence.survivorCount(), is(equalTo(0)));
        
        assertThat(sequence.robustness(), is(closeTo(500D/6, 1e-3)));
    }

}
