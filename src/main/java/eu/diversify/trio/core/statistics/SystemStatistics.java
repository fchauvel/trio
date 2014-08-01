package eu.diversify.trio.core.statistics;

/**
 * Gather various statistics on the system
 */
public class SystemStatistics {

    private final int operatorCount;
    private final int conjunctionCount;
    private final int disjunctionCount;
    private final int requireCount;
    private final int negationCount;

    SystemStatistics(int[] counters) {
        this.operatorCount = counters[Analysis.LOGICAL_OPERATORS];
        this.conjunctionCount = counters[Analysis.LOGICAL_CONJUNCTIONS];
        this.disjunctionCount = counters[Analysis.LOGICAL_DISJUNCTIONS];
        this.requireCount = counters[Analysis.LOGICAL_REQUIRE];
        this.negationCount = counters[Analysis.LOGICAL_NEGATION];
    }

    public int getConjunctionCount() {
        return this.conjunctionCount;
    }

    public double getConjunctionRatio() {
        return ((double) conjunctionCount) / operatorCount;
    }

    public int getOperatorCount() {
        return this.operatorCount;
    }

    public int getDisjunctionCount() {
        return this.disjunctionCount;
    }

    public double getDisjunctionRatio() {
        return ((double) disjunctionCount) / operatorCount;
    }

    public int getRequireCount() {
        return this.requireCount;
    }

    public double getRequireRatio() {
        return ((double) requireCount) / operatorCount;
    }
    
    
    public int getNegationCount() {
        return this.negationCount;
    }

    public double getNegationRatio() {
        return ((double) negationCount) / operatorCount;
    }

}
