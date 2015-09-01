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
package eu.diversify.trio.graph.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

/**
 * Graph Specification
 */
public class GraphTest {

    @Test
    public void shouldBeEmptyAtFirst() {
        Graph graph = new Graph();

        assertThat(graph.isEmpty(), is(true));
    }

    @Test
    public void shouldSupportCreatingNewDisconnectedVertexes() {
        Graph graph = new Graph();

        Vertex vertex = graph.createVertex();

        assertThat(graph.isEmpty(), is(true));
        assertThat(graph.vertexCount(), is(equalTo(1)));
        assertThat(vertex.isDisconnected(), is(true));
    }

    @Test
    public void shouldSupportConnectingVertexes() {
        Graph graph = new Graph();

        Vertex source = graph.createVertex();
        Vertex target = graph.createVertex();
        Edge edge = graph.connect(source, target);

        assertThat(edge, is(not(nullValue())));
        assertThat(graph.edgeCount(), is(equalTo(1)));
        assertThat(source.isDisconnected(), is(false));
        assertThat(target.isDisconnected(), is(false));
    }

    @Test
    public void shouldSupportRemovingEdges() {
        Graph graph = new Graph();
        Vertex source = graph.createVertex();
        Vertex target = graph.createVertex();
        Edge edge = graph.connect(source, target);

        graph.disconnect(edge);

        assertThat(graph.edgeCount(), is(equalTo(0)));
        assertThat(source.isDisconnected(), is(true));
        assertThat(target.isDisconnected(), is(true));
    }
    
    @Test
    public void shouldSupportRemovingVertex() {
        Graph graph = new Graph();
        Vertex source = graph.createVertex();
        Vertex target = graph.createVertex();
        Edge edge = graph.connect(source, target);

        graph.removeVertex(source);

        assertThat(graph.vertexCount(), is(equalTo(1)));
        assertThat(graph.edgeCount(), is(equalTo(0)));
        assertThat(target.isDisconnected(), is(true));
    }
}
