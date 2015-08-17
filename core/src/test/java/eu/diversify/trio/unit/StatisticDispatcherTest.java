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
/**
 *
 * This file is part of TRIO.
 *
 * TRIO is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * TRIO is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO. If not, see <http://www.gnu.org/licenses/>.
 */
package eu.diversify.trio.unit;

import eu.diversify.trio.StatisticDispatcher;
import eu.diversify.trio.analytics.events.StatisticListener;
import eu.diversify.trio.analytics.events.Statistic;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

/**
 * Specification of the Channel
 */
public class StatisticDispatcherTest {

    private final Mockery context = new Mockery();

    @Test
    public void shouldDispatchOnFailureSequence() {
        final StatisticDispatcher channel = new StatisticDispatcher();
        final StatisticListener listener = context.mock(StatisticListener.class);
        final Statistic statistic = new Statistic(1, 1, "x");

        channel.register(listener);

        context.checking(new Expectations() {
            {
                oneOf(listener).onFailureSequence(statistic, null);
            }
        });

        channel.onFailureSequence(statistic, null);

        context.assertIsSatisfied();
    }

    @Test
    public void shouldDispatchOnRobustness() {
        final StatisticDispatcher channel = new StatisticDispatcher();
        final StatisticListener listener = context.mock(StatisticListener.class);
        final Statistic statistic = new Statistic(1, 1, "x");

        channel.register(listener);

        context.checking(new Expectations() {
            {
                oneOf(listener).onRobustness(statistic, null);
            }
        });

        channel.onRobustness(statistic, null);

        context.assertIsSatisfied();
    }

    @Test
    public void shouldDispatchOnThreats() {
        final StatisticDispatcher channel = new StatisticDispatcher();
        final StatisticListener listener = context.mock(StatisticListener.class);
        final Statistic statistic = new Statistic(1, 1, "x");

        channel.register(listener);

        context.checking(new Expectations() {
            {
                oneOf(listener).onThreatRanking(statistic, null);
            }
        });

        channel.onThreatRanking(statistic, null);

        context.assertIsSatisfied();
    }

    @Test
    public void shouldDispatchOnSensitivityRanking() {
        final StatisticDispatcher channel = new StatisticDispatcher();
        final StatisticListener listener = context.mock(StatisticListener.class);
        final Statistic statistic = new Statistic(1, 1, "x");

        channel.register(listener);

        context.checking(new Expectations() {
            {
                oneOf(listener).onSensitivityRanking(statistic, null);
            }
        });

        channel.onSensitivityRanking(statistic, null);

        context.assertIsSatisfied();
    }

    @Test(expected = NullPointerException.class)
    public void shouldRejectNullListeners() {
        StatisticDispatcher channel = new StatisticDispatcher();
        channel.register(null);
    }

}
