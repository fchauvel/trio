package eu.diversify.trio.graph.generator;

import eu.diversify.trio.graph.model.Graph;
import static eu.diversify.trio.graph.generator.GraphFactory.graphFactory;
import eu.diversify.trio.graph.model.Vertex;
import eu.diversify.trio.utility.Count;
import eu.diversify.trio.utility.Probability;
import java.util.HashMap;
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
        final Graph graph = graphFactory().meshedGraph(new Count(2));
        while (graph.vertexCount() < nodeCount()) {
            final Action action = selectAction();
            switch (action) {
                case NEW_NODE_WITH_OUTGOING_EDGE: {
                    Vertex newVertex = graph.createVertex();
                    Vertex destination = aCommonlyUsed(graph, TARGET);
                    graph.connect(newVertex, destination);
                    break;
                }
                case NEW_NODE_WITH_INCOMING_EDGE: {
                    Vertex source = aCommonlyUsed(graph, SOURCE);
                    Vertex newVertex = graph.createVertex();
                    graph.connect(source, newVertex);
                    break;
                }
                case NEW_EDGE: {
                    Vertex source = aCommonlyUsed(graph, SOURCE);
                    Vertex destination = aCommonlyUsed(graph, TARGET);
                    graph.connect(source, destination);
                    break;
                }
            }
        }
        return graph;
    }

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

    private Vertex aCommonlyUsed(Graph graph, boolean role) {
        Map<Vertex, Double> probabilities = computeProbabilities(graph, role);

        double draw = random.nextDouble();
        double acc = 0;
        for (Vertex eachVertex : graph.vertexes()) {
            acc += probabilities.get(eachVertex);
            if (acc > draw) {
                return eachVertex;
            }
        }
        throw new RuntimeException("Unable to select node");
    }

    private Map<Vertex, Double> computeProbabilities(Graph graph, boolean role) {
        final Map<Vertex, Double> probabilities = new HashMap<>(graph.vertexCount());
        double total = 0;
        for (Vertex eachVertex : graph.vertexes()) {
            final double degree = degree(eachVertex, role);
            probabilities.put(eachVertex, degree);
            total += degree;
        }
        for (Vertex eachNode : graph.vertexes()) {
            double probability = probabilities.get(eachNode) / total;
            probabilities.put(eachNode, probability);
        }
        return probabilities;
    }

    private static double degree(Vertex vertex, boolean role) {
        if (role) {
            return vertex.incomingEdges().size();
        }
        return vertex.outgoingEdges().size();
    }

}
