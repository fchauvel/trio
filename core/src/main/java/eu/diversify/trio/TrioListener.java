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

import eu.diversify.trio.analytics.robustness.Robustness;
import eu.diversify.trio.analytics.sensitivity.Sensitivity;
import eu.diversify.trio.analytics.threats.Threat;
import java.util.List;

/**
 * Handle the publication of robustness indicators
 */
public interface TrioListener {
    
    /**
     * Triggered when the overall robustness is available
     * @param indicator the overall robustness
     */
    void onRobustness(Robustness indicator);
    
    /**
     * Triggered when the ranking of all components is available
     * @param indicator the ranking of component according to their sensitivity
     */
    void onSensitivityRanking(List<Sensitivity> indicator);
    
    /**
     * Triggered when the threat ranking is published
     * @param indicator the ranking of all possible threats
     */
    void onThreatRanking(List<Threat> indicator);

}
