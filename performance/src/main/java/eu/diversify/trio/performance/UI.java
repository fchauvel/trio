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

    public ProgressBar warmup() {
        if (warmup == null) {
            warmup = new ProgressBar(System.out, BAR_WIDTH);
        }
        return warmup;
    }

    public ProgressBar benchmark() {
        if (benchmark == null) {
            benchmark = new ProgressBar(System.out, BAR_WIDTH);
        }
        return benchmark;
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
                warmup().refresh();
            }
        } catch (IOException ex) {
            Logger.getAnonymousLogger().log(Level.INFO, "I/O error while refreshing the UI", ex);
        }
    }

}
