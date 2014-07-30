
package eu.diversify.trio.core.requirements;

import eu.diversify.trio.core.Requirement;
import eu.diversify.trio.core.SystemListener;
import eu.diversify.trio.simulation.Topology;
import java.util.Set;

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

     public void accept(SystemListener listener) {
        listener.enterDisjunction(this);
        left.accept(listener);
        right.accept(listener);
        listener.exitDisjunction(this);
    }
    
    public boolean isSatisfiedBy(Topology topology) {
        return left.isSatisfiedBy(topology) || right.isSatisfiedBy(topology);
    }
    
    @Override
    public int getComplexity() {
        return 1 + left.getComplexity() + right.getComplexity();
    }

    public Set<String> getVariables() {
        Set<String> result = left.getVariables();
        result.addAll(right.getVariables());
        return result;
    }

    @Override
    public String toString() {
        return String.format("(%s or %s)", left.toString(), right.toString());
    }

}
