/**
 *
 * This file is part of TRIO.
 *
 * TRIO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.diversify.trio.unit.analytics.events;

import eu.diversify.trio.analytics.events.Channel;
import eu.diversify.trio.analytics.events.Listener;
import eu.diversify.trio.analytics.events.Selection;
import eu.diversify.trio.analytics.events.Statistic;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

/**
 * Specification of the Channel
 */
public class ChannelTest {

    private final Mockery context = new Mockery();

    @Test
    public void shouldConveyMessageToInterestedListeners() {
        final Channel channel = new Channel();
        final Listener listener = context.mock(Listener.class);
        final Statistic statistic = new Statistic(1, 1, "x");

        channel.subscribe(listener, toEverything());

        context.checking(new Expectations() {
            {
                oneOf(listener).statisticReady(statistic, 14);
            }
        });

        channel.statisticReady(statistic, 14);

        context.assertIsSatisfied();
    }

    private Selection toEverything() {
        return new Selection() {

            public boolean isSatisfiedBy(Statistic statistic, Object value) {
                return true;
            }

        };
    }

    @Test
    public void shouldNotConveyMessageToIrrelevantListeners() {
        final Channel channel = new Channel();
        final Listener listener = context.mock(Listener.class);
        final Statistic statistic = new Statistic(1, 1, "x");

        channel.subscribe(listener, toNothing());

        context.checking(new Expectations() {
            {
                never(listener).statisticReady(statistic, 14);
            }
        });

        channel.statisticReady(statistic, 14);

        context.assertIsSatisfied();
    }

    private Selection toNothing() {
        return new Selection() {

            public boolean isSatisfiedBy(Statistic statistic, Object value) {
                return false;
            }

        };
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullListeners() {
        Channel channel = new Channel();
        channel.subscribe(null, toEverything());
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullSelectors() {
        Channel channel = new Channel();

        channel.subscribe(dummyListener(), null);
    }

    private Listener dummyListener() {
        return new Listener() {

            public void statisticReady(Statistic statistic, Object value) {
                // Does nothing
            }
            
        };
    }

}
