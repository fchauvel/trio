package eu.diversify.trio.analytics.events;

/**
 * Interface of objects that publish statistics
 */
public interface Publisher {

    /**
     * Subscribe the given listener to the stream if of statistics yield by this
     * publisher. The listener will be notify for any subsequent statistic.
     *
     * @param listener the listener object to be registered
     * @param selection the filter to select specific statistics of interest
     */
    void subscribe(Listener listener, Selection selection); 

}
