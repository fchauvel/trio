package eu.diversify.trio.graph.storage.dot;

import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.graph.SampleGraphs;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

/**
 * Specification of the DotWriter, which serializes a given graph in the dot
 * format.
 */
public class DotWriterTest {


    @Test
    public void shouldSerializeAGraphProperly() throws UnsupportedEncodingException {
        DotWriter writer = new DotWriter();
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        Graph graph = SampleGraphs.twoNodesRing();

        writer.write(graph, output);

        final String dotText = output.toString();
        
        String expectedDotText 
                = "digraph XXX {" + EOL
                + "\tn0;" + EOL
                + "\tn1;" + EOL
                + "\tn0 -> n1;" + EOL
                + "\tn1 -> n0;" + EOL
                + "}" + EOL;
        
        assertThat(dotText, is(equalTo(expectedDotText)));
        
    }
    private static final String EOL = System.lineSeparator();

}
