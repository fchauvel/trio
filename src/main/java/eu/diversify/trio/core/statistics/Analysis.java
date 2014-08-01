/*
 */
package eu.diversify.trio.core.statistics;

import eu.diversify.trio.core.AbstractSystemListener;
import eu.diversify.trio.core.System;
import eu.diversify.trio.core.requirements.Conjunction;
import eu.diversify.trio.core.requirements.Disjunction;
import eu.diversify.trio.core.requirements.Negation;
import eu.diversify.trio.core.requirements.Require;

/**
 *
 */
public class Analysis extends AbstractSystemListener {

    static final int LOGICAL_OPERATORS = 0;
    static final int LOGICAL_CONJUNCTIONS = 1;
    static final int LOGICAL_DISJUNCTIONS = 2;
    static final int LOGICAL_NEGATION = 3;
    static final int LOGICAL_REQUIRE = 4;

    private final int[] counters;

    public Analysis(System system) {
        counters = new int[NB_COUNTER];
        system.accept(this);
    }

    @Override
    protected void byDefault() {
        // Do nothing by default
    }

    @Override
    public void enterSystem(System system) {
        for (int i = 0; i < NB_COUNTER; i++) {
            counters[i] = 0;
        }
    }
    private static final int NB_COUNTER = 5;

    @Override
    public void exitConjunction(Conjunction conjunction) {
        counters[LOGICAL_OPERATORS]++;
        counters[LOGICAL_CONJUNCTIONS]++;
    }

    @Override
    public void exitRequire(Require require) {
        counters[LOGICAL_OPERATORS]++;
        counters[LOGICAL_REQUIRE]++;
    }

    @Override
    public void exitNegation(Negation negation) {
        counters[LOGICAL_OPERATORS]++;
        counters[LOGICAL_NEGATION]++;
    }

    @Override
    public void exitDisjunction(Disjunction disjunction) {
        counters[LOGICAL_OPERATORS]++;
        counters[LOGICAL_DISJUNCTIONS]++;
    }

    public SystemStatistics getResults() {
        return new SystemStatistics(counters);
    }

}
