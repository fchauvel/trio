/**
 * This file is part of TRIO :: Graph.
 *
 * TRIO :: Graph is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Graph is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Graph.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * This file is part of TRIO.
 *
 * TRIO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.diversify.trio.graph.storage.dot;

import eu.diversify.trio.graph.model.Edge;
import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.graph.model.Vertex;
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
     * 
     * @throws java.io.UnsupportedEncodingException
     */
    public void write(Graph graph, OutputStream output) throws UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(output, DEFAULT_ENCODING)));
        writer.printf("digraph %s {%n", DEFAULT_GRAPH_NAME);
        for(Vertex eachVertex: graph.vertexes()) {
            writer.printf("\tn%d;%n", eachVertex.id());
        }
        for(Edge eachEdge: graph.edges()) {
            writer.printf("\tn%d -> n%d;%n", eachEdge.source().id(), eachEdge.destination().id());
        }
        writer.printf("}%n");
        writer.flush();
    }
    private static final String DEFAULT_GRAPH_NAME = "XXX";
    
    private static final String DEFAULT_ENCODING = "UTF-8";
    
}
