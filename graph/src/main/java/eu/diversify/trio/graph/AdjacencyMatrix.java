package eu.diversify.trio.graph;

import eu.diversify.trio.graph.queries.EdgePredicate;
import eu.diversify.trio.graph.queries.NodePredicate;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * A graph, encoded as its adjacency matrix
 */
public class AdjacencyMatrix implements Graph {

    /**
     * Create an adjacency matrix from a string where edges are denote by '1',
     * (e.g., 000111)
     *
     * @param binary the string describing the adjacency matrix
     * @return the adjacency matrix
     */
    public static AdjacencyMatrix from(String binary) {
        final int edgeCount = binary.length();
        final int nodeCount = (int) Math.sqrt(edgeCount);
        final BitSet adjacency = new BitSet(edgeCount);
        for (int eachEdge = 0; eachEdge < edgeCount; eachEdge++) {
            char edge = binary.charAt(eachEdge);
            if (edge == '1') {
                adjacency.set(eachEdge);
            }
        }
        return new AdjacencyMatrix(nodeCount, adjacency);
    }

    public static Graph from(String string, String string0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private final int nodeCount;
    private final BitSet adjacency;

    public AdjacencyMatrix(int nodeCount) {
        this.nodeCount = nodeCount;
        this.adjacency = new BitSet(nodeCount * nodeCount);
    }

    public AdjacencyMatrix(int nodeCount, BitSet adjacency) {
        this.nodeCount = nodeCount;
        this.adjacency = (BitSet) adjacency.clone();
    }

    public AdjacencyMatrix(boolean[][] adjacency) {
        this.nodeCount = adjacency.length;
        this.adjacency = new BitSet(nodeCount * nodeCount);
        for (int eachSourceNode = 0; eachSourceNode < nodeCount; eachSourceNode++) {
            if (adjacency[eachSourceNode].length != nodeCount) {
                final String error
                        = String.format("The given adjacency matrix is not square (expected %d entries for node %d but %d found).",
                                eachSourceNode,
                                adjacency[eachSourceNode].length,
                                nodeCount
                        );
                throw new IllegalArgumentException(error);
            }
            for (int eachTargetNode = 0; eachTargetNode < nodeCount; eachTargetNode++) {
                final int edge = edgeIndex(eachSourceNode, eachTargetNode);
                this.adjacency.set(edge, adjacency[eachSourceNode][eachTargetNode]);
            }
        }
    }

    private int edgeIndex(int eachNode, int otherNode) {
        return eachNode * nodeCount + otherNode;
    }

    @Override
    public List<Node> nodes() {
        computeNodesIfNeeded();
        return nodes;
    }

    private void computeNodesIfNeeded() {
        if (nodes == null) {
            nodes = new ArrayList<>(nodeCount);
            for (int eachNode = 0; eachNode < nodeCount; eachNode++) {
                nodes.add(new Node(eachNode));
            }
        }
    }
    
    private List<Node> nodes;

    @Override
    public List<Node> nodes(NodePredicate predicate) {
        final List<Node> selection = new ArrayList<>(nodeCount);
        for(Node anyNode: nodes()) {
            if (predicate.isSatisfiedBy(this, anyNode)) {
                selection.add(anyNode);
            }
        }
        return selection;
    }

    
    
    
    @Override
    public List<Edge> edges() {
        computeEdgesIfNeeded();
        return edges;
    }

    @Override
    public List<Edge> edges(EdgePredicate predicate) {
        final List<Edge> selection = new ArrayList<Edge>(adjacency.cardinality());
        for (Edge anyEdge: edges()) {
            if (predicate.isSatisfiedBy(anyEdge)) {
                selection.add(anyEdge);
            }
        }
        return selection;
    }
    
    

    private void computeEdgesIfNeeded() {
        if (edges == null) {
            edges = new ArrayList<>(adjacency.cardinality());
            for (int eachEdge = 0; eachEdge < adjacency.length(); eachEdge++) {
                if (adjacency.get(eachEdge)) {
                    final Node source = new Node(eachEdge / nodeCount);
                    final Node target = new Node(eachEdge % nodeCount);
                    edges.add(new Edge(source, target));
                }
            }
        }
    }

    private List<Edge> edges;

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        for (int eachSourceNode = 0; eachSourceNode < nodeCount; eachSourceNode++) {
            for (int eachTargetNode = 0; eachTargetNode < nodeCount; eachTargetNode++) {
                char sign = '0';
                if (adjacency.get(edgeIndex(eachSourceNode, eachTargetNode))) {
                    sign = '1';
                }
                buffer.append(sign);
            }
            buffer.append(System.lineSeparator());
        }
        return buffer.toString();
    }

    @Override
    public Edge connect(Node source, Node target) {
        adjacency.set(edgeIndex(source.index(), target.index()));
        final Edge newEdge = new Edge(source, target);
        computeEdgesIfNeeded();
        edges.add(newEdge);
        return newEdge;
    }

    @Override
    public void disconnect(Edge edge) {
        adjacency.set(edgeIndex(edge.source().index(), edge.target().index()), false);
        computeEdgesIfNeeded();
        edges.remove(edge);
    }

}
