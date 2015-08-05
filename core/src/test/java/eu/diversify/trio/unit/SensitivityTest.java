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

import org.junit.Test;
import eu.diversify.trio.core.validation.InvalidSystemException;
import eu.diversify.trio.simulation.filter.TaggedAs;
import eu.diversify.trio.simulation.RandomFailureSequence;
import eu.diversify.trio.simulation.Simulation;
import java.util.Collection;
import java.util.LinkedList;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import eu.diversify.trio.core.storage.Storage;
import eu.diversify.trio.core.storage.StorageError;
import eu.diversify.trio.simulation.filter.Filter;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Check the calculation of robustness, in many different situation
 */
@RunWith(Parameterized.class)
public class SensitivityTest {

    private final Trio trio;
    private final Filter observed;
    private final Filter controlled;
    private final int controlCount;

    public SensitivityTest(Storage storage, Filter observed, Filter controlled, int controlCount) {
        this.trio = new Trio(storage);
        this.observed = observed;
        this.controlled = controlled;
        this.controlCount = controlCount;
    }

    @Test
    public void testExample() throws InvalidSystemException, StorageError {

        final Simulation scenario = new RandomFailureSequence(observed, controlled);

        final TrioResponse response = new TrioResponse();
        trio.run(scenario, 10000, response);

        assertThat("Missing sensitivity",
                response.sensitivities().size(),
                is(equalTo(controlCount)));

    }

    private static final double TOLERANCE = 1e-2;

    @Parameterized.Parameters
    public static Collection<Object[]> makeExamples() {
        final Collection<Object[]> examples = new LinkedList<Object[]>();

        examples.add(new Object[]{
            Samples.oneClientAndOneServer(),
            new TaggedAs("internal"),
            new TaggedAs("external"),
            1});

        examples.add(new Object[]{
            Samples.oneClientRequiresServerAndVM(),
            new TaggedAs("internal"),
            new TaggedAs("external"),
            2});

        examples.add(new Object[]{
            Samples.oneClientRequiresClusteredServers(),
            new TaggedAs("internal"),
            new TaggedAs("external"),
            3});

        return examples;
    }

}
