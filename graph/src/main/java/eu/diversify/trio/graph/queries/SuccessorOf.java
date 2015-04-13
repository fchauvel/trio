package eu.diversify.trio.graph.queries;

import eu.diversify.trio.graph.Edge;
import eu.diversify.trio.graph.Graph;
import eu.diversify.trio.graph.Node;
import static eu.diversify.trio.graph.queries.Leaving.leaving;
import java.util.Objects;

/**
 * Check whether a node is the successor of the given node.
 */
public class SuccessorOf implements NodePredicate {

    public static SuccessorOf successorOf(Node node) {
        return new SuccessorOf(node);
    }

    private final Node source;

    public SuccessorOf(Node source) {
        this.source = source;
    }

    @Override
    public boolean isSatisfiedBy(Graph graph, Node node) {
        for (Edge anyEdge : graph.edges(leaving(source))) {
            if (anyEdge.reaches(node)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.source);
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
        final SuccessorOf other = (SuccessorOf) obj;
        if (!Objects.equals(this.source, other.source)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("successor of %s", source);
    }

}
