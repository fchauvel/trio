package eu.diversify.trio.performance;

import eu.diversify.trio.performance.util.MicroBenchmark;
import java.util.Properties;

/**
 * Contains the configuration of the performance test
 */
public class Setup {

    public enum Entry {

        MIN_ASSEMBLY_SIZE("minimum.assembly.size", "10"),
        MAX_ASSEMBLY_SIZE("maximum.assembly.size", "1000"),
        SAMPLE_COUNT("sample.count", "250"),
        WARM_UP_SAMPLE_COUNT("warmup.sample.count", "25"),
        OUTPUT_FILE_NAME("output.file.name", "scalability.csv");

        private final String key;
        private final String defaultValue;

        private Entry(String key, String defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }

        public String get(Properties values) {
            return values.getProperty(key);
        }
        
        public boolean isDefinedIn(Properties values) {
            return values.containsKey(key);
        }

        public void set(Properties values, Object value) {
            values.put(key, value.toString());
        }

        public void setDefaultValue(Properties values) {
            values.put(key, defaultValue);
        }
    }

    private final Properties properties;

    public Setup() {
        properties = new Properties();
        for (Entry eachEntry : Entry.values()) {
            eachEntry.setDefaultValue(properties);
        }
    }

    public Setup(Properties values) {
        this();
        for (Entry eachEntry : Entry.values()) {
            if (eachEntry.isDefinedIn(values)) {
                eachEntry.set(properties, eachEntry.get(values));
            }
        }
    }

    public Setup(int sampleCount, int minimumAssemblySize, int maximumAssemblySize, int warmupSampleCount, String outputFileName) {
        this();
        setSampleCount(sampleCount);
        setMinimumAssemblySize(minimumAssemblySize);
        setMaximumAssemblySize(maximumAssemblySize);
        setWarmupSampleCount(warmupSampleCount);
        setOuputFileName(outputFileName);
    }

    /**
     * @return a new micro benchmark object initialized according to this setup
     */
    public MicroBenchmark prepareBenchmark() {
        return new MicroBenchmark(getSampleCount(), getWarmupSampleCount(), makeFactory());
    }

    private SimulationFactory makeFactory() {
        return new SimulationFactory(getMinimumAssemblySize(), getMaximumAssemblySize());
    }

    public String summary() {
        return String.format("%d simulation(s) (assembly sizes within [%d, %d])", getSampleCount(), getMinimumAssemblySize(), getMaximumAssemblySize());
    }

    /**
     * Get the value of ouputFileName
     *
     * @return the value of ouputFileName
     */
    public String getOuputFileName() {
        return Entry.OUTPUT_FILE_NAME.get(properties);
    }

    /**
     * Set the value of ouputFileName
     *
     * @param outputFileName new value of ouputFileName
     */
    public final void setOuputFileName(String outputFileName) {
        if (outputFileName == null) {
            throw new IllegalArgumentException("Invalid output file name (found 'null')");
        }
        if (outputFileName.isEmpty()) {
            throw new IllegalArgumentException("Invalid output file name (found '')");
        }
        Entry.OUTPUT_FILE_NAME.set(properties, outputFileName);
    }

    /**
     * Get the value of warmupSampleCount
     *
     * @return the value of warmupSampleCount
     */
    public int getWarmupSampleCount() {
        return Integer.parseInt(Entry.WARM_UP_SAMPLE_COUNT.get(properties));
    }

    /**
     * Set the value of warmupSampleCount
     *
     * @param warmupSampleCount new value of warmupSampleCount
     */
    public final void setWarmupSampleCount(int warmupSampleCount) {
        validateCount(warmupSampleCount, "warm-up sample size");
        Entry.WARM_UP_SAMPLE_COUNT.set(properties, warmupSampleCount);
    }

    /**
     * Get the value of maximumAssemblySize
     *
     * @return the value of maximumAssemblySize
     */
    public int getMaximumAssemblySize() {
        return Integer.parseInt(Entry.MAX_ASSEMBLY_SIZE.get(properties));
    }

    /**
     * Set the value of maximumAssemblySize
     *
     * @param maximumAssemblySize new value of maximumAssemblySize
     */
    public final void setMaximumAssemblySize(int maximumAssemblySize) {
        validateCount(maximumAssemblySize, "maximum assembly size");
        if (maximumAssemblySize < getMinimumAssemblySize()) {
            throw new IllegalArgumentException("Invalid 'maximum assembly size' (should be above 'minimum assembly size'," + getMinimumAssemblySize() + ")");
        }
        Entry.MAX_ASSEMBLY_SIZE.set(properties, maximumAssemblySize);
    }

    /**
     * Get the value of minimumAssemblySize
     *
     * @return the value of minimumAssemblySize
     */
    public int getMinimumAssemblySize() {
        return Integer.parseInt(Entry.MIN_ASSEMBLY_SIZE.get(properties));
    }

    /**
     * Set the value of minimumAssemblySize
     *
     * @param minimumAssemblySize new value of minimumAssemblySize
     */
    public final void setMinimumAssemblySize(int minimumAssemblySize) {
        validateCount(minimumAssemblySize, "minimum assembly size");
        if (minimumAssemblySize > getMaximumAssemblySize()) {
            throw new IllegalArgumentException("Invalid 'minimum assembly size' (should be greated than 'maximum assembly size'," + getMaximumAssemblySize() + ")");
        }
        Entry.MIN_ASSEMBLY_SIZE.set(properties, minimumAssemblySize);
    }

    /**
     * @return the number of sample generated by the benchmark
     */
    public int getSampleCount() {
        return Integer.parseInt(Entry.SAMPLE_COUNT.get(properties));
    }

    /**
     * Set the number of samples
     *
     * @param sampleCount the number of sample to be generated by the benchmark
     */
    public final void setSampleCount(int sampleCount) {
        validateCount(sampleCount, "sample count");
        Entry.SAMPLE_COUNT.set(properties, sampleCount);
    }

    private void validateCount(int sampleCount, String property) throws IllegalArgumentException {
        if (sampleCount <= 0) {
            final String error = String.format("Property '%s' must be strictly positive (value '%d' found)", property, sampleCount);
            throw new IllegalArgumentException(error);
        }
    }

}
