package eu.diversify.trio.graph.statistics;

import eu.diversify.trio.graph.Graph;
import eu.diversify.trio.graph.Node;
import static eu.diversify.trio.graph.Node.node;
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
 * Diameter specification by examples
 */
@RunWith(Parameterized.class)
public class DiameterTest {

    private final String name;
    private final Graph graph;
    private final int expectedDiameter;

    public DiameterTest(String name, Graph graph, int diameter) {
        this.name = name;
        this.graph = graph;
        this.expectedDiameter = diameter;
    }

    @Test
    public void testDiameter() {
        Statistics stats = new Statistics(graph);
        assertThat(stats.diameter(), is(equalTo(expectedDiameter)));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testCases() {
        final List<Object[]> testCases = new ArrayList<>();

        testCases.add(new Object[]{
            "2 nodes ring, 0 -> 1",
            SampleGraphs.twoNodesRing(),
            1
        });

        testCases.add(new Object[]{
            "2 paths",
            SampleGraphs.twoPaths(),
            1
        });

        testCases.add(new Object[]{
            "loop in the middle",
            SampleGraphs.aLoopInTheMiddle(),
            3
        });

        return testCases;
    }

}
