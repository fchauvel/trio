package eu.diversify.trio.performance;

import eu.diversify.trio.performance.util.Listener;
import eu.diversify.trio.performance.util.ProgressBar;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The text-based user interface
 */
public class UI {

    private static final int BAR_WIDTH = 70;

    private BenchmarkView warmup;
    private BenchmarkView benchmark;

    BenchmarkView warmupView() {
        if (warmup == null) {
            System.out.println("");
            System.out.println("Warming-up ... ");
            warmup = new BenchmarkView(System.out);
        }
        return warmup;
    }

    BenchmarkView benchmarkView() {
        if (benchmark == null) {
            System.out.println();
            System.out.println();
            System.out.println("Evaluating performance ... ");
            benchmark = new BenchmarkView(System.out);
        }
        return benchmark;
    }

    public void showOpening() {
        System.out.println("TRIO - Topology Robustness Indicator");
        System.out.println("Copyright (C) 2015 - SINTEF ICT");
        System.out.println();
    }
    
    public void showClosing() {
        System.out.println();
        System.out.println("That's all folks!");
    }
    
    public void info(String info) {
        System.out.println(info);
    }
    
    public void error(String error) {
        System.err.println(error);
    }

    
    static class BenchmarkView implements Listener {

        private final ProgressBar bar;
        private int taskCount;
        
        public BenchmarkView(OutputStream out) {
            this.taskCount = 0;
            this.bar = new ProgressBar(out, BAR_WIDTH);
        }

        @Override
        public void onCompletionOfTask(int taskId, int totalTaskCount) {
            taskCount++;
            double ratio = (double) taskCount / totalTaskCount;
            bar.setProgress(ratio);
            try {
                bar.refresh();
            
            } catch (IOException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, "I/O error while refreshing progress bar", ex);
            }
        }
        
        
    }
    

}
