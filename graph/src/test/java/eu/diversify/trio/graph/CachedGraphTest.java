/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.diversify.trio.graph;

import static eu.diversify.trio.graph.Node.node;
import static eu.diversify.trio.graph.queries.Reaching.reaching;
import static eu.diversify.trio.graph.queries.SuccessorOf.successorOf;
import java.util.ArrayList;
import org.jmock.Expectations;
import static org.jmock.Expectations.returnValue;
import org.jmock.Mockery;
import org.junit.Test;

/**
 * Check that the cache does prevent similar request to be processed several
 * times.
 */
public class CachedGraphTest {

    private final Mockery context = new Mockery();

    @Test
    public void shallNotRunTwiceTheSameEdgeRequest() {

        final Graph mockup = context.mock(Graph.class);

        context.checking(new Expectations() {
            {
                oneOf(mockup).edges(with(reaching(node(2))));
                will(returnValue(new ArrayList<Edge>()));
            }
        });

        Graph graph = new CachedGraph(mockup);
        graph.edges(reaching(node(2)));
        graph.edges(reaching(node(2)));

        context.assertIsSatisfied();
    }

    @Test
    public void shallNotRunTwiceTheSameNodeRequest() {
        final Graph mockup = context.mock(Graph.class);

        context.checking(new Expectations() {
            {
                oneOf(mockup).nodes(with(successorOf(node(2))));
                will(returnValue(new ArrayList<Node>()));
            }
        });

        Graph graph = new CachedGraph(mockup);
        graph.nodes(successorOf(node(2)));
        graph.nodes(successorOf(node(2)));

        context.assertIsSatisfied();
    }

}
