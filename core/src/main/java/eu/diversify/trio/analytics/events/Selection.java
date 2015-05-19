
package eu.diversify.trio.analytics.events;

public interface Selection {

    boolean isSatisfiedBy(Statistic statistic, Object value);
        
}
