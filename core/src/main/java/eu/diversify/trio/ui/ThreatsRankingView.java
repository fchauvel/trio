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
import eu.diversify.trio.analytics.threats.Threat;
import eu.diversify.trio.analytics.threats.ThreatRanking;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

/**
 * Display the most threatening failure sequences
 */
public class ThreatsRankingView implements Listener {

    private final PrintStream out;
    
    public ThreatsRankingView(OutputStream out, Channel statistics) {
        this.out = new PrintStream(out, true);
        statistics.subscribe(this, new Selection() {

            public boolean isSatisfiedBy(Statistic statistic, Object value) {
                return statistic.getName().equals(ThreatRanking.KEY_THREAT_RANKING);
            }
            
        });
    }

    public void statisticReady(Statistic statistic, Object value) {
        final List<Threat> threats = (List<Threat>) value;
        out.printf(" + Most threatening failure sequences: \n");
        
        int counter = 0;
        for(Threat eachThreat: threats) {
            counter ++;
            out.printf("   - %7.4f -- %s%n", eachThreat.threatLevel(), eachThreat.failureSequence());
            if (counter >= 5) { break; }
        }
    
    }
    
    
    
    
}
