
package eu.diversify.trio.performance;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The options which can be given through the command line
 */
public class Arguments {

    public static final String DEFAULT_OUTPUT_FILE = "scalability.csv";
    public static final String DEFAULT_SETUP_FILE = "setup.properties";

    private String outputFileName;
    private String setupFile;

    public Arguments() {
        this(DEFAULT_SETUP_FILE, DEFAULT_OUTPUT_FILE);
    }

    public Arguments(String setupFile, String outputFile) {
        this.setupFile = setupFile;
        this.outputFileName = outputFile;
    }

    public String getOutputFile() {
        return outputFileName;
    }

    public void setOutputFile(String outputFile) {
        this.outputFileName = outputFile;
    }

    public String getSetupFile() {
        return setupFile;
    }

    public void setSetupFile(String setupFile) {
        this.setupFile = setupFile;
    }

    private static List<String> SETUP_MARKERS = Arrays.asList(new String[]{"-setup", "-s"});
    private static List<String> OUTPUT_MARKERS = Arrays.asList(new String[]{"-output", "-o"});

    /**
     * Convert the raw command line arguments into an 'Option' object.
     *
     * @param args the raw command line argument
     * @return the corresponding Option object.
     */
    public static Arguments readFrom(String[] args) {
        final Arguments results = new Arguments();
        final Iterator<String> iterator = Arrays.asList(args).iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (SETUP_MARKERS.contains(next)) {
                extractSetupFile(iterator, results);
            } else if (OUTPUT_MARKERS.contains(next)) {
                extractOutputFile(iterator, results);
            } else {
                throw new IllegalArgumentException("Unknown option '" + next + "'");
            }
        }
        return results;
    }

    private static void extractOutputFile(final Iterator<String> iterator, final Arguments results) throws IllegalArgumentException {
        if (!iterator.hasNext()) {
            throw new IllegalArgumentException("Expecting output file");
        }
        results.setOutputFile(iterator.next());
    }

    private static void extractSetupFile(final Iterator<String> iterator, final Arguments results) throws IllegalArgumentException {
        if (!iterator.hasNext()) {
            throw new IllegalArgumentException("Expecting setup file");
        }
        results.setSetupFile(iterator.next());
    }

}
