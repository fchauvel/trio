package eu.diversify.trio.graph;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The list of Edge in a graph
 */
public class EdgeSet extends ArrayList<Edge> {

    public EdgeSet() {
    }

    public EdgeSet(int initialCapacity) {
        super(initialCapacity);
    }

    public EdgeSet(Collection<? extends Edge> c) {
        super(c);
    }

    public EdgeSet from(Node source) {
        final EdgeSet result = new EdgeSet();
        for (Edge eachEdge : this) {
            if (eachEdge.startsFrom(source)) {
                result.add(eachEdge);
            }
        }
        return result;
    }

    public EdgeSet to(Node source) {
        final EdgeSet result = new EdgeSet();
        for (Edge eachEdge : this) {
            if (eachEdge.arrivesAt(source)) {
                result.add(eachEdge);
            }
        }
        return result;
    }

}
