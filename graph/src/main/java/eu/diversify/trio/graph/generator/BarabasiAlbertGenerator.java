package eu.diversify.trio.graph.generator;

import eu.diversify.trio.graph.Services;
import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.graph.model.Vertex;
import eu.diversify.trio.graph.statistics.VertexDegreeAnalysis;
import eu.diversify.trio.utility.Count;
import eu.diversify.trio.utility.Probability;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * The 'Barabasi and Albert' model, where in and out degree follow a power law
 * distribution.
 */
public class BarabasiAlbertGenerator implements GraphGenerator {

    private static final boolean TARGET = true;
    private static final boolean SOURCE = false;

    private final Random random;
    private final Count nodeCount;
    private final Probability alpha;
    private final Probability beta;

    public BarabasiAlbertGenerator(Count nodeCount, Probability alpha, Probability beta) {
        this.random = new Random();
        this.nodeCount = nodeCount;
        this.alpha = alpha;
        this.beta = beta;
    }

    @Override
    public Graph nextGraph() {
        final Graph graph = Services.registry().factory().meshedGraph(new Count(INITIAL_SIZE));
        final VertexDegreeAnalysis degrees = new VertexDegreeAnalysis(graph);

        while (graph.vertexCount() < nodeCount()) {
            final Action action = selectAction();
            switch (action) {
                case NEW_NODE_WITH_OUTGOING_EDGE: {
                    Vertex destination = aCommonlyUsed(graph, degrees, TARGET);
                    Vertex newVertex = graph.createVertex();
                    graph.connect(newVertex, destination);
                    break;
                }
                case NEW_NODE_WITH_INCOMING_EDGE: {
                    Vertex source = aCommonlyUsed(graph, degrees, SOURCE);
                    Vertex newVertex = graph.createVertex();
                    graph.connect(source, newVertex);
                    break;
                }
                case NEW_EDGE: {
                    Vertex source = aCommonlyUsed(graph, degrees, SOURCE);
                    Vertex destination = aCommonlyUsed(graph, degrees, TARGET);
                    if (!graph.hasEdge(source, destination)) {
                        graph.connect(source, destination);
                    }
                    break;
                }
            }
        }
        return graph;
    }

    private static final int INITIAL_SIZE = 3;

    private int nodeCount() {
        return nodeCount.value();
    }

    private enum Action {

        NEW_EDGE,
        NEW_NODE_WITH_INCOMING_EDGE,
        NEW_NODE_WITH_OUTGOING_EDGE
    }

    private Action selectAction() {
        double draw = random.nextDouble();
        if (draw < alpha.value()) {
            return Action.NEW_NODE_WITH_OUTGOING_EDGE;
        } else if (draw < alpha.value() + beta.value()) {
            return Action.NEW_NODE_WITH_INCOMING_EDGE;
        } else {
            return Action.NEW_EDGE;
        }
    }

    private Vertex aCommonlyUsed(Graph graph, VertexDegreeAnalysis degrees, boolean role) {
        double draw = random.nextDouble();
        double acc = 0;
        for (Vertex eachVertex : graph.vertexes()) {
            acc += probabilityOf(degrees, eachVertex, role);
            if (acc > draw) {
                return eachVertex;
            }
        }

        final String description = String.format("Unable to select node (%d candidates, draw = %1.3e)", graph.vertexCount(), draw);
        throw new RuntimeException(description);
    }

    private static double probabilityOf(VertexDegreeAnalysis degrees, Vertex eachVertex, boolean role) {
        if (role) {
            assert degrees.totalInDegree() > 0D : "Invalid total in-degree";
            return (double) degrees.inDegreeOf(eachVertex) / degrees.totalInDegree();
        }

        assert degrees.totalOutDegree() > 0D : "Invalid total out-degree";
        return (double) degrees.outDegreeOf(eachVertex) / degrees.totalOutDegree();
    }

}
