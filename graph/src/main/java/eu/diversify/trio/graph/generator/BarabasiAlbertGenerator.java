package eu.diversify.trio.graph.generator;

import eu.diversify.trio.graph.AdjacencyMatrix;
import eu.diversify.trio.graph.CachedGraph;
import eu.diversify.trio.graph.Graph;
import eu.diversify.trio.graph.Node;
import static eu.diversify.trio.graph.Node.node;
import static eu.diversify.trio.graph.queries.Leaving.leaving;
import static eu.diversify.trio.graph.queries.Reaching.reaching;
import eu.diversify.trio.utility.Count;
import eu.diversify.trio.utility.Probability;
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
        Graph graph = new CachedGraph(new AdjacencyMatrix(nodeCount()));
        graph.connect(node(0), node(1));
        graph.connect(node(1), node(0));

        int index = 2;
        while (index < nodeCount()) {
            final Action action = selectAction();
            switch (action) {
                case NEW_NODE_WITH_OUTGOING_EDGE:
                    graph.connect(node(index), aCommonlyUsed(graph, TARGET));
                    index++;
                    break;
                case NEW_NODE_WITH_INCOMING_EDGE:
                    graph.connect(aCommonlyUsed(graph, SOURCE), node(index));
                    index++;
                case NEW_EDGE:
                    graph.connect(aCommonlyUsed(graph, SOURCE), aCommonlyUsed(graph, TARGET));
                    break;
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
        } 
        else if (draw < alpha.value() + beta.value()) {
            return Action.NEW_NODE_WITH_INCOMING_EDGE;
        } 
        else {
            return Action.NEW_EDGE;
        }
    }

    private Node aCommonlyUsed(Graph graph, boolean role) {
        double probabilities[] = computeProbabilities(graph, role);

        double draw = random.nextDouble();
        double acc = 0;
        for (Node eachNode : graph.nodes()) {
            acc += probabilities[eachNode.index()];
            if (acc > draw) {
                return eachNode;
            }
        }
        throw new RuntimeException("Unable to select node");
    }

    private double[] computeProbabilities(Graph graph, boolean role) {
        double probabilities[] = new double[nodeCount()];
        double total = 0;
        for (Node eachNode : graph.nodes()) {
            probabilities[eachNode.index()] = degree(graph, eachNode, role);
            total += probabilities[eachNode.index()];
        }
        for (Node eachNode : graph.nodes()) {
            probabilities[eachNode.index()] /= total;
        }
        return probabilities;
    }

    private static int degree(Graph graph, Node node, boolean role) {
        if (role) {
            return graph.edges(reaching(node)).size();
        }
        return graph.edges(leaving(node)).size();
    }

}
