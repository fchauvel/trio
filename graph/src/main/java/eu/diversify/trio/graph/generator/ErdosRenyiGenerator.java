package eu.diversify.trio.graph.generator;

import static eu.diversify.trio.graph.generator.GraphFactory.graphFactory;
import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.graph.model.Vertex;
import eu.diversify.trio.utility.Count;
import eu.diversify.trio.utility.Probability;
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
        final Graph graph = graphFactory().emptyGraph(graphSize); 
        for (Vertex eachSource: graph.vertexes()) {
            for (Vertex eachTarget: graph.vertexes()) {
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
