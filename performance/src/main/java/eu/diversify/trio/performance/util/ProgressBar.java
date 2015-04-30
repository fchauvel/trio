package eu.diversify.trio.performance.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * A simple text based progress bar
 */
public class ProgressBar {

    private static final double DEFAULT_PROGRESS = 0D;

    private Writer output;
    private int width;
    private double progress;

    public ProgressBar(OutputStream out, int width) {
        this(out, width, DEFAULT_PROGRESS);
    }

    public ProgressBar(OutputStream out, int width, double progress) {
        setOutput(out);
        setWidth(width);
        setProgress(progress);
    }

    private void setOutput(OutputStream out) {
        if (out == null) {
            final String description = String.format("Invalid output stream ('null' found)");
            throw new IllegalArgumentException(description);
        }
        this.output = new PrintWriter(out);
    }

    private void setWidth(int width) {
        if (width <= HEADER_LENGTH) {
            final String description = String.format("Progress bars must be at least %d character wide (but width of %d characters requested)", HEADER_LENGTH, width);
            throw new IllegalArgumentException(description);
        }
        this.width = width;
    }

    public void refresh() throws IOException {
        output.write('\r');
        final String view = String.format("%3d %% [%s]", progressAsPercentage(), progressBar());
        output.write(view);
        output.flush();
    }

    public final void setProgress(double progress) {
        if (progress > 1D) {
            String description = String.format("Progress must be within [0, 1] (but found %.3f)", progress);
            throw new IllegalArgumentException(description);
        }
        if (progress - this.progress < 0D) {
            String description = String.format("Regression detected (current state %.3f, next %.3f)", this.progress, progress);
            throw new IllegalArgumentException(description);
        }
        this.progress = progress;
    }

    private long progressAsPercentage() {
        return Math.round(progress * 100D);
    }

    private String progressBar() {
        final StringBuilder builder = new StringBuilder();

        final int arrowLength = arrowLength();
        for (int i = 0; i < arrowLength; i++) {
            builder.append(DONE_MARKER);
        }

        if (arrowLength < barWidth()) {
            builder.append(HEAD_MARKER);
        }

        for (int i = arrowLength + 1; i < barWidth(); i++) {
            builder.append(TODO_MARKER);
        }

        final String progressBar = builder.toString();
        assert progressBar.length() == barWidth() : "Wrong progress bar width (expected " + barWidth() + ", but found " + progressBar.length() + ")";

        return progressBar;
    }

    private int arrowLength() {
        return (int) Math.round(barWidth() * progress);
    }

    private static final char TODO_MARKER = ' ';
    private static final char HEAD_MARKER = '>';
    private static final char DONE_MARKER = '=';

    private int barWidth() {
        return width - HEADER_LENGTH;
    }

    private static final int HEADER_LENGTH = 8; // thee length of 'XXX % [...]'

}
