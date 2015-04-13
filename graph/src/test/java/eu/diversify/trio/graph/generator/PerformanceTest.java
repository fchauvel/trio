/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.diversify.trio.graph.generator;

import eu.diversify.trio.graph.Graph;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.junit.Test;

public class PerformanceTest {

    @Test
    public void oneBarabasiAndAlbert() throws UnsupportedEncodingException, FileNotFoundException, IOException {
        final int RUN_COUNT = 100;
        final int NODE_COUNT = 250;

        GraphGenerator generate = new BarabasiAlbertGenerator(NODE_COUNT);

        long duration = 0L;
        for (int eachRun = 0; eachRun < RUN_COUNT; eachRun++) {
            long start = System.currentTimeMillis();
            Graph graph = generate.nextGraph();
            long end = System.currentTimeMillis();
            duration += end - start;
        }
        
        double averageDuration = (double) duration / RUN_COUNT;
        
        System.out.printf("%d B&A graph(s) generated in %.3f ms.%n", RUN_COUNT, averageDuration);
    }

}