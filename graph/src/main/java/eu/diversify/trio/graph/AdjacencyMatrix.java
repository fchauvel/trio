package eu.diversify.trio.graph;

import java.util.BitSet;

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
    public NodeSet nodes() {
        final NodeSet nodes = new NodeSet(nodeCount);
        for (int eachNode = 0; eachNode < nodeCount; eachNode++) {
            nodes.add(new Node(eachNode));
        }
        return nodes;
    }

    @Override
    public EdgeSet edges() {
        final EdgeSet edges = new EdgeSet(adjacency.cardinality());
        for (int eachEdge = 0; eachEdge < adjacency.length(); eachEdge++) {
            if (adjacency.get(eachEdge)) {
                final Node source = new Node(eachEdge / nodeCount);
                final Node target = new Node(eachEdge % nodeCount);
                edges.add(new Edge(source, target));
            }
        }
        return edges;
    }

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
        return new Edge(source, target);
    }

    @Override
    public void disconnect(Edge edge) {
        adjacency.set(edgeIndex(edge.source().index(), edge.target().index()), false);
    }

}
