
package eu.diversify.trio.unit.core;

import eu.diversify.trio.core.Component;
import eu.diversify.trio.core.Generator;
import eu.diversify.trio.core.System;
import eu.diversify.trio.core.Tag;
import eu.diversify.trio.core.requirements.Conjunction;
import eu.diversify.trio.core.requirements.Disjunction;
import eu.diversify.trio.core.requirements.Negation;
import eu.diversify.trio.core.requirements.Require;

import static eu.diversify.trio.core.requirements.Factory.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Specification of the generator
 */
@RunWith(JUnit4.class)
public class GeneratorTest extends TestCase {

    private static final class RandomStub extends Random {

        private static final long serialVersionUID = 1L;

        
        
        @Override
        public int nextInt(int n) {
            return 0;
        }

        @Override
        public double nextDouble() {
            return 0.99;
        }

    }

    private final Generator generate;

    public GeneratorTest() {
        this.generate = new Generator(new RandomStub());  
    }

    @Test
    public void shouldGenerateCorrectSystems() {
        final System random = generate.randomSystem(2);

        final Collection<Component> components = new ArrayList<Component>();
        components.add(new Component("C1", require("C2")));
        components.add(new Component("C2", require("C1")));
        final Collection<Tag> tags = new ArrayList<Tag>();
        final System expected = new System("randomly generated", components, tags);

        assertThat(random, is(equalTo(expected)));
    }

    @Test
    public void randomComponentShouldGenerateACorrectComponent() {
        final List<String> names = Arrays.asList(new String[]{"C4", "C3", "C2"});
        final Component random = generate.randomComponent(names, "C5");

        assertThat(random, is(equalTo(new Component("C5", new Require("C4")))));
    }

    @Test
    public void randomRequireShouldGenerateACorrectRequrie() {
        final List<String> names = Arrays.asList(new String[]{"C4", "C3", "C2"});
        final Require randomRequire = generate.randomRequire(names);

        assertThat(randomRequire, is(equalTo(new Require("C4"))));
    }

    @Test
    public void nextNegationShouldProduceTheCorrectExpression() {
        final List<String> names = Arrays.asList(new String[]{"C4", "C3", "C2"});
        final Negation randomNegation = generate.randomNegation(names);

        assertThat(randomNegation, is(equalTo(new Require("C4").not())));
    }

    @Test
    public void nextConjunctionShouldProduceTheCorrectConjunction() {
        final List<String> names = Arrays.asList(new String[]{"C4", "C3", "C2"});
        final Conjunction randomConjunction = generate.randomConjunction(names);

        assertThat(randomConjunction, is(equalTo(new Require("C4").and(new Require("C4")))));
    }

    @Test
    public void nextDisjunctionShouldProduceTheCorrectDisjunction() {
        final List<String> names = Arrays.asList(new String[]{"C4", "C3", "C2"});
        final Disjunction randomDisjunction = generate.randomDisjunction(names);

        assertThat(randomDisjunction, is(equalTo(new Require("C4").or(new Require("C4")))));
    }

    @Test
    public void sandbox() {
        final Generator generate = new Generator();
        final System system = generate.randomSystem(100);
        java.lang.System.out.println(system);
    }

}
