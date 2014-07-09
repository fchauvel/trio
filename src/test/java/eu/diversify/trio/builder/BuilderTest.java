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
package eu.diversify.trio.builder;

import eu.diversify.trio.*;
import eu.diversify.trio.System;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static eu.diversify.trio.builder.Builder.*;
import static eu.diversify.trio.requirements.Require.require;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 */
@RunWith(JUnit4.class)
public class BuilderTest extends TestCase {

    @Test
    public void shouldReadConjunction() {
        final String text = "requires foo and bar";

        final Requirement requirement = build().requirementFrom(text);

        assertThat(requirement, is(equalTo(require("foo").and(require("bar")))));
    }

    @Test
    public void shouldReadDisjunction() {
        final String text = "requires foo or bar";

        final Requirement requirement = build().requirementFrom(text);

        assertThat(requirement, is(equalTo(require("foo").or(require("bar")))));
    }

    @Test
    public void shouldReadRequirementWithBrackets() {
        final String text = "requires foo or ( bar and qux )";

        final Requirement requirement = build().requirementFrom(text);

        assertThat(requirement, is(equalTo(require("foo").or(require("bar").and(require("qux"))))));
    }

    @Test
    public void shouldBuildComponentWithoutRequirements() {
        final String text = "AA";

        final Component component = build().componentFrom(text);

        assertThat(component, is(equalTo(new Component("AA"))));
    }

    @Test
    public void shouldReadComponentWithRequirements() {
        final String text = "AA requires BB and (CC or (DD and EE))";

        final Component component = build().componentFrom(text);

        final Component expected
                = new Component("AA",
                                require("BB")
                                .and(
                                        require("CC")
                                        .or(
                                                require("DD")
                                                .and(require("EE")))));
        
        assertThat(component, is(equalTo(expected)));
    }

    @Test
    public void shouldReadASystemWithoutTags() {
        final String text
                = "components:"
                + "      AA requires BB"
                + "      BB ";

        final System expected = new System(
                new Component("AA", require("BB")),
                new Component("BB")
        );

        final System system = build().systemFrom(text);

        assertThat(system, is(equalTo(expected)));
    }

}
