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

import java.util.Set;

import static eu.diversify.trio.Action.*;

import junit.framework.TestCase;
import org.cloudml.core.samples.SensApp;

import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 */
@RunWith(JUnit4.class)
public class SimulatorTest extends TestCase {

    @Test
    public void allSequencesShouldBeDecreasing() {
        final AbstractPopulation population = new TypeLevel(SensApp.completeSensApp().build());
         
        final Simulator simulator = new Simulator(population);
        final SequenceGroup sequences = simulator.randomExtinctions(100);
        
        for(Extinction eachSequence: sequences) {
            int previous = eachSequence.after(0).survivorCount();
            for(int killedCount=1 ; killedCount < eachSequence.length() ; killedCount++) {
                final int current = eachSequence.after(killedCount).survivorCount();
                int loss = previous - current;
                assertThat("sequence " + eachSequence + " is constant or increasing!", loss, is(greaterThanOrEqualTo(1)));
                previous = current;
            }
        }
    }
    
    @Test
    public void aPredifinedSequence() {
        final AbstractPopulation population = new DummyPopulation("x", "y", "z");

        final Simulator simulator = new Simulator(population);
        final Extinction sequence = simulator.run(reviveAll(), kill("x"), kill("y"), kill("z"));

        assertThat(sequence.length(), is(equalTo(population.headcount() + 1)));
        assertThat(sequence.survivorCount(), is(equalTo(0)));
        assertThat(sequence.deadCount(), is(equalTo(population.headcount())));
    }

    @Test
    public void impactOfAction() {
        final AbstractPopulation population = new DummyPopulation("x", "y", "z");

        final Simulator simulator = new Simulator(population);
        final Extinction sequence = simulator.run(reviveAll(), kill("x"), kill("y"), kill("z"));

        assertThat(sequence.impactOf(kill("x")), is(equalTo(1)));
    }

    @Test
    public void robustnessShouldBeAvailableForEachExtinction() {
        final AbstractPopulation population = new DummyPopulation("x", "y", "z");

        final Simulator simulator = new Simulator(population);
        final Extinction sequence = simulator.run(reviveAll(), kill("x"), kill("y"), kill("z"));

        assertThat(sequence.robustness(), is(equalTo(100D)));
    }

    @Test
    public void groupShouldContainTheRightNumberOfSequence() {
        final AbstractPopulation population = new DummyPopulation("x", "y", "z");
        final Simulator simulator = new Simulator(population);
        final SequenceGroup group = simulator.randomExtinctions(10);

        assertThat(group.size(), is(equalTo(10)));
    }

    @Test
    public void groupShouldProvideRobustnessDistribution() {
        final AbstractPopulation population = new DummyPopulation("x", "y", "z");
        final Simulator simulator = new Simulator(population);
        final SequenceGroup group = simulator.randomExtinctions(10);
        Distribution robustness = group.robustness();

        assertThat(group.summary(), robustness.mean(), is(closeTo(100D, 1e-6)));
    }

    @Test
    public void groupShouldProvideARankingOfIndividuals() {
        final AbstractPopulation population = new DummyPopulation("x", "y", "z");
        final Simulator simulator = new Simulator(population);
        final SequenceGroup group = simulator.randomExtinctions(10);

        assertThat(group.summary(), group.ranking().size(), is(equalTo(population.headcount() + 1)));
    }

    @Test
    public void oneVMsShouldLeadToARobustnessOf83_333() {
        final TypeLevel population = new TypeLevel(CloudML.anAppAndNCandidateVMs(1).build());
        final Simulator simulator = new Simulator(population);
        final SequenceGroup group = simulator.randomExtinctions(100);

        verifyRobustnessValues(group, 100D, 200D / 3);

        assertThat(group.robustness().mean(), is(closeTo(83.334, 2)));
    }

    public void verifyRobustnessValues(final SequenceGroup group, Double... legalValues) {
        final Set<Double> actualValues = group.robustness().values();
        for (Double eachRobustness: legalValues) {
            assertThat(actualValues, hasItem(closeTo(eachRobustness, 1e-6)));
        }
        assertThat("Wrong number of values " + actualValues, actualValues.size(), is(equalTo(legalValues.length)));
    }

    @Test
    public void twoAlternativeVMsShouldLeadToARobustnessOf94_444() {
        final TypeLevel population = new TypeLevel(CloudML.anAppAndNCandidateVMs(2).build());
        final Simulator simulator = new Simulator(population);
        final SequenceGroup group = simulator.randomExtinctions(100);

        verifyRobustnessValues(group, 100D, 500D / 6);

        assertThat(group.robustness().mean(), is(closeTo(94.444, 2)));
    }

    @Test
    public void threeAlternativeVMsShouldLeadToARobustnessOf97_5() {
        final TypeLevel population = new TypeLevel(CloudML.anAppAndNCandidateVMs(3).build());
        final Simulator simulator = new Simulator(population);
        final SequenceGroup group = simulator.randomExtinctions(100);

        verifyRobustnessValues(group, 100D, 90D);

        assertThat(group.robustness().mean(), is(closeTo(97.5, 2)));
    }

    @Test
    public void fourAlternativeVMsShouldLeadToARobustnessOf98_667() {
        final TypeLevel population = new TypeLevel(CloudML.anAppAndNCandidateVMs(4).build());
        final Simulator simulator = new Simulator(population);
        final SequenceGroup group = simulator.randomExtinctions(100);

        verifyRobustnessValues(group, 100D, 1400D / 15);

        assertThat(group.robustness().mean(), is(closeTo(98.667, 2)));
    }

}
