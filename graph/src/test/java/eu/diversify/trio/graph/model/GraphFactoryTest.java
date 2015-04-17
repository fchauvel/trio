package eu.diversify.trio.graph.model;

import eu.diversify.trio.graph.generator.GraphFactory;
import eu.diversify.trio.utility.Count;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

/**
 * Specification of the graph factory
 */
public class GraphFactoryTest {

    private final GraphFactory createGraph;

    public GraphFactoryTest() {
        this.createGraph = new GraphFactory();
    }

    @Test
    public void shouldCreateGraphFromAjacencyMatrix() {
        Graph graph = createGraph.fromAdjacencyMatrix(new Count(3), "010100110");

        assertThat(graph.vertexCount(), is(equalTo(3)));
        assertThat(graph.edgeCount(), is(equalTo(4)));

        Vertex v0 = graph.vertexWithId(0);
        Vertex v1 = graph.vertexWithId(1);
        Vertex v2 = graph.vertexWithId(2);

        assertThat(graph.hasEdge(v0, v0), is(false));
        assertThat(graph.hasEdge(v0, v1), is(true));
        assertThat(graph.hasEdge(v0, v2), is(false));
        assertThat(graph.hasEdge(v1, v0), is(true));
        assertThat(graph.hasEdge(v1, v1), is(false));
        assertThat(graph.hasEdge(v1, v2), is(false));
        assertThat(graph.hasEdge(v2, v0), is(true));
        assertThat(graph.hasEdge(v2, v1), is(true));
        assertThat(graph.hasEdge(v2, v2), is(false));
    }

}
