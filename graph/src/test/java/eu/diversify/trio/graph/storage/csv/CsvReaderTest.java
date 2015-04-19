package eu.diversify.trio.graph.storage.csv;

import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.graph.model.Vertex;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

/**
 * Specifications of the CSVReader
 */
public class CsvReaderTest {

    @Test
    public void shouldReadACsvEdgeTable() throws IOException {
        String csvText
                = "X1,X2" + System.lineSeparator()
                + "1,2" + System.lineSeparator()
                + "2,1" + System.lineSeparator();

        CsvReader reader = new CsvReader();

        Graph graph = reader.read(new ByteArrayInputStream(csvText.getBytes()));

        assertThat(graph.vertexCount(), is(equalTo(2)));

        Vertex v1 = graph.vertexWithId(0);
        Vertex v2 = graph.vertexWithId(1);
        assertThat(graph.edgeCount(), is(equalTo(2)));
        assertThat(graph.hasEdge(v1, v2), is(true));
        assertThat(graph.hasEdge(v2, v1), is(true));
    }

}
