
package eu.diversify.trio.graph.generator;

/**
 * A factory class that ease the creation of generators
 */
public class GeneratorFactory {
    
    public static GeneratorFactory generators() {
        return new GeneratorFactory();
    }
    
    /**
     * @return a new instance of erdosRenyi generator
     * @param nodeCount the number of node in the graphs to be generated
     * @param edgeProbability the probability of generating an edge
     */
    public GraphGenerator erdosRenyi(int nodeCount, double edgeProbability) {
        return new GraphGenerator(new ErdosRenyiModel(nodeCount, edgeProbability));
    }
    
    /**
     * @return a new regular ring lattice generator
     * @param nodeCount the number of node the graphs to be generated
     * @param neighborhoodSize the number of node to include in the neighborhood
     */
    public GraphGenerator regularRindLattice(int nodeCount, int neighborhoodSize) {
        return new GraphGenerator(new RingLatticeModel(nodeCount, neighborhoodSize));
    }
    
    
}
