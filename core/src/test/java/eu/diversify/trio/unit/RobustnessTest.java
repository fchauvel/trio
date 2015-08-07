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

package eu.diversify.trio.unit;

import eu.diversify.trio.Samples;
import eu.diversify.trio.Trio;
import eu.diversify.trio.core.storage.Storage;
import eu.diversify.trio.core.storage.StorageError;

import org.junit.Test;
import eu.diversify.trio.core.validation.InvalidSystemException;
import eu.diversify.trio.simulation.filter.TaggedAs;
import eu.diversify.trio.simulation.RandomSimulation;
import eu.diversify.trio.simulation.Simulation;
import eu.diversify.trio.simulation.RandomSimulation;
import java.util.Collection;
import java.util.LinkedList;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

/**
 * Check the calculation of robustness, in many different situation
 */
@RunWith(Parameterized.class)
public class RobustnessTest {

    private final Trio trio;
    private final Simulation simulation;
    private final double expectedRobustness;

    public RobustnessTest(Storage storage, Simulation simulation, double expectedRobustness) {
        this.trio = new Trio(storage);
        this.simulation = simulation;
        this.expectedRobustness = expectedRobustness;
    }

    @Test
    public void test() throws InvalidSystemException, StorageError {

        final TrioResponse response = new TrioResponse();
        trio.run(simulation, 100000, response);

        assertThat("Wrong robustness",
                response.robustness(),
                is(closeTo(expectedRobustness, TOLERANCE)));
    }

    private static final double TOLERANCE = 1e-3;

    @Parameterized.Parameters
    public static Collection<Object[]> makeExamples() {
        final Collection<Object[]> examples = new LinkedList<Object[]>();

        examples.add(new Object[]{
            Samples.A_require_B_or_C(),
            new RandomSimulation(),
            (3D / 9) * (4D / 6) + (2D / 9) * (2D / 6)
        });

        examples.add(new Object[]{
            Samples.A_require_B_and_C(),
            new RandomSimulation(),
            (2D / 3) * (1D / 9) + (1D / 3) * (3D / 9)
        });

        examples.add(new Object[]{
            Samples.ABC_with_circular_dependencies(),
            new RandomSimulation(),
            0D
        });

        examples.add(new Object[]{
            Samples.ABC_with_linear_dependencies(),
            new RandomSimulation(),
            (1D / 6) * (3D / 9) + (1D / 6) * (2D / 9) + (1D / 3) * (1D / 9)
        });

        examples.add(new Object[]{
            Samples.oneClientAndOneServer(),
            new RandomSimulation(new TaggedAs("internal"), new TaggedAs("external")),
            0D});

        examples.add(new Object[]{
            Samples.oneClientRequiresServerAndVM(),
            new RandomSimulation(new TaggedAs("internal"), new TaggedAs("external")),
            0.125});

        examples.add(new Object[]{
            Samples.oneClientRequiresClusteredServers(),
            new RandomSimulation(new TaggedAs("internal"), new TaggedAs("external")),
            7D / 18});
        
        examples.add(new Object[]{
            Samples.A_requires_B_and_C_with_MTTF(),
            new RandomSimulation(new TaggedAs("service"), new TaggedAs("resource")),
            0.25
        });

        return examples;
    }

}
