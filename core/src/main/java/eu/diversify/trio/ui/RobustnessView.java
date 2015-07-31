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

package eu.diversify.trio.ui;

import eu.diversify.trio.analytics.events.Channel;
import eu.diversify.trio.analytics.events.Listener;
import eu.diversify.trio.analytics.events.Selection;
import eu.diversify.trio.analytics.events.Statistic;
import eu.diversify.trio.analytics.robustness.Robustness;
import eu.diversify.trio.analytics.robustness.RobustnessAggregator;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Display the robustness when ready
 */
public class RobustnessView implements Listener {
    
    private final PrintStream out;
    
    public RobustnessView(OutputStream out, Channel statistics) {
        this.out = new PrintStream(out, true);
        statistics.subscribe(this, new Selection() {

            public boolean isSatisfiedBy(Statistic statistic, Object value) {
                return statistic.getName().equals(RobustnessAggregator.KEY_ROBUSTNESS);
            }
            
        });
    }

    public void statisticReady(Statistic statistic, Object value) {
        Robustness robustness = (Robustness) value;
        out.printf("Robustness: %.4f \n",  robustness.average());
    }
    
    
    
}
