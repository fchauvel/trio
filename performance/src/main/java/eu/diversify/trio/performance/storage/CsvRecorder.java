package eu.diversify.trio.performance.storage;

import eu.diversify.trio.performance.util.Listener;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Properties;

/**
 * Record properties in a CSV format
 */
public class CsvRecorder implements Listener {

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

    public void record(Properties properties) {
        if (!headerPrinted) {
            printHeader(properties);
        }
        printValues(properties);
    }

    
    private void printValues(Properties properties) {
        int counter = 0;
        for (Map.Entry eachEntry : properties.entrySet()) {
            counter++;
            output.print(eachEntry.getValue());
            if (counter < properties.size()) {
                output.print(fieldSeparator);
            }
        }
        output.println();
    }

    private void printHeader(Properties taskProperties) {
        assert !headerPrinted : "Header already printed!";

        int counter = 0;
        for (Object eachKey : taskProperties.keySet()) {
            counter++;
            output.print(eachKey);
            if (counter < taskProperties.size()) {
                output.print(fieldSeparator);
            }
        }
        output.println();

        this.headerPrinted = true;
    }

    @Override
    public void onTaskCompleted(Properties properties) {
        record(properties);
    }

}
