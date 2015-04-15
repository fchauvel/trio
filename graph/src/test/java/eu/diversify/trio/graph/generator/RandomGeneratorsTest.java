package eu.diversify.trio.graph.generator;

import eu.diversify.trio.graph.Edge;
import eu.diversify.trio.graph.Graph;
import static eu.diversify.trio.graph.Node.node;
import eu.diversify.trio.graph.storage.csv.CsvWriter;
import eu.diversify.trio.graph.storage.dot.DotWriter;
import eu.diversify.trio.graph.util.Count;
import eu.diversify.trio.graph.util.Probability;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

/**
 * Specification of the ring lattice generator
 */
public class RandomGeneratorsTest {

    @Test
    public void sampleOneRandomRegularLattice() throws UnsupportedEncodingException, FileNotFoundException, IOException {
        final String file = "target/regular_lattice";
        final Count NODE_COUNT = new Count(25);
        final Count NEIGHBORHOOD = new Count(6);

        GraphGenerator generator = new RingLatticeGenerator(NODE_COUNT, NEIGHBORHOOD);

        Graph graph = generator.nextGraph();

        assertThat(graph.nodes().size(), is(equalTo(NODE_COUNT.value())));

        save(file, graph);
    }

    @Test
    public void oneRandomErdosRenyi() throws UnsupportedEncodingException, FileNotFoundException, IOException {
        final String file = "target/random_erdos_renyi";
        final Count NODE_COUNT = new Count(250);
        final Probability EDGE_PROBABILITY = new Probability(0.05);

        GraphGenerator generator = new ErdosRenyiGenerator(NODE_COUNT, EDGE_PROBABILITY);
        Graph graph = generator.nextGraph();

        assertThat(graph.nodes().size(), is(equalTo(NODE_COUNT.value())));

        save(file, graph);
    }

    @Test
    public void oneBarabasiAndAlbert() throws UnsupportedEncodingException, FileNotFoundException, IOException {
        final String file = "target/random_barabasi_albert";
        final Count NODE_COUNT = new Count(150);
        final Probability ALPHA = new Probability(1D / 3);
        final Probability BETA = new Probability(1D / 3);

        GraphGenerator generate = new BarabasiAlbertGenerator(NODE_COUNT, ALPHA, BETA);
        Graph graph = generate.nextGraph();

        assertThat(graph.nodes().size(), is(equalTo(NODE_COUNT.value())));

        save(file, graph);
    }

    @Test
    public void oneWattsStrogatz() throws UnsupportedEncodingException, FileNotFoundException, IOException {
        final String file = "target/random_watts_strogatz";
        final Count NODE_COUNT = new Count(250);
        final Count NEIGHBORHOOD = new Count(10);
        final Probability RELINKING_PROBABILITY = new Probability(0.05);

        GraphGenerator generate = new WattsStrogatzGenerator(NODE_COUNT, NEIGHBORHOOD, RELINKING_PROBABILITY); 
        Graph graph = generate.nextGraph();

        assertThat(graph.nodes().size(), is(equalTo(NODE_COUNT.value())));
        for (int i = 0; i < graph.nodes().size(); i++) {
            assertThat(graph.edges(), not(hasItem(new Edge(node(i), node(i)))));
        }

        save(file, graph);
    }

    private void save(String file, Graph graph) throws UnsupportedEncodingException, IOException {
        saveAsDot(file, graph);
        saveAsCsv(file, graph);
    }

    private void saveAsDot(final String file, Graph graph) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        DotWriter dot = new DotWriter();
        final FileOutputStream output = new FileOutputStream(file + ".dot");
        dot.write(graph, output);
        output.close();
    }

    private void saveAsCsv(final String file, Graph graph) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        CsvWriter dot = new CsvWriter();
        final FileOutputStream output = new FileOutputStream(file + ".csv");
        dot.save(graph, output);
        output.close();
    }

}
