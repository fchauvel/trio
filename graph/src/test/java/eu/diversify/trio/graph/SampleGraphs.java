package eu.diversify.trio.graph;

import static eu.diversify.trio.graph.GraphFactory.graphFactory;
import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.graph.util.Count;

/**
 * Defines various graphs for testing purpose
 */
public class SampleGraphs {

    public static Graph twoNodesRing() {
        String adjacency
                = "01"
                + "10";
        return graphFactory().fromAdjacencyMatrix(new Count(2), adjacency);
    }

    public static Graph twoPaths() {
        String adjacency
                = "011"
                + "000"
                + "010";
        return graphFactory().fromAdjacencyMatrix(new Count(3), adjacency);
    }

    public static Graph aLoopInTheMiddle() {
        String adjacency
                = "0100"
                + "0010"
                + "0101"
                + "0000";
        return graphFactory().fromAdjacencyMatrix(new Count(4), adjacency);
    }
}
