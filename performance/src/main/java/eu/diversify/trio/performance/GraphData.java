package eu.diversify.trio.performance;

import eu.diversify.trio.graph.model.Graph;

/**
 * Wrapper for graph, which permits to attach some meta-data
 */
public class GraphData {
    
    private final int id;
    private final String sourceFile;
    private final Graph graph;

    public GraphData(int id, String sourceFile, Graph graph) {
        this.id = id;
        this.sourceFile = sourceFile;
        this.graph = graph;
    }

    public int id() {
        return id;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public Graph graph() {
        return graph;
    }
    
    
}
