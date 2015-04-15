package eu.diversify.trio.graph.generator;

import eu.diversify.trio.graph.AdjacencyMatrix;
import eu.diversify.trio.graph.Graph;
import eu.diversify.trio.graph.Node;
import eu.diversify.trio.graph.util.Count;
import eu.diversify.trio.graph.util.Probability;
import java.util.Random;

/**
 * The Erdos-Renyi G(n, p) generator of random graphs
 */
public class ErdosRenyiGenerator  implements GraphGenerator {

    private final Random random;
    private final Count graphSize;
    private final Probability edgeCreation;
    
    public ErdosRenyiGenerator(Count graphSize, Probability edgeCreation) {
        this(new Random(), graphSize, edgeCreation);
    }

    public ErdosRenyiGenerator(Random random, Count graphSize, Probability edgeCreation) {
        this.random = random;
        this.graphSize = graphSize;
        this.edgeCreation = edgeCreation;
    }

    @Override
     public Graph nextGraph() {
        final AdjacencyMatrix graph = new AdjacencyMatrix(graphSize.value());
        for (Node eachSource: graph.nodes()) {
            for (Node eachTarget: graph.nodes()) {
                if (edgeCreated()) {
                    graph.connect(eachSource, eachTarget);
                }
            }
        }
        return graph;
    }

    public boolean edgeCreated() {
        double draw = random.nextDouble();
        return (draw < edgeCreation.value());
    }


}
