package eu.diversify.trio.performance;

import eu.diversify.trio.performance.setup.Setup;
import eu.diversify.trio.performance.setup.SetupStore;
import eu.diversify.trio.performance.util.CsvRecorder;
import eu.diversify.trio.performance.util.MicroBenchmark;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Entry point of the performance test
 */
public class Runner {

    public static void main(String[] args) {
        try {
            Runner runner = new Runner(Arguments.readFrom(args));
            runner.run();

        } catch (IllegalArgumentException error) {
            System.out.println("ERROR: " + error.getMessage());
        }
    }

    private final Arguments arguments;

    public Runner(Arguments arguments) {
        this.arguments = arguments;
    }

    public void run() {
        showOpening();

        final Setup setup = loadSetupFile();

        final OutputStream outputFile = openOutputFile();

        final CsvRecorder recorder = new CsvRecorder(outputFile);
        for (MicroBenchmark eachBenchmark : setup.prepareBenchmarks()) {
            eachBenchmark.run(recorder);
        }

        closeOutputFile(outputFile);
    }

    private void showOpening() {
        System.out.println("TRIO - Topology Robustness Indicator");
        System.out.println("Copyright (C) 2015 - SINTEF ICT");
        System.out.println("");
    }

    private Setup loadSetupFile() {
        final SetupStore store = new SetupStore();
        final Properties properties = readProperties();
        final Setup setup = store.loadFromProperties(properties);

        System.out.println("%n" + setup.summary());
        
        return setup;
    }

    private Properties readProperties() {
        System.out.println("Reading configuration in '" + arguments.getSetupFile() + "'");
        final Properties properties = new Properties();
        try {
            
            properties.load(new FileInputStream(arguments.getSetupFile()));

        } catch (IOException ex) {
            final String error
                    = String.format("Unable to open the setup file '%s' (%s)",
                            arguments.getSetupFile(),
                            ex.getMessage());

            System.out.println(error);
            System.out.println("Switching to the default setup");
        }
        return properties;
    }

    private OutputStream openOutputFile() throws IllegalArgumentException {
        OutputStream outputFile = null;
        try {
            outputFile = new FileOutputStream(arguments.getOutputFile());

        } catch (FileNotFoundException error) {
            final String message
                    = String.format("Unable to open output file '%s' (%s)",
                            arguments.getOutputFile(),
                            error.getMessage());
            throw new IllegalArgumentException(message, error);
        }
        return outputFile;
    }


    private void closeOutputFile(final OutputStream outputFile) throws IllegalArgumentException {
        try {
            outputFile.close();

        } catch (IOException error) {
            String message
                    = String.format("Unable to close the selected output '%s' (%s)",
                            arguments.getOutputFile(),
                            error.getMessage());
            throw new IllegalArgumentException(message, error);
        }
    }

}
