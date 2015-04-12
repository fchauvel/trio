package eu.diversify.trio.graph;

import java.util.Objects;

/**
 * Represent an edge between two nodes (a.k.a. vertices)
 */
public class Edge {

    private final Node source;
    private final Node target;

    public Edge(Node source, Node target) {
        this.source = source;
        this.target = target;
    }
    
    public Node source() {
        return source;
    }
    
    public Node target() {
        return target;
    }
    
    public boolean leaves(Node node) {
        return this.source.equals(node);
    }

    public boolean reaches(Node node) {
        return this.target.equals(node);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.source);
        hash = 31 * hash + Objects.hashCode(this.target);
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
        final Edge other = (Edge) obj;
        if (!Objects.equals(this.source, other.source)) {
            return false;
        }
        if (!Objects.equals(this.target, other.target)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Edge " + source + " -> " + target;
    }

}
