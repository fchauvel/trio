package eu.diversify.trio.performance.util;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Record benchmark data in a CSV format
 */
public class CsvRecorder implements Recorder {

    private final String fieldSeparator;
    private final PrintWriter output;

    public CsvRecorder(OutputStream destination) {
        this.fieldSeparator = ",";
        this.output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(destination, Charset.defaultCharset())), true);
    }

    @Override
    public void record(int runIndex, Task task, Performance performance) {
        output.print(runIndex);
        final Map<String, Object> taskProperties = task.getProperties();
        for (String eachProperty : taskProperties.keySet()) {
            output.print(fieldSeparator);
            output.print(taskProperties.get(eachProperty));
        }
        final Map<String, Object> performanceProperties = performance.getProperties();
        for (String eachProperty : performanceProperties.keySet()) {
            output.print(fieldSeparator);
            output.print(performanceProperties.get(eachProperty));
        }
        output.println();
    }

}
