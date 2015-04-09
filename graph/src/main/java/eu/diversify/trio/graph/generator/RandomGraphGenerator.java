package eu.diversify.trio.graph.generator;

import eu.diversify.trio.graph.Graph;

/**
 * Generate random graph with specific topological properties (e.g., degree
 * distribution, distance)
 */
public interface RandomGraphGenerator {

    /**
     * @return a new random graph derived according to the configuration of this
     * generator
     */
    Graph nextRandomGraph();

}
