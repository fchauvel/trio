package eu.diversify.trio.unit.core.adapters;

import eu.diversify.trio.codecs.Builder;
import eu.diversify.trio.codecs.SyntaxError;
import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.core.Component;
import eu.diversify.trio.core.adapters.GraphAdapter;
import static eu.diversify.trio.core.requirements.Factory.not;
import static eu.diversify.trio.core.requirements.Factory.require;
import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.graph.storage.csv.CsvWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

/**
 *
 */
public class GraphAdapterTest {

    @Test
    public void shouldConvertAssemblyToGraph() {
        Assembly assembly = new Assembly(
                new Component("C1", require("C2")),
                new Component("C2", require("C3").and(require("C4"))),
                new Component("C3", require("C5").or(require("C6"))),
                new Component("C4", not(require("C7"))),
                new Component("C5"),
                new Component("C6"),
                new Component("C7")
        );

        GraphAdapter adapter = new GraphAdapter();
        Graph graph = adapter.adapt(assembly);

        assertThat(graph.vertexCount(), is(equalTo(assembly.size())));
        assertThat(graph.edgeCount(), is(equalTo(6)));

    }

    @Test
    public void shouldConvertExistingFiles() throws FileNotFoundException, IOException, SyntaxError {
        final String[][] conversion = new String[][]{
            {"src/test/resources/samples/sensapp.trio", "target/sensapp.csv"},
            {"src/test/resources/samples/sensapp_topo1.trio", "target/sensapp_topo1.csv"},
            {"src/test/resources/samples/sensapp_topo2.trio", "target/sensapp_topo2.csv"},
            {"src/test/resources/samples/sensapp_topo3.trio", "target/sensapp_topo3.csv"},
            {"src/test/resources/samples/sensapp_topo4.trio", "target/sensapp_topo4.csv"}
        };

        for (String[] eachConversion : conversion) {
            final String trioFile = eachConversion[0];
            final String csvFile = eachConversion[1];
            assemblyToCsv(trioFile, csvFile);
        }

    }

    private void assemblyToCsv(final String trioFile, final String csvFile) throws FileNotFoundException, UnsupportedEncodingException, IOException, SyntaxError {
        final FileInputStream input = new FileInputStream(trioFile);
        Builder builder = new Builder();
        Assembly assembly = builder.systemFrom(input);
        input.close();

        Graph graph = new GraphAdapter().adapt(assembly);

        CsvWriter edgeList = new CsvWriter();
        final FileOutputStream output = new FileOutputStream(csvFile);
        edgeList.save(graph, output);
        output.close();
    }

}
