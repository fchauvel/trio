
package eu.diversify.trio.graph.statistics;


import eu.diversify.trio.graph.SampleGraphs;
import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.graph.model.Vertex;
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
    private final int sourceId;
    private final int targetId;
    private final Path shortest;

    public ShortestPathTest(String name, Graph graph, int source, int target, Path shortest) {
        this.name = name;
        this.graph = graph;
        this.sourceId = source;
        this.targetId = target;
        this.shortest = shortest;
    }
   
    @Test
    public void shouldFindTheShortestPath() {
        Statistics stats = new Statistics(graph);
        Vertex source = graph.vertexWithId(sourceId);
        Vertex target = graph.vertexWithId(targetId);
        assertThat(stats.shortestPathBetween(source, target), is(equalTo(shortest)));
    }
    
    @Parameterized.Parameters
    public static Collection<Object[]> testCases() {
        final List<Object[]> testCases = new ArrayList<>();
        
        testCases.add(new Object[]{
            "2 nodes ring, 0 -> 1", 
            SampleGraphs.twoNodesRing(), 
            0, 1, 
            new Path(new Long[]{0L})});
        
        testCases.add(new Object[]{
           "2 paths",
            SampleGraphs.twoPaths(),
            0, 1,
            new Path(new Long[]{0L})
        });
        
        testCases.add(new Object[]{
           "loop in the middle",
            SampleGraphs.aLoopInTheMiddle(),
            0, 3,
            new Path(new Long[]{0L, 1L, 3L})
        });
        
        return testCases;
    }
    
}
