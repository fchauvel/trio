package eu.diversify.trio.core.requirements;

import eu.diversify.trio.core.SystemListener;
import eu.diversify.trio.simulation.Topology;
import java.util.HashSet;
import java.util.Set;

/**
 * Require that another component exists
 */
public class Require extends AbstractRequirement {

    private final String requiredComponent;

    public Require(String requiredComponent) {
        this.requiredComponent = requiredComponent;
    }
    
     public void accept(SystemListener listener) {
        listener.enterRequire(this);
        listener.exitRequire(this);
    }

    public boolean isSatisfiedBy(Topology topology) {
        return topology.isActive(requiredComponent);
    }
    
    public int getComplexity() {
        return 1;
    }

    public Set<String> getVariables() {
        final Set<String> result = new HashSet<String>();
        result.add(requiredComponent);
        return result;
    }
    
    @Override
    public String toString() {
        return String.format("%s", requiredComponent);
    }
}
