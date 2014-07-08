package eu.diversify.trio;

import eu.diversify.trio.requirements.Nothing;
import eu.diversify.trio.requirements.Require;
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
