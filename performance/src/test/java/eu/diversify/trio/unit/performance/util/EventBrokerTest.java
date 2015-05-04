package eu.diversify.trio.unit.performance.util;

import eu.diversify.trio.performance.util.EventBroker;
import eu.diversify.trio.performance.util.Listener;
import java.util.Properties;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

/**
 * Test the event broker
 */
public class EventBrokerTest {

    private final Mockery mockery;

    public EventBrokerTest() {
        mockery = new Mockery();
    }
    
    @Test
    public void shouldSupportListenerSubscription() {
        EventBroker broker = new EventBroker();

        Listener listener = new Listener() {

            @Override
            public void onTaskCompleted(Properties properties) {
               
            }
        };

        broker.subscribe(0, listener);

        assertThat(broker.subscribers(), hasItem(listener));
    }

    @Test
    public void shouldSupportPublishingInformation() {
        final EventBroker broker = new EventBroker();

        final Listener listener1 = mockery.mock(Listener.class, "l1");
        final Listener listener2 = mockery.mock(Listener.class, "l2");
        
        broker.subscribe(0, listener1);
        broker.subscribe(1, listener2);  

        mockery.checking(new Expectations() {
            {
               never(listener1).onTaskCompleted(with(any(Properties.class)));
               exactly(1).of(listener2).onTaskCompleted(with(any(Properties.class)));
            }
        });

        broker.taskCompleted(1, new Properties());

        mockery.assertIsSatisfied();
    }

}
