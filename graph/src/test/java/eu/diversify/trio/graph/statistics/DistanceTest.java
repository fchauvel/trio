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
 * Specification of the distance between two nodes by examples
 */
@RunWith(Parameterized.class)
public class DistanceTest {

    private final String name;
    private final Graph graph;
    private final int sourceId;
    private final int targetId;
    private final int expectedDistance;

    public DistanceTest(String name, Graph graph, int source, int target, int distance) {
        this.name = name;
        this.graph = graph;
        this.sourceId = source;
        this.targetId = target;
        this.expectedDistance = distance;
    }

    @Test
    public void testDistance() {
        Statistics stats = new Statistics(graph);
        Vertex source = graph.vertexWithId(sourceId);
        Vertex target = graph.vertexWithId(targetId);
        assertThat(stats.distanceBetween(source, target), is(equalTo(expectedDistance)));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testCases() {
        final List<Object[]> testCases = new ArrayList<>();

        testCases.add(new Object[]{
            "2 nodes ring, 0 -> 1",
            SampleGraphs.twoNodesRing(),
            0, 1,
            1});

        testCases.add(new Object[]{
            "2 paths",
            SampleGraphs.twoPaths(),
            0, 1,
            1
        });

        testCases.add(new Object[]{
            "loop in the middle",
            SampleGraphs.aLoopInTheMiddle(),
            0, 3,
            3
        });

        return testCases;
    }

}
