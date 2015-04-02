
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

    public static void main(String[] args) throws FileNotFoundException, IOException { 
        final OutputStream outputFile = new FileOutputStream("scalability.csv");
        
        SimulationFactory factory = new SimulationFactory(MIN_SIZE, MAX_SIZE);
        MicroBenchmark benchmark = new MicroBenchmark(SAMPLE_COUNT, WARM_UP_SAMPLES, factory, new CsvRecorder(outputFile, ","));
        
        benchmark.run();
        
        outputFile.close();
    }
    public static final int MAX_SIZE = 1000;
    public static final int MIN_SIZE = 10;
    public static final int WARM_UP_SAMPLES = 25;
    public static final int SAMPLE_COUNT = 200;
    
}
