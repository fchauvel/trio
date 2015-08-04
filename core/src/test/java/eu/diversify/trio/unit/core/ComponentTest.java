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
package eu.diversify.trio.unit.core;

import eu.diversify.trio.core.Component;
import eu.diversify.trio.core.AssemblyVisitor;
import eu.diversify.trio.core.requirements.Requirement;
import eu.diversify.trio.core.requirements.Require;

import junit.framework.TestCase;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Specifications of components
 */
@RunWith(JUnit4.class)
public class ComponentTest extends TestCase {

    @Test
    public void shouldProvideAccessToItsName() {
        final Component component = new Component("Foo");

        assertThat(component.getName(), is(equalTo("Foo")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNullNames() {
        new Component(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNullRequirement() {
        new Component("Foo", null);
    }

    @Test
    public void shouldExposeItsMTTF() {
        final double mttf = 43.5;
        final Component component = new Component("Foo", mttf);

        assertThat(component.meanTimeToFailure(), is(equalTo(mttf)));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNegativeMTTF() {
        new Component("Foo", -3.4);
    }
    
     @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNaNAsMTTF() {
        new Component("Foo", Double.NaN);
    }

    @Test
    public void shouldExposeItsRequirement() {
        final Component component = new Component("Foo", new Require("Bar"));
        final Requirement expected = new Require("Bar");

        assertThat(component.getRequirement(), is(equalTo(expected)));
    }

    @Test
    public void beginShouldTriggerEnterOnTheVisitor() {
        final Mockery context = new JUnit4Mockery();

        final Component component = new Component("A");

        final AssemblyVisitor visitor = context.mock(AssemblyVisitor.class);

        context.checking(new Expectations() {
            {
                oneOf(visitor).enter(component);
            }
        });

        component.begin(visitor);

        context.assertIsSatisfied();
    }

    @Test
    public void endShouldTriggerExitOnTheVisitor() {
        final Mockery context = new JUnit4Mockery();

        final Component component = new Component("A");

        final AssemblyVisitor visitor = context.mock(AssemblyVisitor.class);

        context.checking(new Expectations() {
            {
                oneOf(visitor).exit(component);
            }
        });

        component.end(visitor);

        context.assertIsSatisfied();
    }

    @Test
    public void subPartShouldNeverBeEmpty() {
        final Component component = new Component("A");

        assertThat("has sub parts", component.subParts(), is(not(empty())));
    }

}
