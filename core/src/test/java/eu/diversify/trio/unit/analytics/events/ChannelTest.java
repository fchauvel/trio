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

import eu.diversify.trio.StatisticDispatcher;
import eu.diversify.trio.analytics.events.StatisticListener;
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
        final StatisticDispatcher channel = new StatisticDispatcher();
        final StatisticListener listener = context.mock(StatisticListener.class);
        final Statistic statistic = new Statistic(1, 1, "x");

        channel.register(listener, toEverything());

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
        final StatisticDispatcher channel = new StatisticDispatcher();
        final StatisticListener listener = context.mock(StatisticListener.class);
        final Statistic statistic = new Statistic(1, 1, "x");

        channel.register(listener, toNothing());

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
        StatisticDispatcher channel = new StatisticDispatcher();
        channel.register(null, toEverything());
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullSelectors() {
        StatisticDispatcher channel = new StatisticDispatcher();

        channel.register(dummyListener(), null);
    }

    private StatisticListener dummyListener() {
        return new StatisticListener() {

            public void statisticReady(Statistic statistic, Object value) {
                // Does nothing
            }
            
        };
    }

}
