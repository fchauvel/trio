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
 * Specification of the vertex eccentricity by example
 */
@RunWith(Parameterized.class) 
public class EccentricityTest {

    private final String name;
    private final Graph graph;
    private final int vertexId;
    private final int expectedEccentricity;

    public EccentricityTest(String name, Graph graph, int node, int eccentricity) {
        this.name = name;
        this.graph = graph;
        this.vertexId = node;
        this.expectedEccentricity = eccentricity;
    }

    @Test
    public void testEccentricity() {
        Statistics stats = new Statistics(graph);
        Vertex vertex = graph.vertexWithId(vertexId);
        assertThat(stats.eccentricityOf(vertex), is(equalTo(expectedEccentricity)));
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> testCases() {
        final List<Object[]> testCases = new ArrayList<>();

        testCases.add(new Object[]{
            "2 nodes ring / 0 -> 1",
            SampleGraphs.twoNodesRing(),
            0,
            1
        });

        testCases.add(new Object[]{
            "2 paths",
            SampleGraphs.twoPaths(),
            0,
            1
        });

        testCases.add(new Object[]{
            "loop in the middle",
            SampleGraphs.aLoopInTheMiddle(),
            0,
            3
        });

        return testCases;
    }
}
