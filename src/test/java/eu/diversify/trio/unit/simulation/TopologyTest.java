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
package eu.diversify.trio.unit.simulation;

import eu.diversify.trio.simulation.Topology;
import eu.diversify.trio.core.System;
import eu.diversify.trio.core.Component;

import static eu.diversify.trio.core.requirements.Require.*;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Specification of a topology
 */
@RunWith(JUnit4.class)
public class TopologyTest extends TestCase {

    @Test
    public void shouldProvideTheNumberOfSurvivors() {
        final System system = new System(new Component("A"), new Component("B"));
        final Topology sut = system.instantiate();

        assertThat("active count", sut.countActiveAndObserved(), is(equalTo(2)));
    }

    @Test
    public void capacityShouldRemainConstantRegardlessOfAction() {
        final System system = new System(new Component("Foo"), new Component("Bar"));
        final Topology sut = system.instantiate();

        sut.inactivate("Foo");

        assertThat("Survivor count", sut.getCapacity(), is(equalTo(2)));
    }

    @Test
    public void shouldSupportDisablingSpecificComponent() {
        final System system = new System(new Component("Foo"), new Component("Bar"));
        final Topology sut = system.instantiate();
        sut.inactivate("Foo");

        assertThat("Survivor count", sut.countActiveAndObserved(), is(equalTo(1)));

    }

    @Test
    public void shouldSupportEnablingSpecificComponent() {
        final System system = new System(new Component("Foo"), new Component("Bar"));
        final Topology sut = system.instantiate();
        sut.inactivate("Foo");
        sut.activate("Foo");

        assertThat("Survivor count", sut.countActiveAndObserved(), is(equalTo(2)));
    }

    @Test
    public void shouldSupportDetectingExistenceOfActiveComponents() {
        final System system = new System(new Component("Foo"), new Component("Bar"));
        final Topology sut = system.instantiate();

        assertThat("should have active components", sut.hasActiveAndObservedComponents());
    }

    @Test
    public void shouldSupportSelectingActiveComponents() {
        final System system = new System(new Component("Foo"), new Component("Bar"));
        final Topology sut = system.instantiate();

        assertThat("should have active components", sut.activeAndObservedComponents(), contains("Foo", "Bar"));
    }

    @Test
    public void disjunctionShouldRemainActiveDespiteOneRequirementMissing() {
        final System system = new System(
                new Component("A", require("B").or(require("C"))),
                new Component("B"),
                new Component("C"));

        final Topology sut = system.instantiate();
        sut.inactivate("B");

        assertThat("A should be active", sut.isActive("A"));
    }

    @Test
    public void disjunctionShouldBeInactiveWhenAllRequirementsAreMissing() {
        final System system = new System(
                new Component("A", require("B").or(require("C"))),
                new Component("B"),
                new Component("C"));

        final Topology sut = system.instantiate();
        sut.inactivate("B");
        sut.inactivate("C");

        assertThat("A should not be active", !sut.isActive("A"));
    }

    @Test
    public void conjunctionShouldBeInactiveRegarlessOfWhichDependencyIsMissing() {
        final System system = new System(
                new Component("A", require("B").and(require("C"))),
                new Component("B"),
                new Component("C"));

        final Topology sut = system.instantiate();
        sut.inactivate("C");

        assertThat("A should not be active", !sut.isActive("A"));
    }

    @Test
    public void conjunctionShouldBeInactiveWhenAllDependenciesAreMissing() {
        final System system = new System(
                new Component("A", require("B").and(require("C"))),
                new Component("B"),
                new Component("C"));

        final Topology sut = system.instantiate();
        sut.inactivate("C");
        sut.inactivate("B");

        assertThat("A should not be active", !sut.isActive("A"));
    }

    @Test
    public void inactivityShouldPropagatesAlongDependencies() {
        final System system = new System(
                new Component("A", require("B")),
                new Component("B", require("C")),
                new Component("C"));

        final Topology sut = system.instantiate();
        sut.inactivate("C");

        assertThat("A should not be active", !sut.isActive("A"));
    }

}
