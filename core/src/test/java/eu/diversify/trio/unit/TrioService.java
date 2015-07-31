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

import eu.diversify.trio.Trio;
import eu.diversify.trio.analytics.events.Listener;
import eu.diversify.trio.analytics.events.Selection;
import eu.diversify.trio.analytics.events.Statistic;
import eu.diversify.trio.analytics.robustness.Robustness;
import eu.diversify.trio.analytics.robustness.RobustnessAggregator;
import eu.diversify.trio.analytics.sensitivity.Sensitivity;
import eu.diversify.trio.analytics.sensitivity.SensitivityRanking;
import eu.diversify.trio.analytics.threats.Threat;
import eu.diversify.trio.analytics.threats.ThreatRanking;
import eu.diversify.trio.simulation.Scenario;
import java.util.List;

/**
 * provide a synchronous interface for Trio that eases testing
 */
public class TrioService {

    private final Trio trio;
    private final TrioResponse response;

    public TrioService() {
        this.trio = new Trio();
        this.response = new TrioResponse();
    }

    public TrioResponse run(Scenario scenario, int runCount) {
        trio.analyses().subscribe(listener(), keyStatistics());
        trio.run(scenario, runCount);
        return response;
    }

    private Selection keyStatistics() {
        return new Selection() {

            public boolean isSatisfiedBy(Statistic statistic, Object value) {
                return statistic.getName().equals(SensitivityRanking.KEY_SENSITIVITY_RANKING)
                        || statistic.getName().equals(ThreatRanking.KEY_THREAT_RANKING)
                        || statistic.getName().equals(RobustnessAggregator.KEY_ROBUSTNESS);
            }
        };
    }

    private Listener listener() {
        return new Listener() {

            public void statisticReady(Statistic statistic, Object value) {
                if (statistic.getName().equals(SensitivityRanking.KEY_SENSITIVITY_RANKING)) {
                    response.sensitivities = (List<Sensitivity>) value;

                } else if (statistic.getName().equals(ThreatRanking.KEY_THREAT_RANKING)) {
                    response.threats = (List<Threat>) value;

                } else if (statistic.getName().equals(RobustnessAggregator.KEY_ROBUSTNESS)) {
                    response.robustness = (Robustness) value;

                } else {
                }
            }
        };
    }

    public static class TrioResponse {

        Robustness robustness;
        List<Sensitivity> sensitivities;
        List<Threat> threats;

        public TrioResponse() {
            robustness = null;
            sensitivities = null;
            threats = null;
        }

        public double robustness() {
            assert robustness != null: "Error, no robustness yet";
            return robustness.average();
        }

        public List<Sensitivity> sensitivities() {
            return sensitivities;
        }

        public List<Threat> threats() {
            return threats;
        }

    }

}
