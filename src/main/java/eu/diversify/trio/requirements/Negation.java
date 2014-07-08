

package eu.diversify.trio.requirements;

import eu.diversify.trio.Requirement;
import eu.diversify.trio.Topology;

/**
 * The logical negation
 */
public class Negation extends AbstractRequirement {
    
    private final Requirement operand;

    public Negation(Requirement operand) {
        this.operand = operand;
    }


    public boolean isSatisfiedBy(Topology topology) {
        return !operand.isSatisfiedBy(topology);
    }
    
    
    @Override
    public String toString() {
        return String.format("(not %s)".format(operand.toString()));
    }

}
