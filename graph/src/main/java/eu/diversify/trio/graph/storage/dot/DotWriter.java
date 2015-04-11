
package eu.diversify.trio.graph.storage.dot;

import eu.diversify.trio.graph.Edge;
import eu.diversify.trio.graph.Graph;
import eu.diversify.trio.graph.Node;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Serialize a given graph in the dot format
 */
public class DotWriter {

    /**
     * Convert a graph in DOT code
     * 
     * @param graph the graph that shall be converted in DOT
     * @param output the stream where the DOT code shall be written
     */
    public void write(Graph graph, OutputStream output) throws UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(output, DEFAULT_ENCODING)));
        writer.printf("digraph %s {%n", DEFAULT_GRAPH_NAME);
        for(Node eachNode: graph.nodes()) {
            writer.printf("\tn%d;%n", eachNode.index());
        }
        for(Edge eachEdge: graph.edges()) {
            writer.printf("\tn%d -> n%d;%n", eachEdge.source().index(), eachEdge.target().index());
        }
        writer.printf("}%n");
        writer.flush();
    }
    private static final String DEFAULT_GRAPH_NAME = "XXX";
    
    private static final String DEFAULT_ENCODING = "UTF-8";
    
}
