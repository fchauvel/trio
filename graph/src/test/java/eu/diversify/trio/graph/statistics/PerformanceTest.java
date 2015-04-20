package eu.diversify.trio.graph.statistics;

import eu.diversify.trio.graph.generator.ErdosRenyiGenerator;
import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.utility.Count;
import eu.diversify.trio.utility.Probability;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Check the time spent calculating the statistics
 */
@Ignore
public class PerformanceTest {

    @Test
    public void testDiameterDuration() {
        final int RUN_COUNT = 100;
        final Count VERTEX_COUNT = new Count(50);
        final Probability EDGE_PROBABILITY = new Probability(0.5);
        final ErdosRenyiGenerator generator = new ErdosRenyiGenerator(VERTEX_COUNT, EDGE_PROBABILITY);
 
        long total = 0;
        for (int i = 0; i < RUN_COUNT; i++) {
            Graph graph = generator.nextGraph();
            long start = System.currentTimeMillis();
            Statistics stats = new Statistics(graph);
            double diameter = stats.diameter();
            long end = System.currentTimeMillis();
            total += end - start;
        }

        System.out.printf("Duration: %.2f ms (%d runs with %d vertexes and P[edge]=%.3f)%n", (double) total / RUN_COUNT, RUN_COUNT, VERTEX_COUNT.value(), EDGE_PROBABILITY.value());
    }
}
