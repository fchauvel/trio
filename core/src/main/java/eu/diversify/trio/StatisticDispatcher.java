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

import eu.diversify.trio.analytics.events.Statistic;
import eu.diversify.trio.analytics.events.StatisticListener;
import java.util.ArrayList;
import java.util.List;

/**
 * A publish-subscribe communication channel
 */
public class StatisticDispatcher implements StatisticListener {

    private final List<StatisticListener> subscriptions;

    public StatisticDispatcher() {
        subscriptions = new ArrayList<StatisticListener>();
    }

    public void statisticReady(Statistic statistic, Object value) {
        for (StatisticListener eachListener : subscriptions) {
            if (eachListener.accept(statistic)) {
                eachListener.statisticReady(statistic, value);
            }
        }
    }

    public void register(StatisticListener listener) {
        this.subscriptions.add(checkListener(listener));
    }

    
    private StatisticListener checkListener(StatisticListener listener) {
        if (listener == null) {
            throw new NullPointerException("Invalid listener ('null' found)");
        }
        return listener;
    }

    public boolean accept(Statistic statistic) {
        return true;
    }


}
