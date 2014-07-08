
package eu.diversify.trio.requirements;

import eu.diversify.trio.Requirement;
import eu.diversify.trio.Topology;
import eu.diversify.trio.requirements.AbstractRequirement;

/**
 * Logical conjunction between two requirements
 */
public class Conjunction extends AbstractRequirement {

    private final Requirement left;
    private final Requirement right;

    public Conjunction(Requirement left, Requirement right) {
        this.left = left;
        this.right = right;
    }

    public boolean isSatisfiedBy(Topology topology) {
        return left.isSatisfiedBy(topology) && right.isSatisfiedBy(topology);
    }

    @Override
    public String toString() {
        return String.format("(%s and %s)", left.toString(), right.toString());
    }   
    
}
