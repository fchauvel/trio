package eu.diversify.trio.graph;

import static eu.diversify.trio.graph.Node.node;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Specification of the adjacency matrix
 */
@RunWith(JUnit4.class)
public class AdjacencyMatrixTest {

    @Test
    public void matrixBuildFromBitSetShouldHaveACorrectEdges() {
        String adjacency 
                = "010"
                + "101"
                + "010";
        
        AdjacencyMatrix graph = createMatrix(adjacency);
        
        assertThat(graph.edges().size(), is(equalTo(4)));
        assertThat(graph.edges(), hasItem(new Edge(node(0), node(1))));
        assertThat(graph.edges(), hasItem(new Edge(node(1), node(0))));
        assertThat(graph.edges(), hasItem(new Edge(node(1), node(2))));
        assertThat(graph.edges(), hasItem(new Edge(node(2), node(1))));
    }

    private AdjacencyMatrix createMatrix(String text) throws IllegalArgumentException {
        return AdjacencyMatrix.from(text);
    }

    @Test
    public void shouldHaveNodeEdgeAtFirst() {
        AdjacencyMatrix graph = new AdjacencyMatrix(10);

        assertThat(graph.edges(), is(empty()));
    }

    @Test
    public void connectShouldCreateAnEdge() {
        AdjacencyMatrix graph = new AdjacencyMatrix(10);
        graph.connect(node(1), node(2));

        assertThat(graph.edges(), hasItem(new Edge(node(1), node(2))));
    }

    @Test
    public void disconnectShouldDeleteAnEdge() {
        AdjacencyMatrix graph = createMatrix("11"+"11");
        graph.disconnect(node(1), node(2));

        assertThat(graph.edges(), not(hasItem(new Edge(node(1), node(2)))));
    }

    @Test
    public void matrixBuildFromBitSetShouldHaveACorrectNodeCount() {
        AdjacencyMatrix graph = createMatrix("111"+"111"+"111");
        assertThat(graph.nodes().size(), is(equalTo(3)));
    }

}
