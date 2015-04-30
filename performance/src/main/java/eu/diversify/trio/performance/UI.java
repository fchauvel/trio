package eu.diversify.trio.performance;

import eu.diversify.trio.performance.util.MicroBenchmarkListener;
import eu.diversify.trio.performance.util.ProgressBar;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The text-based user interface
 */
public class UI implements MicroBenchmarkListener {

    private static final int BAR_WIDTH = 70;

    private ProgressBar warmup;
    private ProgressBar benchmark;

    private ProgressBar warmup() {
        if (warmup == null) {
            System.out.println("");
            System.out.println("Warming-up ... ");
            warmup = new ProgressBar(System.out, BAR_WIDTH);
        }
        return warmup;
    }

    private ProgressBar benchmark() {
        if (benchmark == null) {
            System.out.println();
            System.out.println();
            System.out.println("Evaluating performance ... ");
            benchmark = new ProgressBar(System.out, BAR_WIDTH);
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

    @Override
    public void onCompletionOfTask(int taskId, int totalTaskCount, boolean isWarmUp) {
        try {
            double progress = (double) taskId / totalTaskCount;
            if (isWarmUp) {
                warmup().setProgress(progress);
                warmup().refresh();
            } else {
                benchmark().setProgress(progress);
                benchmark().refresh();
            }
        } catch (IOException ex) {
            Logger.getAnonymousLogger().log(Level.INFO, "I/O error while refreshing the UI", ex);
        }
    }
    
    public void info(String info) {
        System.out.println(info);
    }
    
    public void error(String error) {
        System.err.println(error);
    }

}
