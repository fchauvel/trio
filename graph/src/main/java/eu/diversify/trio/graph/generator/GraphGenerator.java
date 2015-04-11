package eu.diversify.trio.graph.generator;

import eu.diversify.trio.graph.Graph;

/**
 * Graph Generator
 */
public interface GraphGenerator {

    /**
     * @return a new graph following the configuration of this generator
     */
    Graph nextGraph();

}
