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
package eu.diversify.trio.builder;

import eu.diversify.trio.Component;
import org.junit.Test;
import eu.diversify.trio.System;
import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import static eu.diversify.trio.builder.Builder.*;



/**
 *
 */
@RunWith(JUnit4.class)
public class BuilderTest extends TestCase {

    @Test
    public void shouldBuildComponentWithoutRequirements() {
        final String text = "component AA";

        final Component component = build().componentFrom(text);
        
        assertThat(component, is(equalTo(new Component("AA"))));     
    }

    @Test
    public void readSimpleSystem() {
        final String text
                = "component AA"
                + "component BB";

        final System expected = new System(
                new Component("AA"),
                new Component("BB")
        );

        final System system = build().systemFrom(text);

    }

}
