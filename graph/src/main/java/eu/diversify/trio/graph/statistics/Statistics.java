package eu.diversify.trio.graph.statistics;

import eu.diversify.trio.graph.Graph;
import eu.diversify.trio.graph.Node;
import eu.diversify.trio.graph.Path;
import static eu.diversify.trio.graph.queries.PredecessorOf.predecessorOf;
import static eu.diversify.trio.graph.queries.SuccessorOf.successorOf;

/**
 * The adjacency matrix of a graph, that tell the connection between two nodes
 */
public class Statistics {

    private final Graph graph;

    public Statistics(Graph subject) {
        this.graph = subject;
        shortestPaths = new ShortestPathMatrix(graph);
    }

    /**
     * @return the number of nodes in the graph
     */
    public int nodeCount() {
        return this.graph.nodes().size();
    }

    /**
     * @return the number of edges in the graph
     */
    public int edgeCount() {
        return this.graph.edges().size();
    }

    /**
     * @return the edge density of the graph (a.k.a. 'connectance').
     */
    public double density() {
        final double maximumEdgeCount = nodeCount() * (nodeCount() - 1);
        return edgeCount() / maximumEdgeCount;
    }

    /**
     * Compute the degree of a given node.
     *
     * @param node the node whose degree is needed
     * @param kind the type of degree (i.e., IN or OUT)
     * @return the degree of the given node
     */
    public int degreeOf(Node node, Degree kind) {
        switch (kind) {
            case IN:
                return this.graph.nodes(predecessorOf(node)).size();
            case OUT:
                return this.graph.nodes(successorOf(node)).size();
        }
        throw new IllegalArgumentException("Unknown degree type " + kind.name());
    }

    /**
     * @param kind the type of degree (incoming, outgoing, of both edges)
     * @return the average node degree of the graph
     */
    public double averageNodeDegree(Degree kind) {
        int total = 0;
        for (Node eachNode : graph.nodes()) {
            total += degreeOf(eachNode, kind);
        }
        return (double) total / nodeCount();
    }

    /**
     * Find the shortest path between a pair of node
     *
     * @param source the first node of the path
     * @param target the last node of the path
     * @return the shortest possible path
     */
    public Path shortestPathBetween(Node source, Node target) {
        return shortestPaths.between(source, target);
    }

    private final ShortestPathMatrix shortestPaths;

    /**
     * Return the distance between a pair of node
     *
     * @param source the first node of the path
     * @param target the last node
     * @return the number of edges between the two given nodes
     */
    public int distanceBetween(Node source, Node target) {
        return shortestPathBetween(source, target).length();
    }

    /**
     * Compute the eccentricity of the node, i.e., the greatest distance with
     * all other node.
     *
     * @param node the node whose eccentricity is needed
     * @return the eccentricity of the node
     */
    public int eccentricityOf(Node node) {
        return shortestPaths.eccentricityOf(node);
    }

    /**
     * @return the diameter of the graph, i.e., the longest shortest path
     * between all pairs of node
     */
    public int diameter() {
        return shortestPaths.diameter();
    }

}
