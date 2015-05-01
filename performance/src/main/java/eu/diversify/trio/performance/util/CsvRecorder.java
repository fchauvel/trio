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

    public static final String DEFAULT_FIELD_SEPARATOR = ",";

    private final String fieldSeparator;
    private final PrintWriter output;
    private boolean headerPrinted;

    public CsvRecorder(OutputStream destination) {
        this(destination, DEFAULT_FIELD_SEPARATOR);
    }

    public CsvRecorder(OutputStream destination, String fieldSeparator) {
        this.fieldSeparator = fieldSeparator;
        this.output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(destination, Charset.defaultCharset())), true);
        headerPrinted = false;
    }

    @Override
    public void record(int runIndex, Task task, Observation performance) {
        if (!headerPrinted) {
            printHeaderLine(task, performance);
        }
        printProperties(runIndex, task, performance);
    }

    private void printProperties(int runIndex, Task task, Observation performance) {
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

    private void printHeaderLine(Task task, Observation performance) {
        headerPrinted = true;
        output.print("run");
        for (String eachProperty : task.getProperties().keySet()) {
            output.print(fieldSeparator);
            output.print(eachProperty);
        }
        for (String eachProperty : performance.getProperties().keySet()) {
            output.print(fieldSeparator);
            output.print(eachProperty);
        }
        output.println();
    }

}
