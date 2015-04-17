package eu.diversify.trio.graph.statistics;

import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.graph.model.Vertex;

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
        return this.graph.vertexCount();
    }

    /**
     * @return the number of edges in the graph
     */
    public int edgeCount() {
        return this.graph.edgeCount();
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
     * @param vertex the node whose degree is needed
     * @param kind the type of degree (i.e., IN or OUT)
     * @return the degree of the given node
     */
    public int degreeOf(Vertex vertex, Degree kind) {
        switch (kind) {
            case IN:
                return vertex.incomingEdges().size();
            case OUT:
                return vertex.outgoingEdges().size();
        }
        throw new IllegalArgumentException("Unknown degree type " + kind.name());
    }
    
    public int degreeOf(int vertexId, Degree kind) {
        return degreeOf(graph.vertexWithId(vertexId), kind);
    }

    /**
     * @param kind the type of degree (incoming, outgoing, of both edges)
     * @return the average node degree of the graph
     */
    public double averageNodeDegree(Degree kind) {
        int total = 0;
        for (Vertex eachVertex : graph.vertexes()) {
            total += degreeOf(eachVertex, kind);
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
    public Path shortestPathBetween(Vertex source, Vertex target) {
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
    public int distanceBetween(Vertex source, Vertex target) {
        return shortestPathBetween(source, target).length();
    }

    /**
     * Compute the eccentricity of the node, i.e., the greatest distance with
     * all other node.
     *
     * @param vertex the node whose eccentricity is needed
     * @return the eccentricity of the node
     */
    public int eccentricityOf(Vertex vertex) {
        return shortestPaths.eccentricityOf(vertex);
    }

    /**
     * @return the diameter of the graph, i.e., the longest shortest path
     * between all pairs of node
     */
    public int diameter() {
        return shortestPaths.diameter();
    }

}
