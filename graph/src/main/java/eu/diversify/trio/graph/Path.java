package eu.diversify.trio.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represent a path in a graph
 */
public class Path {

    public static Path getShortest(Path p1, Path p2) {
        if (p1.length() < p2.length()) {
            return p1;
        }
        return p2;
    }

    public static Path infinite(Node source) {
        return new Path(source);
    }

    private final List<Node> nodes;

    public Path(Node... nodes) {
        this(Arrays.asList(nodes));
    }

    public Path(List<Node> nodes) {
        this.nodes = new ArrayList<>(nodes);
    }

    public int length() {
        return isDefined() ? nodes.size() - 1 : Integer.MAX_VALUE;
    }

    public Path append(Node node) {
        final List<Node> copy = new ArrayList<>(nodes);
        copy.add(node);
        return new Path(copy);
    }
    
    public boolean isDefined() {
        return nodes.size() > 1;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.nodes);
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
        final Path other = (Path) obj;
        if (!Objects.equals(this.nodes, other.nodes)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "path " + nodes;
    }
    
    

}
