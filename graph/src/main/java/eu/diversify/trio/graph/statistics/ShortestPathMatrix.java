package eu.diversify.trio.graph.statistics;

import eu.diversify.trio.graph.Edge;
import eu.diversify.trio.graph.Graph;
import eu.diversify.trio.graph.Node;
import eu.diversify.trio.graph.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * A matrix that contains all the shortest paths in a graph
 */
public class ShortestPathMatrix {

    private final Graph graph;
    private final int nodeCount;
    private final List<Path> shortestPaths;

    public ShortestPathMatrix(Graph graph) {
        visited = new HashSet<>();
        this.graph = graph;
        nodeCount = graph.nodes().size();
        this.shortestPaths = new ArrayList<>((int) Math.pow(nodeCount, 2));
        for (Node eachSource : graph.nodes()) {
            for (Node eachTarget : graph.nodes()) {
                setShortestPath(eachSource, eachTarget, Path.infinite(eachSource));
            }
        }
    }

    public int eccentricityOf(Node node) {
        int maxDistance = 0;
        for (Node eachTarget : graph.nodes()) {
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
        for (Node eachNode : graph.nodes()) {
            maxEccenctricity = Math.max(maxEccenctricity, eccentricityOf(eachNode));
        }
        return maxEccenctricity;
    }

    public Path between(Node source, Node target) {
        visited.clear();
        final List<Node> frontier = new LinkedList<>();
        frontier.add(source);
        while (!frontier.isEmpty()) {
            final Node current = frontier.remove(0);
            visited.add(current);
            for (Edge eachOutgoingEdge : graph.edges().from(current)) {
                final Node neighbor = eachOutgoingEdge.target();
                if (!visited.contains(neighbor)) {
                    Path oldPath = getShortestPath(source, neighbor);
                    Path newPath = getShortestPath(source, current).append(neighbor);
                    setShortestPath(source, neighbor, Path.getShortest(newPath, oldPath));
                    frontier.add(neighbor);
                }
            }
        }
        return getShortestPath(source, target);
    }

    private final Set<Node> visited;

    private Path getShortestPath(Node source, Node target) {
        return shortestPaths.get(pathIndex(source, target));
    }

    private void setShortestPath(Node source, Node target, Path shortest) {
        final int pathIndex = pathIndex(source, target);
        if (shortestPaths.size() == pathIndex) {
            shortestPaths.add(shortest);
        } else {
            shortestPaths.set(pathIndex, shortest);
        }
    }

    private int pathIndex(Node source, Node target) {
        return source.index() * nodeCount + target.index();
    }

}
