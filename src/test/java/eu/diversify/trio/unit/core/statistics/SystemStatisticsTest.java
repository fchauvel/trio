/*
 */
package eu.diversify.trio.unit.core.statistics;

import eu.diversify.trio.core.statistics.Analysis;
import eu.diversify.trio.core.Component;
import eu.diversify.trio.core.System;
import eu.diversify.trio.core.statistics.SystemStatistics;

import static eu.diversify.trio.core.requirements.Factory.*;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 */
@RunWith(JUnit4.class)
public class SystemStatisticsTest extends TestCase {

    @Test
    public void conjunctionRatioShouldBeCorrect() {
        final System system = new System(
                new Component("A", require("B").and(require("C"))),
                new Component("B", require("D")),
                new Component("C"),
                new Component("D")
        );

        final SystemStatistics statistics = new Analysis(system).getResults();

        assertThat(statistics.getOperatorCount(), is(equalTo(4)));
        assertThat(statistics.getConjunctionCount(), is(equalTo(1)));
        assertThat(statistics.getConjunctionRatio(), is(closeTo(0.25, 1e-6)));
    }

    @Test
    public void disjunctionRatioShouldBeCorrect() {
        final System system = new System(
                new Component("A", require("B").or(require("C"))),
                new Component("B", require("D")),
                new Component("C"),
                new Component("D")
        );

        final SystemStatistics statistics = new Analysis(system).getResults();

        assertThat(statistics.getOperatorCount(), is(equalTo(4)));
        assertThat(statistics.getDisjunctionCount(), is(equalTo(1)));
        assertThat(statistics.getDisjunctionRatio(), is(closeTo(0.25, 1e-6)));
    }

    @Test
    public void requireRatioShouldBeCorrect() {
        final System system = new System(
                new Component("A", require("B").or(require("C"))),
                new Component("B", require("D")),
                new Component("C"),
                new Component("D")
        );

        final SystemStatistics statistics = new Analysis(system).getResults();

        assertThat(statistics.getOperatorCount(), is(equalTo(4)));
        assertThat(statistics.getRequireCount(), is(equalTo(3))); 
        assertThat(statistics.getRequireRatio(), is(closeTo(0.75, 1e-6)));
    }
    
    
    @Test
    public void negationRatioShouldBeCorrect() {
        final System system = new System(
                new Component("A", require("B").or(require("C")).not()),
                new Component("B", require("D")),
                new Component("C"),
                new Component("D")
        );

        final SystemStatistics statistics = new Analysis(system).getResults();

        assertThat(statistics.getOperatorCount(), is(equalTo(5)));
        assertThat(statistics.getNegationCount(), is(equalTo(1))); 
        assertThat(statistics.getNegationRatio(), is(closeTo(0.2, 1e-6)));
    }

}
