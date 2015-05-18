
package eu.diversify.trio.analytics.events;

/**
 * 
 */
public interface Listener {

    /**
     * Handle the publication of a new statistic
     * @param statistic some metadata describing the statistic being published
     * @param value the value being published
     */
    void statisticReady(Statistic statistic, double value);
    
}
