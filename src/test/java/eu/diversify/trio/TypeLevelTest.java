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

import java.io.FileNotFoundException;
import junit.framework.TestCase;
import org.cloudml.core.Deployment;
import org.cloudml.core.samples.SensApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static eu.diversify.trio.Action.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Specification of the robustness calculator
 */
@RunWith(JUnit4.class)
public class TypeLevelTest extends TestCase {

    @Test
    public void noVMInSensAppShouldBeACompleteSequence() throws FileNotFoundException {
        final AbstractPopulation population = new TypeLevel(SensApp.completeSensApp().build());

        population.reviveAll();
        population.kill("SL");
        population.kill("ML");

        assertThat("sensapp should have no survivor once ML & SL are killed", population.survivorCount(), is(equalTo(0)));
    }

    @Test
    public void robustnessOfASingleVMShouldBe100Percent() {
        final Deployment deployment = CloudML.aSingleVM("X").build();

        final double robustness = new Simulator(new TypeLevel(deployment)).run(reviveAll(), kill("X")).robustness();

        assertThat(robustness, is(equalTo(100.0)));
    }

    @Test
    public void robustnessOfTwoIndependentVMsShouldBe100Percent() {
        final Deployment deployment = CloudML.twoIndependentVMs("X", "Y").build();
        final Simulator simulator = new Simulator(new TypeLevel(deployment));

        final double r1 = simulator.run(reviveAll(), kill("X"), kill("Y")).robustness();
        assertThat(r1, is(equalTo(100.0)));

        final double r2 = simulator.run(reviveAll(), kill("Y"), kill("X")).robustness();
        assertThat(r2, is(equalTo(100.0)));

    }

    @Test
    public void robustnessOfAnAppDeployedOnItsVMShouldBe100Percent() {
        final Deployment deployment = CloudML.anAppOnAVm("app", "vm").build();
        final Simulator simulator = new Simulator(new TypeLevel(deployment));

        final Extinction sequence = simulator.run(reviveAll(), kill("app"), kill("vm"));
        final double robustness = sequence.robustness();

        assertThat(sequence.toString(), robustness, is(anyOf(closeTo(50.0, 1e-3), closeTo(200D / 3, 1e-3), closeTo(100D, 1e-3))));
    }

    @Test
    public void killingTheAppAndThenTheVMShouldCompleteTheExtinction() {
        final Deployment deployment = CloudML.anAppOnAVm("app", "vm").build();
        final Simulator simulator = new Simulator(new TypeLevel(deployment));

        final Extinction sequence = simulator.run(reviveAll(), kill("app"), kill("vm"));

        assertThat(sequence.after(0).survivorCount(), is(equalTo(2)));
        assertThat(sequence.after(1).survivorCount(), is(equalTo(1)));
        assertThat(sequence.after(2).survivorCount(), is(equalTo(0)));
    }

    @Test
    public void killingTheVMShouldBeACompleteSequence() {
        final Deployment deployment = CloudML.anAppOnAVm("app", "vm").build();

        final Simulator simulator = new Simulator(new TypeLevel(deployment));

        final Extinction sequence = simulator.run(reviveAll(), kill("vm"));

        assertThat(sequence.after(0).survivorCount(), is(equalTo(2)));
        assertThat(sequence.after(1).survivorCount(), is(equalTo(0)));
    }

    @Test
    public void meanRobustnessOfAnAppDeployedOnItsVMShouldBeAround86Percent() {
        final Deployment deployment = CloudML.anAppOnAVm().build();
        final Simulator simulator = new Simulator(new TypeLevel(deployment));

        final SequenceGroup sequences = simulator.randomExtinctions(100);
        final double robustness = sequences.robustness().mean();

        assertThat(sequences.toString(), robustness, is(closeTo(86.111, 10.0)));
    }

    @Test
    public void meanRobustnessOfAnAppAndItsDependencyOnAVmShouldBeBetween50And100Percent() {
        final Deployment deployment = CloudML.anAppAndItsDependencyOnAVm().build();

        final Simulator simulator = new Simulator(new TypeLevel(deployment));

        final SequenceGroup sequence = simulator.randomExtinctions(100);
        final double robustness = sequence.robustness().mean();

        assertThat(sequence.toString(), robustness, is(both(greaterThanOrEqualTo(50D)).and(lessThanOrEqualTo(100D))));
    }

}