
package eu.diversify.trio.graph.storage.csv;

import eu.diversify.trio.graph.model.Edge;
import eu.diversify.trio.graph.model.Graph;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Store the incidence matrix of the given graph as a CSV table.
 */
public class CsvWriter {
  
    public void save(Graph graph, OutputStream output) throws UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(output, "UTF-8")));
        writer.printf("source,target%n");
        for(Edge eachEdge: graph.edges()) {
            writer.printf("n%d,n%d%n", eachEdge.source().id(), eachEdge.destination().id());
        }
        writer.flush();
    }
}
