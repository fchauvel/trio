package eu.diversity.trio.unit.performance;

import eu.diversify.trio.performance.Setup;
import eu.diversify.trio.performance.Setup.Entry;
import java.util.Properties;
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
    public void shouldReadMaximumAssmblySizeFromProperties() {
        final int SIZE = 45673;

        Properties properties = new Properties();
        Entry.MAX_ASSEMBLY_SIZE.set(properties, String.valueOf(SIZE));
        Setup setup = new Setup(properties);

        assertThat(setup.getMaximumAssemblySize(), is(equalTo(SIZE)));
    }

    @Test
    public void shouldReadMinimumAssmblySizeFromProperties() {
        final int SIZE = 234;

        Properties properties = new Properties();
        Entry.MIN_ASSEMBLY_SIZE.set(properties, String.valueOf(SIZE));
        Setup setup = new Setup(properties);

        assertThat(setup.getMinimumAssemblySize(), is(equalTo(SIZE)));
    }

    @Test
    public void shouldStoreMaximumEdgeProbability() {
        final double PROBABILITY = 0.5D;

        setup.setMaximumEdgeProbability(PROBABILITY);

        assertThat(setup.getMaximumEdgeProbability(), is(equalTo(PROBABILITY)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNegativeProbability() {
        final double PROBABILITY = -0.5D;

        setup.setMaximumEdgeProbability(PROBABILITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectProbabilityAboveOne() {
        final double PROBABILITY = 1.5D;

        setup.setMaximumEdgeProbability(PROBABILITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectMinimumProbabilityAboveMaximumProbability() {
        setup.setMaximumEdgeProbability(0.5);
        setup.setMinimumEdgeProbability(0.6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectMaximumProbabilityBelowMinimumProbability() {
        setup.setMinimumEdgeProbability(0.5);
        setup.setMaximumEdgeProbability(0.3);
    }

    @Test
    public void shouldStoreMinimumEdgeProbability() {
        final double PROBABILITY = 0.5D;

        setup.setMinimumEdgeProbability(PROBABILITY);

        assertThat(setup.getMinimumEdgeProbability(), is(equalTo(PROBABILITY)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNegativeMinimumEdgeProbability() {
        final double PROBABILITY = -0.5D;

        setup.setMinimumEdgeProbability(PROBABILITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectMinimumEdgeProbabilityAboveOne() {
        final double PROBABILITY = 1.5D;

        setup.setMinimumEdgeProbability(PROBABILITY);
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

}
