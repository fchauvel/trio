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
import eu.diversify.trio.analytics.sensitivity.Sensitivity;
import eu.diversify.trio.analytics.sensitivity.SensitivityRanking;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

/**
 * Display the sensitivity ranking
 */
public class SensitivityRankingView implements Listener {
    
    private final PrintStream out;
    
    public SensitivityRankingView(OutputStream out, Channel statistics) {
        this.out = new PrintStream(out, true);
        statistics.subscribe(this, new Selection() {

            public boolean isSatisfiedBy(Statistic statistic, Object value) {
                return statistic.getName().equals(SensitivityRanking.KEY_SENSITIVITY_RANKING);
            }
        });
    }

    public void statisticReady(Statistic statistic, Object value) {
        final List<Sensitivity> sensitivities = (List<Sensitivity>) value;
        out.printf(" + Most sensitive components: \n");
        
        int counter = 0;
        for (Sensitivity eachSensitivity: sensitivities) {
            counter ++;
            out.printf("    - %7.2f -- %s \n", eachSensitivity.averageImpact(), eachSensitivity.component());
            if (counter >= TOP_N) { break; }
        }
     }
    
    private static final int TOP_N = 5;
    
}
