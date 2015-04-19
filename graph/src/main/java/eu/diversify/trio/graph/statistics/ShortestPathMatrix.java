package eu.diversify.trio.graph.statistics;

import eu.diversify.trio.graph.model.Edge;
import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.graph.model.Vertex;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A matrix that contains all the shortest paths in a graph
 */
public class ShortestPathMatrix {

    private final Graph graph;
    private final Map<Route, Path> shortestPaths;
    private final Set<Vertex> visitedVertexes;

    public ShortestPathMatrix(Graph graph) {
        this.graph = graph;

        this.shortestPaths = new HashMap<>();
        for (Vertex eachSource : graph.vertexes()) {
            for (Vertex destination : graph.vertexes()) {
                shortestPaths.put(new Route(eachSource, destination), new Path());
            }
        }

        this.visitedVertexes = new HashSet<>();
    }

    public int eccentricityOf(Vertex node) {
        int maxDistance = 0;
        for (Vertex eachTarget : graph.vertexes()) {
            if (!node.equals(eachTarget)) {
                Path path = between(node, eachTarget);
                if (path.isDefined()) {
                    maxDistance = Math.max(maxDistance, path.length());
                }
            }
        }
        return maxDistance;
    }

    public int diameter() {
        int maxEccenctricity = 0;
        for (Vertex eachVertex : graph.vertexes()) {
            maxEccenctricity = Math.max(maxEccenctricity, eccentricityOf(eachVertex));
        }
        return maxEccenctricity;
    }

    public Path between(Vertex source, Vertex target) {
        visitedVertexes.clear();
        final List<Vertex> frontier = new LinkedList<>();
        frontier.add(source);
        while (!frontier.isEmpty()) {
            final Vertex current = frontier.remove(0);
            visitedVertexes.add(current);
            for (Edge anyOutgoingEdge : current.outgoingEdges()) {
                if (!visitedVertexes.contains(anyOutgoingEdge.destination())) {
                    final Path oldPath = shortestPaths.get(new Route(source, anyOutgoingEdge.destination()));
                    final Path newPath = shortestPaths.get(new Route(source, current));
                    if (oldPath.length() <= newPath.length() + 1) {
                        shortestPaths.put(new Route(source, anyOutgoingEdge.destination()), oldPath);
                    } else {
                        shortestPaths.put(new Route(source, anyOutgoingEdge.destination()), newPath.append(anyOutgoingEdge));
                    }
                    frontier.add(anyOutgoingEdge.destination());
                }
            }
        }
        
        return shortestPaths.get(new Route(source, target));
    }

    /**
     * A route is the specification of a path, i.e., only the needed source and
     * target.
     */
    private static class Route {

        private final int sourceId;
        private final int targetId;

        public Route(Vertex source, Vertex target) {
            this(source.id(), target.id());
        }

        public Route(int sourceId, int targetId) {
            this.sourceId = sourceId;
            this.targetId = targetId;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 37 * hash + this.sourceId;
            hash = 37 * hash + this.targetId;
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
            final Route other = (Route) obj;
            return this.sourceId == other.sourceId
                    && this.targetId == other.targetId;
        }

    }

}
