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

    private final Options options;

    public Runner(Options options) {
        this.options = options;
    }

    public void run() throws FileNotFoundException, IOException {
        System.out.println("TRIO - Topology Robustness Indicator");
        System.out.println("Copyright (C) 2015 - SINTEF ICT");

        final Setup setup = options.getSetup();
        System.out.println(setup.summary());

        try {
            final OutputStream outputFile = new FileOutputStream(options.getOutputFile());
            final MicroBenchmark benchmark = setup.prepareBenchmark();
            benchmark.run(new CsvRecorder(outputFile));
            outputFile.close();
            System.out.println("Done. (Results saved in '" + setup.getOuputFileName() + "')");

        } catch (FileNotFoundException error) {

        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Runner runner = new Runner(Options.readFrom(args));
        runner.run();
    }


}
