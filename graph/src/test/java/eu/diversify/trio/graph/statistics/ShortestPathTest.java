
package eu.diversify.trio.graph.statistics;

import eu.diversify.trio.graph.Graph;
import eu.diversify.trio.graph.Node;
import static eu.diversify.trio.graph.Node.node;
import eu.diversify.trio.graph.Path;
import eu.diversify.trio.graph.SampleGraphs;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Test shortest path calculation
 */
@RunWith(Parameterized.class)
public class ShortestPathTest {
    
    private final String name;
    private final Graph graph;
    private final Node source;
    private final Node target;
    private final Path shortest;

    public ShortestPathTest(String name, Graph graph, Node source, Node target, Path shortest) {
        this.name = name;
        this.graph = graph;
        this.source = source;
        this.target = target;
        this.shortest = shortest;
    }
   
    @Test
    public void shouldFindTheShortestPath() {
        Statistics stats = new Statistics(graph);
        assertThat(stats.shortestPathBetween(source, target), is(equalTo(shortest)));
    }
    
    @Parameterized.Parameters
    public static Collection<Object[]> testCases() {
        final List<Object[]> testCases = new ArrayList<>();
        
        testCases.add(new Object[]{
            "2 nodes ring, 0 -> 1", 
            SampleGraphs.twoNodesRing(), 
            node(0), node(1), 
            new Path(node(0), node(1))});
        
        testCases.add(new Object[]{
           "2 paths",
            SampleGraphs.twoPaths(),
            node(0), node(1),
            new Path(node(0), node(1))
        });
        
        testCases.add(new Object[]{
           "loop in the middle",
            SampleGraphs.aLoopInTheMiddle(),
            node(0), node(3),
            new Path(node(0), node(1), node(2), node(3))
        });
        
        return testCases;
    }
    
}
