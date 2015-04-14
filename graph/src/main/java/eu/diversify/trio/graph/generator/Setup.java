package eu.diversify.trio.graph.generator;

import java.util.Properties;

/**
 * Configuration of the generator. It permit to instantiate the associated
 * generator.
 */
public interface Setup {

    
    /**
     * Update this setup with the value contained in the given
     * @param properties the properties to be used as configuration
     */
    void updateWith(Properties properties);
    
    /**
     * @return a brand new instance of the graph generator associated with this
     * setup.
     */
    GraphGenerator instantiate();

    
}
