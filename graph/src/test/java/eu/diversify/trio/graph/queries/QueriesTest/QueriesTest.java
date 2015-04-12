package eu.diversify.trio.graph.queries.QueriesTest;

import eu.diversify.trio.graph.AdjacencyMatrix;
import eu.diversify.trio.graph.Edge;
import eu.diversify.trio.graph.Graph;
import eu.diversify.trio.graph.Node;
import static eu.diversify.trio.graph.Node.node;
import static eu.diversify.trio.graph.queries.Leaving.leaving;
import static eu.diversify.trio.graph.queries.PredecessorOf.predecessorOf;
import static eu.diversify.trio.graph.queries.Reaching.reaching;
import static eu.diversify.trio.graph.queries.SuccessorOf.successorOf;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

/**
 * Specification of the queries on nodes and edges
 */
public class QueriesTest {

    @Test
    public void successorsShouldBeCorrect() {
        Graph graph = AdjacencyMatrix.from("001" + "010" + "000");
        final List<Node> successors = graph.nodes(successorOf(node(0)));

        assertThat(successors.size(), is(equalTo(1)));
        assertThat(successors, hasItem(node(2)));
    }

    @Test
    public void predecessorsShouldBeCorrect() {
        Graph graph = AdjacencyMatrix.from("001" + "010" + "000");
        final List<Node> predecessors = graph.nodes(predecessorOf(node(2)));

        assertThat(predecessors.size(), is(equalTo(1)));
        assertThat(predecessors, hasItem(node(0)));
    }

    @Test
    public void reachingShouldBeCorrect() {
        Graph graph = AdjacencyMatrix.from("001" + "010" + "000");
        final List<Edge> outgoing = graph.edges(reaching(node(2)));

        assertThat(outgoing.size(), is(equalTo(1)));
        assertThat(outgoing, hasItem(new Edge(node(0), node(2))));
    }

    @Test
    public void leavingShouldBeCorrect() {
        Graph graph = AdjacencyMatrix.from("001" + "010" + "000");
        final List<Edge> outgoing = graph.edges(leaving(node(0))); 

        assertThat(outgoing.size(), is(equalTo(1)));
        assertThat(outgoing, hasItem(new Edge(node(0), node(2))));
    }

}
