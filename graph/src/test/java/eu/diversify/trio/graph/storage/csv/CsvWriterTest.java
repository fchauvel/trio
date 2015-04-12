package eu.diversify.trio.graph.storage.csv;

import eu.diversify.trio.graph.AdjacencyMatrix;
import eu.diversify.trio.graph.Graph;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;



/**
 * Specification of the storage of the incidence matrix as a CSV file
 */
public class CsvWriterTest {
    
    
    @Test
    public void shouldStoreCSV() throws UnsupportedEncodingException {
        String adjacency 
                = "011" 
                + "001"
                + "110";
        
        Graph graph = AdjacencyMatrix.from(adjacency);
        
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        CsvWriter writer = new CsvWriter();
        
        writer.save(graph, output);
        
        String csvCode = output.toString();
        String expectedCsvCode 
                = "source,target" + EOL
                + "n0,n1" + EOL
                + "n0,n2" + EOL
                + "n1,n2" + EOL
                + "n2,n0" + EOL
                + "n2,n1" + EOL;
        
        assertThat(csvCode, is(equalTo(expectedCsvCode)));
    }
    
    private static final String EOL = System.lineSeparator();
}
