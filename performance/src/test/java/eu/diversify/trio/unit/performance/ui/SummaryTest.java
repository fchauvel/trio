package eu.diversify.trio.unit.performance.ui;

import eu.diversify.trio.performance.ui.Summary;
import java.io.ByteArrayOutputStream;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

/**
 * test the behaviour of the summary 
 */
public class SummaryTest {

    @Test
    public void shouldUpdateProgressProperly() {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        Summary summary = new Summary(output);

        summary.setProgress(0.5);
        summary.refresh();

        String result = output.toString();
        assertThat(result, result.contains("50"), is(true));
    }

    @Test
    public void shouldUpdateDurationProperly() {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        Summary summary = new Summary(output);

        summary.setAverageDuration(239.00 * 1e7);
        summary.refresh();

        String result = output.toString();
        assertThat(result, result.contains("239.00"), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNegativeProgress() {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        Summary summary = new Summary(output);

        summary.setProgress(0.5);
        summary.setProgress(0.4);
    }
}
