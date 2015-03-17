package eu.diversify.trio.performance.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class CsvRecorderTest {

    @Test
    public void shouldFormatProperlyCsv() throws IOException {
        final Task task = new Task() {

            @Override
            public Map<String, Object> getProperties() {
                Map<String, Object> results = new HashMap<>();
                results.put("size", 234.456);
                return results;
            }

            @Override
            public void execute() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };

        Performance performance = new Performance(23L);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        CsvRecorder recorder = new CsvRecorder(buffer, ",");
        recorder.record(25, task, performance);
        
        final String expectedCsv 
                = "run,size,duration" + System.lineSeparator() 
                + "25,234.456,23" + System.lineSeparator();

        assertThat(buffer.toString(), is(equalTo(expectedCsv)));

    }

}
