package eu.diversify.trio.performance.util;

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

        MicroBenchmarkListener listener = new MicroBenchmarkListener() {
            @Override
            public void onCompletionOfTask(int taskId, int totalTaskCount, boolean isWarmUp) {
            }
        };

        broker.subscribe(listener);

        assertThat(broker.subscribers(), hasItem(listener));
    }

    @Test
    public void shouldSupportPublishingInformation() {
        final EventBroker broker = new EventBroker();

        final MicroBenchmarkListener listener = mockery.mock(MicroBenchmarkListener.class);
        broker.subscribe(listener);

        mockery.checking(new Expectations() {
            {
               exactly(1).of(listener).onCompletionOfTask(1, 100, true);
            }
        });

        broker.taskCompleted(1, 100, true);

        mockery.assertIsSatisfied();
    }

}
