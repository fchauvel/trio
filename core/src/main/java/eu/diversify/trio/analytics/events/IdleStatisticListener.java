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

package eu.diversify.trio.analytics.events;

import eu.diversify.trio.analytics.robustness.FailureSequence;
import eu.diversify.trio.analytics.robustness.Robustness;
import eu.diversify.trio.analytics.sensitivity.Sensitivity;
import eu.diversify.trio.analytics.threats.Threat;
import java.util.List;

/**
 * The default statistic listener, which does nothing.
 */
public class IdleStatisticListener implements StatisticListener {

    public void onFailureSequence(Statistic context, FailureSequence sequence) {
    }

    public void onRobustness(Statistic context, Robustness indicator) {
    }

    public void onSensitivityRanking(Statistic context, List<Sensitivity> indicator) {
    }

    public void onThreatRanking(Statistic context, List<Threat> indicator) {
    }
    
}
