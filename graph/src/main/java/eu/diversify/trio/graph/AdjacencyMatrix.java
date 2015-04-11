package eu.diversify.trio.graph;

import java.util.BitSet;

/**
 * A graph, encoded as its adjacency matrix
 */
public class AdjacencyMatrix implements Graph {

    private final int nodeCount;
    private final BitSet adjacency;

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
        for(int eachNode = 0 ; eachNode < nodeCount ; eachNode++) {
            nodes.add(new Node(eachNode));
        }
        return nodes;
    }

    @Override
    public EdgeSet edges() {
        final EdgeSet edges = new EdgeSet(adjacency.cardinality());
        for(int eachEdge=0 ; eachEdge<adjacency.length() ; eachEdge++) {
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

}
