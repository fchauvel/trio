
package eu.diversify.trio.graph.queries;

import eu.diversify.trio.graph.Edge;
import eu.diversify.trio.graph.Node;
import java.util.Objects;

/**
 * Check whether a given edge enter the given node
 */
public class Reaching implements EdgePredicate {
    
    public static Reaching reaching(Node node) {
        return new Reaching(node);
    }
    
    private final Node target;

    public Reaching(Node target) {
        this.target = target;
    }

    @Override
    public boolean isSatisfiedBy(Edge edge) {
        return edge.reaches(target);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.target);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reaching other = (Reaching) obj;
        if (!Objects.equals(this.target, other.target)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("reaching %s", target);
    }
    
    
    
    
    
}
