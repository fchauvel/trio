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

import eu.diversify.trio.simulation.Topology;
import eu.diversify.trio.core.System;
import eu.diversify.trio.core.Component;
import eu.diversify.trio.core.Tag;
import eu.diversify.trio.core.requirements.Require;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import junit.framework.TestCase;
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
        new System(components);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectEmptyListOfComponent() {
        Collection<Component> components = new ArrayList<Component>();
        new System(components);
    }

    @Test
    public void shouldGiveAccessToTheGivenName() {
        final String name = "Foo";
        final List<Component> components = new ArrayList<Component>();
        components.add(new Component("A"));
        final List<Tag> tags = new ArrayList<Tag>();
        final System system = new System(name, components, tags);

        assertThat(system.getName(), is(equalTo(name)));
    }

    @Test
    public void shouldHaveTheDefaultNameWhenNoneIsGiven() {
        final List<Component> components = new ArrayList<Component>();
        components.add(new Component("A"));
        final List<Tag> tags = new ArrayList<Tag>();
        final System system = new System(components, tags);

        assertThat(system.getName(), is(equalTo(System.DEFAULT_NAME)));
    }

    @Test
    public void shouldInstantiateNewTopology() {
        final System system = new System(
                new Component("Foo", new Require("Bar")),
                new Component("Bar")
        );

        final Topology topology = system.instantiate();

        assertThat("", topology.isActive("Foo"));
        assertThat("", topology.isActive("Bar"));
    }

    @Test
    public void shouldComputeTotalComplexityCorrectly() {
        final System system = new System(
                new Component("Foo", new Require("Bar")),
                new Component("Bar", new Require("Baz").or(new Require("Foo"))),
                new Component("Baz")
        );

        assertThat(system.getTotalComplexity(), is(equalTo(4)));
    }

    @Test
    public void shouldComputeMeanComplexityCorrectly() {
        final System system = new System(
                new Component("Foo", new Require("Bar")),
                new Component("Bar", new Require("Baz").or(new Require("Foo"))),
                new Component("Baz")
        );

        assertThat(system.getMeanComplexity(), is(closeTo(4D / 3, 1e-8)));
    }

    @Test
    public void densityShouldBeComputedCorrectly() {
        final System system = new System(
                new Component("Foo", new Require("Bar")),
                new Component("Bar", new Require("Baz").or(new Require("Foo"))),
                new Component("Baz")
        );

        final double density = system.getDensity();
        final double expected = (1 + 2) / 9D;

        assertThat(density, is(closeTo(expected, 1e-6)));
    }

}
