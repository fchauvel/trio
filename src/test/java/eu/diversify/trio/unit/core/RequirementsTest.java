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
package eu.diversify.trio.unit.core;

import eu.diversify.trio.simulation.Topology;
import eu.diversify.trio.core.System;
import eu.diversify.trio.core.Component;
import eu.diversify.trio.core.Requirement;
import eu.diversify.trio.core.requirements.Nothing;
import eu.diversify.trio.core.requirements.Require;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.*;

@RunWith(JUnit4.class)
public class RequirementsTest extends TestCase {

    private Topology sampleTopologyWithAAndB() {
        final System system = new System(new Component("A"), new Component("B"));
        return system.instantiate();
    }

    @Test
    public void shouldDetectSurivor() {
        final Topology p = sampleTopologyWithAAndB();
        final Require req = new Require("A");

        assertThat("should be alive", req.isSatisfiedBy(p));
    }

    @Test
    public void shouldDetectSingleDeadComponent() {
        final Topology p = sampleTopologyWithAAndB();
        p.inactivate("A");

        final Require req = new Require("A");

        assertThat("should be dead", !req.isSatisfiedBy(p));
    }

    @Test
    public void shouldDetectConjunctionOfSurvivors() {
        final Topology p = sampleTopologyWithAAndB();

        final Requirement req = new Require("A").and(new Require("B"));

        assertThat("should be satisfied", req.isSatisfiedBy(p));
    }

    @Test
    public void conjunctionShouldNotBeSatisifiedWhenOneEndIsMissing() {
        final Topology p = sampleTopologyWithAAndB();
        p.inactivate("A");

        final Requirement req = new Require("A").and(new Require("B"));

        assertThat("should not be satisfied", !req.isSatisfiedBy(p));
    }

    @Test
    public void conjunctionShouldNotBeSatisifiedWhenBothEndsAreMissing() {
        final Topology p = sampleTopologyWithAAndB();
        p.inactivate("A");
        p.inactivate("B");

        final Requirement req = new Require("A").and(new Require("B"));

        assertThat("should not be satisfied", !req.isSatisfiedBy(p));
    }

    @Test
    public void disjunctionShouldBeSatisifiedWhenOneEndIsMissing() {
        final Topology p = sampleTopologyWithAAndB();
        p.inactivate("A");

        final Requirement req = new Require("A").or(new Require("B"));

        assertThat("should be satisfied", req.isSatisfiedBy(p));
    }

    @Test
    public void disjunctionShouldNotBeSatisifiedWhenOneEndIsMissing() {
        final Topology p = sampleTopologyWithAAndB();
        p.inactivate("A");
        p.inactivate("B");

        final Requirement req = new Require("A").or(new Require("B"));

        assertThat("should not be satisfied", !req.isSatisfiedBy(p));
    }

    @Test
    public void negationShouldBeSatisfiedWhenOperandIsMissing() {
        final Topology p = sampleTopologyWithAAndB();
        p.inactivate("A");

        final Requirement req = new Require("A").not();

        assertThat("should be satisfied", req.isSatisfiedBy(p));
    }

    @Test
    public void negationShouldNotBeSatisfiedWhenOperandExists() {
        final Topology p = sampleTopologyWithAAndB();

        final Requirement req = new Require("A").not();

        assertThat("should not be satisfied", !req.isSatisfiedBy(p));
    }

    @Test
    public void nothingShouldBeSatifisfied() {
        final Topology p = sampleTopologyWithAAndB();

        final Requirement req = Nothing.getInstance();

        assertThat("Should be satisfied", req.isSatisfiedBy(p));
    }

}
