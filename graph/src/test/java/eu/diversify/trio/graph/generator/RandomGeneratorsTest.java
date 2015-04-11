package eu.diversify.trio.graph.generator;

import eu.diversify.trio.graph.Graph;
import static eu.diversify.trio.graph.generator.GeneratorFactory.generators;
import eu.diversify.trio.graph.storage.dot.DotWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

/**
 * Specification of the ring lattice generator
 */
public class RandomGeneratorsTest {

    @Test
    public void sampleOneRandomRegularLattice() throws UnsupportedEncodingException, FileNotFoundException, IOException {
        final String dotFile = "target/regular_lattice.dot";
        final int NODE_COUNT = 25;
        final int NEIGHBORHOOD = 6;

        GraphGenerator generator = generators().regularRindLattice(NODE_COUNT, NEIGHBORHOOD);

        Graph graph = generator.nextRandomGraph();

        assertThat(graph.nodes().size(), is(equalTo(NODE_COUNT)));

        saveAsDot(dotFile, graph);
    }

    @Test
    public void oneRandomErdosRenyi() throws UnsupportedEncodingException, FileNotFoundException, IOException {
        final String dotFile = "target/random_erdos_renyi.dot";
        final int NODE_COUNT = 25;
        final double EDGE_PROBABILITY = 0.3;

        GraphGenerator generator = generators().erdosRenyi(NODE_COUNT, EDGE_PROBABILITY);
        Graph graph = generator.nextRandomGraph();

        assertThat(graph.nodes().size(), is(equalTo(NODE_COUNT)));

        saveAsDot(dotFile, graph);
    }

    private void saveAsDot(final String dotFile, Graph graph) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        DotWriter dot = new DotWriter();
        final FileOutputStream output = new FileOutputStream(dotFile);
        dot.write(graph, output);
        output.close();
    }

}
