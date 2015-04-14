package eu.diversify.trio.graph.generator.barabasi;

import eu.diversify.trio.graph.AdjacencyMatrix;
import eu.diversify.trio.graph.CachedGraph;
import eu.diversify.trio.graph.Graph;
import eu.diversify.trio.graph.Node;
import eu.diversify.trio.graph.generator.GraphGenerator;
import static eu.diversify.trio.graph.Node.node;
import static eu.diversify.trio.graph.queries.Leaving.leaving;
import static eu.diversify.trio.graph.queries.Reaching.reaching;
import java.util.Random;

/**
 * The Barabasi and Albert model, where in and out degree follow a power law
 * distribution.
 */
public class BAGenerator implements GraphGenerator {

    public static final double DEFAULT_BETA = 1D / 3;
    public static final double DEFAULT_ALPHA = 1D / 3;

    private final Random random;
    private final int nodeCount;
    private final double alpha;
    private final double beta;

    
    public BAGenerator(int nodeCount) {
      this(nodeCount, DEFAULT_ALPHA, DEFAULT_BETA);
    }
    
    public BAGenerator(int nodeCount, double alpha, double beta) {
        this.random = new Random();
        this.nodeCount = nodeCount;
        this.alpha = alpha;
        this.beta = beta;
    }

    @Override
    public Graph nextGraph() {
        Graph graph = new CachedGraph(new AdjacencyMatrix(nodeCount));
        graph.connect(node(0), node(1));
        graph.connect(node(1), node(0));

        int index = 2;
        while (index < nodeCount) {
            double draw = random.nextDouble();
            if (draw < alpha) {
                graph.connect(node(index), select(graph, IN_DEGREE));
                index++;
            
            } else if (draw < alpha + beta) {
                graph.connect(select(graph, OUT_DEGREE), node(index));
                index++;
            
            } else {
                graph.connect(select(graph, OUT_DEGREE), select(graph, IN_DEGREE));
            
            }
        }
        return graph;
    }

    private static final boolean IN_DEGREE = true;
    private static final boolean OUT_DEGREE = false;

    private Node select(Graph graph, boolean degree) {
        double probabilities[] = computeProbabilities(graph, degree);

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

    private double[] computeProbabilities(Graph graph, boolean degree) {
        double probabilities[] = new double[nodeCount];
        double total = 0;
        for (Node eachNode : graph.nodes()) {
            probabilities[eachNode.index()] = degree(graph, eachNode, degree);
            total += probabilities[eachNode.index()];
        }
        for (Node eachNode : graph.nodes()) {
            probabilities[eachNode.index()] /= total;
        }
        return probabilities;
    }

    
    private static int degree(Graph graph, Node eachNode, boolean degree) {
        if (degree) {
            //return graph.nodes(predecessorOf(eachNode)).size();
            return graph.edges(reaching(eachNode)).size();
        }
//        return graph.nodes(successorOf(eachNode)).size();
        return graph.edges(leaving(eachNode)).size();
    }

}