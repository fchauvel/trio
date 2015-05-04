package eu.diversify.trio.performance;

import eu.diversify.trio.performance.ui.UI;
import eu.diversify.trio.performance.setup.Setup;
import eu.diversify.trio.performance.setup.SetupStore;
import eu.diversify.trio.performance.util.CsvRecorder;
import eu.diversify.trio.performance.util.EventBroker;
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
    private final UI ui;

    public Runner(Arguments arguments) {
        this.arguments = arguments;
        this.ui = new UI();
    }

    public void run() {
        
        ui.showOpening();

        final Setup setup = loadSetupFile();

        final OutputStream outputFile = openOutputFile();
        final CsvRecorder recorder = new CsvRecorder(outputFile);
        
        final MicroBenchmark warmup = setup.warmup();
        EventBroker.instance().subscribe(warmup.id(), ui.warmupView()); 
        warmup.run();
        
        final MicroBenchmark benchmark = setup.benchmark();
        EventBroker.instance().subscribe(benchmark.id(), ui.benchmarkView()); 
        EventBroker.instance().subscribe(benchmark.id(), recorder);
        benchmark.run();
        
        closeOutputFile(outputFile);
        
        ui.showClosing();
    }

   

    private Setup loadSetupFile() {
        final SetupStore store = new SetupStore();
        final Properties properties = readProperties();
        final Setup setup = store.loadFromProperties(properties);

        System.out.println(setup.summary());

        return setup;
    }

    private Properties readProperties() {
        ui.info("Reading configuration in '" + arguments.getSetupFile() + "'");
        final Properties properties = new Properties();
        try {

            properties.load(new FileInputStream(arguments.getSetupFile()));

        } catch (IOException ex) {
            final String description
                    = String.format("Unable to open the setup file '%s' (%s)",
                            arguments.getSetupFile(),
                            ex.getMessage());

            ui.error(description);
            ui.info("Switching to the default setup");
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
