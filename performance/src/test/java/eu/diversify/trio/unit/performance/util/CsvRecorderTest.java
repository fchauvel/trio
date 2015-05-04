package eu.diversify.trio.unit.performance.util;

import eu.diversify.trio.performance.util.CsvRecorder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class CsvRecorderTest {

    @Test
    public void shouldFormatProperlyCsv() throws IOException {

        Properties results = new Properties();
        results.put("run", 25);
        results.put("size", 234.456);
        results.put("duration", 23L);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        CsvRecorder recorder = new CsvRecorder(buffer, ",");
        
        recorder.record(results);
        recorder.record(results);

        final String expectedCsv
                = "size,duration,run" + EOL
                + "234.456,23,25" + EOL
                + "234.456,23,25" + EOL;

        assertThat(buffer.toString(), is(equalTo(expectedCsv)));

    }
    private static final String EOL = System.lineSeparator();

}
