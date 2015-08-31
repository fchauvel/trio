package eu.diversify.trio.graph.storage.csv;

import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.graph.model.Vertex;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Read a graph from a CSV file containing the list of edges
 */
public class CsvReader {

    public Graph read(InputStream in) throws IOException {
        final Graph graph = new Graph();

        Map<Integer, Vertex> vertexes = new HashMap<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String lineOfText = readFirstLine(reader);
        while (lineOfText != null) {
            String[] parts = split(lineOfText);
            int sourceId = recordVertexId(parts[SOURCE], vertexes, graph);
            int destinationId = recordVertexId(parts[DESTINATION], vertexes, graph);
            graph.connect(vertexes.get(sourceId), vertexes.get(destinationId));
            lineOfText = reader.readLine();
        }

        return graph;
    }

    private String readFirstLine(BufferedReader reader) throws IOException {
        String lineOfText = reader.readLine();
        if (isHeaderLine(lineOfText)) {
            lineOfText = reader.readLine();
        }
        return lineOfText;
    }

    private boolean isHeaderLine(String lineOfText) {
        return lineOfText != null && !lineOfText.matches("\\d+,\\d+");
    }

    private String[] split(String lineOfText) throws IllegalArgumentException {
        String[] parts = lineOfText.split(",");
        if (parts.length > 2) {
            final String description = String.format("Too many fields (expected 2, but %d found)", parts.length);
            throw new IllegalArgumentException(description);
        }
        return parts;
    }

    private static int recordVertexId(String part, Map<Integer, Vertex> vertexes, Graph graph) throws NumberFormatException {
        try {
            final int id = Integer.parseInt(part);
            if (!vertexes.containsKey(id)) {
                Vertex vertex = graph.createVertex();
                vertexes.put(id, vertex);
            }
            return id;
        } 
        catch (NumberFormatException nfe) {
            final String description = String.format("Invalid vertex id (expected integer value, but found '%s')", part);
            throw new IllegalArgumentException(description);
        
        }
    }
    
    private static final int DESTINATION = 1;
    private static final int SOURCE = 0;
}
