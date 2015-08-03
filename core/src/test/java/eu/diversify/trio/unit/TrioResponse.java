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

package eu.diversify.trio.unit;

import eu.diversify.trio.TrioListener;
import eu.diversify.trio.analytics.robustness.Robustness;
import eu.diversify.trio.analytics.sensitivity.Sensitivity;
import eu.diversify.trio.analytics.threats.Threat;
import java.util.List;

/**
 * Capture the responses sent by Trio
 */
public class TrioResponse implements TrioListener {
    private Robustness robustness;
    private List<Sensitivity> sensitivities;
    private List<Threat> threats;
    
    public TrioResponse() {
        robustness = null;
        sensitivities = null;
        threats = null;
    }

    public void onRobustness(Robustness indicator) {
        this.robustness = indicator;
    }

    public void onSensitivityRanking(List<Sensitivity> indicator) {
        this.sensitivities = indicator;
    }

    public void onThreatRanking(List<Threat> indicator) {
        this.threats = indicator;
    }
  
    
    public double robustness() {
        assert robustness != null : "Error, no robustness yet";
        return robustness.average();
    }

    public List<Sensitivity> sensitivities() {
        return sensitivities;
    }

    public List<Threat> threats() {
        return threats;
    }
    
}
