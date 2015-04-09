package eu.diversify.trio.graph.statistics;

import static eu.diversify.trio.graph.Node.node;
import eu.diversify.trio.graph.SampleGraphs;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import org.junit.Test;

/**
 * Specification of the adjacency matrix
 */
public class StatisticsTest {

    private final Statistics statistics;

    public StatisticsTest() {
        statistics = new Statistics(SampleGraphs.twoNodesRing());
    }

    @Test
    public void shouldProvideTheNodeCount() {
        assertThat(statistics.nodeCount(), is(equalTo(2)));
    }

    @Test
    public void shouldProvideTheEdgeCount() {
        assertThat(statistics.edgeCount(), is(equalTo(2)));
    }

    @Test
    public void shouldProvideTheDensity() {
        double expectation = 2D / (2D * 1D);
        assertThat(statistics.density(), is(closeTo(expectation, ERROR_TOLERANCE)));
    }
   

    @Test
    public void shouldProvideTheInDegreeOfNodes() {
        assertThat(statistics.degreeOf(node(0), Degree.IN), is(equalTo(1)));
        assertThat(statistics.degreeOf(node(1), Degree.IN), is(equalTo(1)));
    }

    @Test
    public void shouldProvideTheOutDegreeOfNodes() {
        assertThat(statistics.degreeOf(node(0), Degree.OUT), is(equalTo(1)));
        assertThat(statistics.degreeOf(node(1), Degree.OUT), is(equalTo(1)));
    }
    
    @Test
    public void shouldProvideAverageNodeDegree() {
        assertThat(statistics.averageNodeDegree(Degree.IN), is(equalTo(1D)));
    }
    
    private static final double ERROR_TOLERANCE = 1e-6;

}
