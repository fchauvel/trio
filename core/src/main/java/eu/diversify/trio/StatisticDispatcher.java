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
package eu.diversify.trio;

import eu.diversify.trio.analytics.events.Selection;
import eu.diversify.trio.analytics.events.Statistic;
import eu.diversify.trio.analytics.events.StatisticListener;
import java.util.ArrayList;
import java.util.List;

/**
 * A publish-subscribe communication channel
 */
public class StatisticDispatcher implements StatisticListener {

    private final List<Subscription> subscriptions;

    public StatisticDispatcher() {
        subscriptions = new ArrayList<Subscription>();
    }

    public void statisticReady(Statistic statistic, Object value) {
        for (Subscription eachSubscription : subscriptions) {
            if (eachSubscription.includes(statistic, value)) {
                eachSubscription.listener.statisticReady(statistic, value);
            }
        }
    }

    public void register(StatisticListener listener, Selection selection) {
        final Subscription newSubscription = new Subscription(checkListener(listener), checkSelection(selection));
        this.subscriptions.add(newSubscription);
    }
    
    private Selection checkSelection(Selection selection) {
        if (selection == null) {
            throw new NullPointerException("Invalid selection ('null' found)");
        }
        return selection;
    }
    
    private StatisticListener checkListener(StatisticListener listener) {
        if (listener == null) {
            throw new NullPointerException("Invalid listener ('null' found)");
        }
        return listener;
    }

    private static class Subscription {

        private final StatisticListener listener;
        private final Selection selection;

        public Subscription(StatisticListener listener, Selection selection) {
            this.listener = listener;
            this.selection = selection;
        }

        public boolean includes(Statistic statistic, Object value) {
            return selection.isSatisfiedBy(statistic, value);
        }

    }

}
