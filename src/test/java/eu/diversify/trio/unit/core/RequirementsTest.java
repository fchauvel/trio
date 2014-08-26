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
import eu.diversify.trio.core.requirements.Requirement;
import eu.diversify.trio.core.requirements.Nothing;
import eu.diversify.trio.core.requirements.Require;

import static eu.diversify.trio.core.requirements.Factory.*;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

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
/*
    
    @Test
    public void requireShouldHaveAComplexityOfOne() {
        final Requirement require = require("X");
        assertThat(require.getComplexity(), is(equalTo(1)));
    }

    @Test
    public void complexityOfConjunctionsShouldBeTheSumOfTheOperandComplexity() {
        final Requirement require = require("X").and(require("Y").and(require("Z")));
        assertThat(require.getComplexity(), is(equalTo(5)));
    }

    @Test
    public void complexityOfDisjunctionsShouldBeTheSumOfTheOperandComplexity() {
        final Requirement require = require("X").or(require("Y").or(require("Z")));
        assertThat(require.getComplexity(), is(equalTo(5)));
    }

    @Test
    public void complexityOfNegationShouldBeOnePlusTheComplexityOfTheOperand() {
        final Requirement require = require("X").not();
        assertThat(require.getComplexity(), is(equalTo(2)));
    }

    @Test
    public void complexityOfNothingShouldBeOne() {
        final Requirement require = Nothing.getInstance();
        assertThat(require.getComplexity(), is(equalTo(1)));
    }

    @Test
    public void variableReferredTwiceInAConjunctionShouldBeCountedOnce() {
        final Requirement requirement = require("X").and(require("X"));

        assertThat(requirement.getVariables(), contains("X"));
    }

    @Test
    public void eachVariablesInAConjunctionShouldBeCounted() {
        final Requirement requirement = require("X").and(require("Y"));

        assertThat(requirement.getVariables(), containsInAnyOrder("X", "Y"));
    }

    @Test
    public void variableReferredTwiceInADisjunctionShouldBeCountedOnce() {
        final Requirement requirement = require("X").or(require("X"));

        assertThat(requirement.getVariables(), contains("X"));
    }

    @Test
    public void eachVariableDisjunctionShouldBeCounted() {
        final Requirement requirement = require("X").and(require("Y"));

        assertThat(requirement.getVariables(), containsInAnyOrder("X", "Y"));
    }

    public void eachVariableInNegationShouldBeCounted() {
        final Requirement requirement = not(require("X").and(require("Y")));

        assertThat(requirement.getVariables(), containsInAnyOrder("X", "Y"));
    }

    public void nothingShouldReferNoVariable() {
        final Requirement requirement = nothing();

        assertThat(requirement.getVariables(), is(empty()));
    }
    
    public void requireShouldReferToOneVariable() {
        final Requirement requirement = require("A");
        
        assertThat(requirement.getVariables(), contains("A"));
    }
    
    public void xorShouldBeExpandedCorrectly() {
        final Requirement xor = require("X").xor(require("Y"));
        
        assertThat(xor, is(equalTo(require("X").or(require("Y").and(not(require("X").and(require("Y"))))))));
    }
 */
}
