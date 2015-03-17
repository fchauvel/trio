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

import eu.diversify.trio.Trio;
import eu.diversify.trio.analysis.Analysis;
import eu.diversify.trio.analysis.RelativeRobustness;
import eu.diversify.trio.analysis.Robustness;

import static eu.diversify.trio.codecs.Builder.*;

import org.junit.Test;
import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.core.validation.InvalidSystemException;
import eu.diversify.trio.core.validation.Validator;
import eu.diversify.trio.data.DataSet;
import eu.diversify.trio.filter.TaggedAs;
import eu.diversify.trio.simulation.RandomFailureSequence;
import eu.diversify.trio.simulation.Scenario;
import java.util.Collection;
import java.util.LinkedList;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static eu.diversify.trio.core.Evaluation.evaluate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

/**
 * Check the calculation of robustness, in many different situation
 */
@RunWith(Parameterized.class)
public class RobustnessTest {

    private final Trio trio;
    private final String systemText;
    private final String observed;
    private final String controlled;
    private final double expectedRobustness;

    public RobustnessTest(String systemText, String observed, String controlled, double expectedRobustness) {
        this.trio = new Trio();
        this.systemText = systemText;
        this.observed = observed;
        this.controlled = controlled;
        this.expectedRobustness = expectedRobustness;
    }

    @Test
    public void testExample() throws InvalidSystemException {
        final Assembly example = build().systemFrom(systemText);

        final Validator validity = new Validator();
        evaluate(validity).on(example);
        validity.check();

        final Scenario scenario = new RandomFailureSequence(example, new TaggedAs(observed), new TaggedAs(controlled));
        final DataSet data = trio.run(scenario, 10000);
        final Analysis analysis = trio.analyse(data);

        assertThat("Wrong robustness",
                   analysis.metric(RelativeRobustness.NAME).distribution().mean(),
                   is(closeTo(expectedRobustness, TOLERANCE)));
    }

    private static final double TOLERANCE = 1e-2;

    @Parameterized.Parameters
    public static Collection<Object[]> makeExamples() {
        final Collection<Object[]> examples = new LinkedList<Object[]>();

        examples.add(new Object[]{
            "components: "
            + " - Client requires Server and VM "
            + " - Server requires VM"
            + " - VM "
            + "tags:"
            + " - 'internal' on Client, Server"
            + " - 'external' on VM",
            "internal",
            "external",
            0D});
        
        examples.add(new Object[]{
            "components: "
            + " - Client requires Server and VM_Client "
            + " - Server requires VM_Server "
            + " - VM_Server "
            + " - VM_Client "
            + "tags:"
            + " - 'internal' on Client, Server"
            + " - 'external' on VM_Client, VM_Server",
            "internal",
            "external",
            0.125});

        examples.add(new Object[]{
            "components: "
            + " - Client requires Server and VM_Client "
            + " - Server requires VM_Server1 or VM_Server2"
            + " - VM_Server1"
            + " - VM_Server2 "
            + " - VM_Client "
            + "tags:"
            + " - 'internal' on Client, Server"
            + " - 'external' on VM_Client, VM_Server1, VM_Server2",
            "internal",
            "external",
            7D/18});

        return examples;
    }

}
