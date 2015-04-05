package eu.diversify.trio.performance;

import eu.diversify.trio.performance.util.CsvRecorder;
import eu.diversify.trio.performance.util.MicroBenchmark;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Entry point of the performance test
 */
public class Runner {

    public static void main(String[] args) {
        try {
            Runner runner = new Runner(Options.readFrom(args));
            runner.run();

        } catch (IllegalArgumentException error) {
            System.out.println("ERROR: " + error.getMessage());
        }
    }

    private final Options options;

    public Runner(Options options) {
        this.options = options;
    }

    public void run() {
        showHeader();
        final OutputStream outputFile = openOutputFile();
        final Setup setup = loadSetupFile();
        runBenchmark(setup, outputFile);
        closeOutput(outputFile);
    }

    private void showHeader() {
        System.out.println("TRIO - Topology Robustness Indicator");
        System.out.println("Copyright (C) 2015 - SINTEF ICT");
        System.out.println("");
    }

    private Setup loadSetupFile() {
        Setup result;
        try {
            System.out.println("Reading configuration in '" + options.getSetupFile() + "'");
            result = options.getSetup();

        } catch (IOException ex) {
            final String error
                    = String.format("Unable to open the setup file '%s' (%s)",
                            options.getSetupFile(),
                            ex.getMessage());

            System.out.println(error);
            System.out.println("Switching to the default setup");
            result = new Setup(); // The default setup
        }

        System.out.println("");
        System.out.println(result.summary());
        return result;
    }

    private OutputStream openOutputFile() throws IllegalArgumentException {
        OutputStream outputFile = null;
        try {
            outputFile = new FileOutputStream(options.getOutputFile());

        } catch (FileNotFoundException error) {
            final String message
                    = String.format("Unable to open output file '%s' (%s)",
                            options.getOutputFile(),
                            error.getMessage());
            throw new IllegalArgumentException(message, error);
        }
        return outputFile;
    }

    private void runBenchmark(final Setup setup, final OutputStream outputFile) {
        final MicroBenchmark benchmark = setup.prepareBenchmark();
        benchmark.run(new CsvRecorder(outputFile));
        System.out.println("");
        System.out.println("OK");
        System.out.println("Results written in '" + options.getOutputFile() + "'");
    }


    private void closeOutput(final OutputStream outputFile) throws IllegalArgumentException {
        try {
            outputFile.close();

        } catch (IOException error) {
            String message 
                    = String.format("Unable to close the selected output '%s' (%s)", 
                            options.getOutputFile(), 
                            error.getMessage());
            throw new IllegalArgumentException(message, error);
        }
    }

}
