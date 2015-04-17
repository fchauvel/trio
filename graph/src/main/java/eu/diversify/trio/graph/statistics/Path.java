package eu.diversify.trio.graph.statistics;

import eu.diversify.trio.graph.model.Edge;
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

    private final List<Long> edgeIds;


    public Path(Long... edgeIds) {
        this(Arrays.asList(edgeIds));
    }

    public Path(List<Long> edgeIds) {
        this.edgeIds = new ArrayList<>(edgeIds);
    }

    public int length() {
        return isDefined() ? edgeIds.size() : Integer.MAX_VALUE;
    }

    public Path append(long edgeId) {
        final List<Long> copy = new ArrayList<>(edgeIds);
        copy.add(edgeId);
        return new Path(copy);
    }

    public Path append(Edge edge) {
        return append(edge.id());
    }

    public boolean isDefined() {
        return !edgeIds.isEmpty();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.edgeIds);
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
        return Objects.equals(this.edgeIds, other.edgeIds);
    }

    @Override
    public String toString() {
        return "path " + edgeIds;
    }

}
