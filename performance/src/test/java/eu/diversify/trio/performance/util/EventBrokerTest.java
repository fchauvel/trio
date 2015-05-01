package eu.diversify.trio.performance.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
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
    public void shouldBeGloballyAvailable() {
        EventBroker broker = EventBroker.instance();
        assertThat(broker, is(not(nullValue())));
    }
    
    @Test
    public void shouldSupportListenerSubscription() {
        EventBroker broker = new EventBroker();

        Listener listener = new Listener() {
            @Override
            public void onCompletionOfTask(int taskId, int totalTaskCount) {
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
               never(listener1).onCompletionOfTask(1, 100);
               exactly(1).of(listener2).onCompletionOfTask(1, 100);
            }
        });

        broker.taskCompleted(1, 1, 100);

        mockery.assertIsSatisfied();
    }

}
