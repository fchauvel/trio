
package eu.diversify.trio.requirements;

import eu.diversify.trio.Requirement;
import eu.diversify.trio.Topology;
import eu.diversify.trio.requirements.AbstractRequirement;

/**
 * Logical disjunction between requirements
 */
public class Disjunction extends AbstractRequirement {

    private final Requirement left;
    private final Requirement right;

    public Disjunction(Requirement left, Requirement right) {
        this.left = left;
        this.right = right;
    }

    public boolean isSatisfiedBy(Topology topology) {
        return left.isSatisfiedBy(topology) || right.isSatisfiedBy(topology);
    }
    
        @Override
    public String toString() {
        return String.format("(%s or %s)", left.toString(), right.toString());
    }


}
