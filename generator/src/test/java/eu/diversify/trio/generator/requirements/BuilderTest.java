package eu.diversify.trio.generator.requirements;

import eu.diversify.trio.core.requirements.CachedFactory;
import eu.diversify.trio.core.requirements.Conjunction;
import eu.diversify.trio.core.requirements.Disjunction;
import static eu.diversify.trio.core.requirements.Factory.require;
import eu.diversify.trio.core.requirements.Negation;
import eu.diversify.trio.core.requirements.Require;
import eu.diversify.trio.core.requirements.Requirement;
import eu.diversify.trio.core.requirements.RequirementFactory;
import eu.diversify.trio.generator.requirements.Builder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

/**
 * Specification of the requirements builder
 */
public class BuilderTest {

    @Test
    public void resultShouldNotBeReadyAtFirst() {
        Builder builder = prepareBuilder();

        assertThat(builder.isResultReady(), is(false));
    }

    @Test(expected = IllegalStateException.class)
    public void resultShouldDisabledUntilItGetsReady() {
        Builder builder = prepareBuilder();
        builder.getResult();
    }

    @Test
    public void resultShouldEventuallyBeReady() {
        Builder builder = prepareBuilder();

        builder.addRequire(1);

        assertThat(builder.isResultReady(), is(true));
    }

    @Test
    public void shouldBuildRequire() {
        Builder builder = prepareBuilder();

        builder.addRequire(1);
        Requirement actual = builder.getResult();

        Requirement expected = new Require("C1");
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void shouldBuildBinaryConjunction() {
        Builder builder = prepareBuilder();

        builder.openConjunction();
        builder.addRequire(1);
        builder.addRequire(2);
        builder.closeOperator();
        Requirement actual = builder.getResult();

        Requirement expected = require("C1").and(require("C2"));
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void shouldNAryConjunction() {
        Builder builder = prepareBuilder();

        builder.openConjunction();
        builder.addRequire(1);
        builder.addRequire(2);
        builder.addRequire(3);
        builder.closeOperator();
        Requirement actual = builder.getResult();

        Requirement expected = new Conjunction(require("C1"), require("C2"), require("C3"));
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void shouldBuildBinaryDisjunction() {
        Builder builder = prepareBuilder();

        builder.openDisjunction();
        builder.addRequire(1);
        builder.addRequire(2);
        builder.closeOperator();
        Requirement actual = builder.getResult();

        Requirement expected = require("C1").or(require("C2"));
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void shouldNAryDisjunction() {
        Builder builder = prepareBuilder();

        builder.openDisjunction();
        builder.addRequire(1);
        builder.addRequire(2);
        builder.addRequire(3);
        builder.closeOperator();
        Requirement actual = builder.getResult();

        Requirement expected = new Disjunction(require("C1"), require("C2"), require("C3"));
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void shouldBuildNegation() {
        Builder builder = prepareBuilder();

        builder.addNot();
        builder.addRequire(1);
        Requirement actual = builder.getResult();

        Requirement expected = require("C1").not();
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void shouldBuildDoubleNegation() {
        Builder builder = prepareBuilder();

        builder.addNot();
        builder.addNot();
        builder.addRequire(1);
        Requirement actual = builder.getResult();

        Requirement expected = require("C1").not().not();
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void shouldBuildComplexExpression() {
        Builder builder = prepareBuilder();

        builder.openConjunction();
        builder.addNot();
        builder.addRequire(1);
        builder.addRequire(2);
        builder.openDisjunction();
        builder.addRequire(3);
        builder.addRequire(4);
        builder.addRequire(5);
        builder.closeOperator();
        builder.closeOperator();
        Requirement actual = builder.getResult();

        Requirement expected
                = new Conjunction(
                        new Negation(new Require("C1")),
                        new Require("C2"),
                        new Disjunction(
                                new Require("C3"),
                                new Require("C4"),
                                new Require("C5"))
                );

        assertThat(actual, is(equalTo(expected)));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldPreventClosingIncompleteConjunction() {
        Builder builder = prepareBuilder();

        builder.openConjunction();
        builder.closeOperator();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldPreventClosingIncompleteDisjunction() {
        Builder builder = prepareBuilder();

        builder.openDisjunction();
        builder.addRequire(1);
        builder.closeOperator();
    }
    
    @Test
    public void shouldDetectWhenClosingIsLegal() {
        Builder builder = prepareBuilder();

        builder.openDisjunction();
        builder.addRequire(1);
        builder.addRequire(2);
        
        assertThat(builder.canCloseOperator(), is(true));
    }
    
    
    @Test
    public void shouldDisableClosingWhenDone() {
        Builder builder = prepareBuilder();

        builder.openDisjunction();
        builder.addRequire(1);
        builder.addRequire(2);
        builder.closeOperator();
        
        assertThat(builder.canCloseOperator(), is(false));
    }
    
    @Test
    public void shouldDisableAllActionsWhenDone() {
         Builder builder = prepareBuilder();

        builder.openDisjunction();
        builder.addRequire(1);
        builder.addRequire(2);
        builder.closeOperator();
        
        assertThat(builder.getAllowedCommands(), is(empty()));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldPreventClosingANegation() {
        Builder builder = prepareBuilder();

        builder.addNot();
        builder.closeOperator();
    }

    private Builder prepareBuilder() {
        RequirementFactory factory = new CachedFactory();
        Builder builder = new Builder(factory);
        return builder;
    }

}
