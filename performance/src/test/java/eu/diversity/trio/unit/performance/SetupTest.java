package eu.diversity.trio.unit.performance;

import eu.diversify.trio.performance.Setup;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

/**
 * Specification of the setup object
 */
public class SetupTest {

    public final Setup setup;

    public SetupTest() {
        setup = new Setup();
    }

    @Test
    public void shouldStoreSampleCount() {
        final int SAMPLE_COUNT = 1000;

        setup.setSampleCount(SAMPLE_COUNT);

        assertThat(setup.getSampleCount(), is(equalTo(SAMPLE_COUNT)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNegativeSampleCount() {
        setup.setSampleCount(-1000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectZeroSampleCount() {
        setup.setSampleCount(0);
    }

    @Test
    public void shouldStoreWarmupSampleCount() {
        final int SAMPLE_COUNT = 25;

        setup.setWarmupSampleCount(SAMPLE_COUNT);

        assertThat(setup.getWarmupSampleCount(), is(equalTo(SAMPLE_COUNT)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectZeroWarmupSampleCount() {
        setup.setWarmupSampleCount(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNegativeWarmupSampleCount() {
        setup.setWarmupSampleCount(-1000);
    }

    @Test
    public void shouldStoreMinimumAssmblySize() {
        final int MIN_ASSEMBLY_SIZE = 25;

        setup.setMinimumAssemblySize(MIN_ASSEMBLY_SIZE);

        assertThat(setup.getMinimumAssemblySize(), is(equalTo(MIN_ASSEMBLY_SIZE)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectZeroMinimumAssemblySize() {
        setup.setMinimumAssemblySize(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNegativeMinimumAssemblySize() {
        setup.setMinimumAssemblySize(-3);
    }

    @Test
    public void shouldStoreMaximumAssemblySize() {
        final int MAX_ASSEMBLY_SIZE = 25;

        setup.setMaximumAssemblySize(MAX_ASSEMBLY_SIZE);

        assertThat(setup.getMaximumAssemblySize(), is(equalTo(MAX_ASSEMBLY_SIZE)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectZeroMaximumAssemblySize() {
        setup.setMaximumAssemblySize(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNegativeMaximumAssemblySize() {
        setup.setMaximumAssemblySize(-3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectMinimumAssemblySizeAboveMaximumAssemblySize() {
        setup.setMaximumAssemblySize(25000);
        setup.setMinimumAssemblySize(35000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectMaximumAssemblySizeBelowMinimumAssemblySize() {
        setup.setMinimumAssemblySize(2);
        setup.setMaximumAssemblySize(1);
    }

    @Test
    public void shouldStoreOutputFileName() {
        final String path = "my_file.csv";

        setup.setOuputFileName(path);

        assertThat(setup.getOuputFileName(), is(equalTo(path)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNullOutputFile() {
        final String path = null;

        setup.setOuputFileName(path);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectEmptyOutputFileName() {
        final String path = null;

        setup.setOuputFileName(path);
    }

}
