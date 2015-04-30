package eu.diversify.trio.performance.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

/**
 * Specification of the progress bar
 */
public class ProgressBarTest {

    private final ProgressBar bar;
    private final ByteArrayOutputStream output;

    public ProgressBarTest() {
        output = new ByteArrayOutputStream();
        bar = new ProgressBar(output, 80);
    }

    @Test
    public void shouldHaveACorrectWidth() throws IOException {
        bar.refresh();

        assertThat(output.toString(), is(equalTo("\r  0 % [>                                                                       ]")));
    }

    @Test
    public void shouldReflectProgressProperly() throws IOException {
        bar.setProgress(0.1D);
        bar.refresh();

        assertThat(output.toString(), is(equalTo("\r 10 % [=======>                                                                ]")));
    }

    @Test
    public void shouldReflectCompletionProperly() throws IOException {

        bar.setProgress(1D);
        bar.refresh();

        assertThat(output.toString(), is(equalTo("\r100 % [========================================================================]")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectRegression() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ProgressBar bar = new ProgressBar(output, 80);

        bar.setProgress(0.5D);
        bar.setProgress(0.1D);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNegativeInvalidProgress() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ProgressBar bar = new ProgressBar(output, 80);

        bar.setProgress(12.45D);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNegativeWidth() {
        ProgressBar bar = new ProgressBar(output, -5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectTooSmallWidth() {
        ProgressBar bar = new ProgressBar(output, 8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNullOutput() {
        ProgressBar bar = new ProgressBar(null, 40);
    }

}
