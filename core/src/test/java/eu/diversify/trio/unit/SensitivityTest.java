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
/**
 *
 * This file is part of TRIO.
 *
 * TRIO is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * TRIO is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO. If not, see <http://www.gnu.org/licenses/>.
 */
package eu.diversify.trio.unit;

import eu.diversify.trio.Samples;
import static eu.diversify.trio.core.storage.Builder.*;

import org.junit.Test;
import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.core.validation.InvalidSystemException;
import eu.diversify.trio.core.validation.Validator;
import eu.diversify.trio.simulation.filter.TaggedAs;
import eu.diversify.trio.simulation.RandomFailureSequence;
import eu.diversify.trio.simulation.Scenario;
import java.util.Collection;
import java.util.LinkedList;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static eu.diversify.trio.core.Evaluation.evaluate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Check the calculation of robustness, in many different situation
 */
@RunWith(Parameterized.class)
public class SensitivityTest {

    private final TrioService trio;
    private final String systemText;
    private final String observed;
    private final String controlled;
    private final int controlCount;

    public SensitivityTest(String systemText, String observed, String controlled, int expectedRobustness) {
        this.trio = new TrioService();
        this.systemText = systemText;
        this.observed = observed;
        this.controlled = controlled;
        this.controlCount = expectedRobustness;
    }

    @Test
    public void testExample() throws InvalidSystemException {
        final Assembly example = build().systemFrom(systemText);

        final Validator validity = new Validator();
        evaluate(validity).on(example);
        validity.check();

        final Scenario scenario = new RandomFailureSequence(1, example, new TaggedAs(observed), new TaggedAs(controlled));
        final TrioService.TrioResponse response = trio.run(scenario, 10000);

        assertThat("Missing sensitivity",
                response.sensitivities.size(),
                is(equalTo(controlCount)));

    }

    private static final double TOLERANCE = 1e-2;

    @Parameterized.Parameters
    public static Collection<Object[]> makeExamples() {
        final Collection<Object[]> examples = new LinkedList<Object[]>();

        examples.add(new Object[]{
            Samples.oneClientAndOneServer(),
            "internal",
            "external",
            1});

        examples.add(new Object[]{
            Samples.oneClientRequiresServerAndVM(),
            "internal",
            "external",
            2});

        examples.add(new Object[]{
            Samples.oneClientRequiresClusteredServers(),
            "internal",
            "external",
            3});

        return examples;
    }

}
