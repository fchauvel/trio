
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
        
        SimulationFactory factory = new SimulationFactory(1, 1000);
        MicroBenchmark benchmark = new MicroBenchmark(500, 20, factory, new CsvRecorder(outputFile));
        
        benchmark.run();
        
        outputFile.close();
    }
    
}
