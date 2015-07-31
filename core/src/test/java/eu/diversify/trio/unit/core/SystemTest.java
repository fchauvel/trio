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
package eu.diversify.trio.unit.core;

import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.core.Component;
import eu.diversify.trio.core.AssemblyVisitor;
import eu.diversify.trio.core.Tag;

import static eu.diversify.trio.core.requirements.Factory.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
 * Specification of a system definition
 */
@RunWith(JUnit4.class)
public class SystemTest extends TestCase {

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNullListOfComponents() {
        Collection<Component> components = null;
        new Assembly(components);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectEmptyListOfComponent() {
        Collection<Component> components = new ArrayList<Component>();
        new Assembly(components);
    }

    @Test
    public void shouldGiveAccessToTheGivenName() {
        final String name = "Foo";
        final List<Component> components = new ArrayList<Component>();
        components.add(new Component("A"));
        final List<Tag> tags = new ArrayList<Tag>();
        final Assembly system = new Assembly(name, components, tags);

        assertThat(system.getName(), is(equalTo(name)));
    }

    @Test
    public void shouldHaveTheDefaultNameWhenNoneIsGiven() {
        final List<Component> components = new ArrayList<Component>();
        components.add(new Component("A"));
        final List<Tag> tags = new ArrayList<Tag>();
        final Assembly system = new Assembly(components, tags);

        assertThat(system.getName(), is(equalTo(Assembly.DEFAULT_NAME)));
    }

    @Test
    public void shouldGivenAccessToTheComponentByIndex() {
        final Assembly system = new Assembly(new Component("A"), new Component("B", require("A")));

        assertThat(system.getComponent(0), is(equalTo(new Component("A"))));
        assertThat(system.getComponent(1), is(equalTo(new Component("B", require("A")))));
    }

    @Test
    public void shouldValidateComponentNames() {
        final Assembly system = new Assembly(new Component("A"), new Component("B", require("A")));

        assertThat("component 'A' should exist", system.hasComponentNamed("A"));
        assertThat("component 'X' should not exists", !system.hasComponentNamed("X"));
    }

    @Test
    public void shouldProvideTheNumberOfComponentItContains() {
        final Assembly system = new Assembly(new Component("A"), new Component("B", require("A")));

        assertThat(system.size(), is(equalTo(2)));
    }

    @Test
    public void shouldProvideTheIndexOfAComponentWithAGivenNmae() {
        final Assembly system = new Assembly(new Component("A"), new Component("B", require("A")));

        assertThat(system.indexOf("A"), is(equalTo(0)));
        assertThat(system.indexOf("B"), is(equalTo(1)));
    }

  
    @Test
    public void beginShouldTriggerEnterOnTheVisitor() {
        final Mockery context = new JUnit4Mockery();

        final Assembly system = new Assembly(new Component("A"));

        final AssemblyVisitor visitor = context.mock(AssemblyVisitor.class);

        context.checking(new Expectations() {
            {
                oneOf(visitor).enter(system);
            }
        });

        system.begin(visitor);

        context.assertIsSatisfied();
    }

    @Test
    public void endShouldTriggerExitOnTheVisitor() {
        final Mockery context = new JUnit4Mockery();

        final Assembly system = new Assembly(new Component("A"));

        final AssemblyVisitor visitor = context.mock(AssemblyVisitor.class);

        context.checking(new Expectations() {
            {
                oneOf(visitor).exit(system);
            }
        });

        system.end(visitor);

        context.assertIsSatisfied();
    }

}
