/**
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
 * This file is part of TRIO :: Core.
 *
 * TRIO :: Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Core.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
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
 * ====
 *     This file is part of TRIO :: Core.
 *
 *     TRIO :: Core is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     TRIO :: Core is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with TRIO :: Core.  If not, see <http://www.gnu.org/licenses/>.
 * ====
 *
 * ====
 *     This file is part of TRIO.
 *
 *     TRIO is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     TRIO is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with TRIO.  If not, see <http://www.gnu.org/licenses/>.
 * ====
 *
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
 * This file is part of TRIO :: Core.
 *
 * TRIO :: Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Core.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
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
package net.fchauvel.trio.ui;

import net.fchauvel.trio.Configuration;
import net.fchauvel.trio.analytics.events.IdleStatisticListener;
import net.fchauvel.trio.analytics.events.Statistic;
import net.fchauvel.trio.analytics.events.StatisticListener;
import net.fchauvel.trio.analytics.robustness.Robustness;
import net.fchauvel.trio.analytics.sensitivity.Sensitivity;
import net.fchauvel.trio.analytics.threats.Threat;
import net.fchauvel.trio.core.Assembly;
import net.fchauvel.trio.simulation.RandomSimulation;
import net.fchauvel.trio.simulation.Simulation;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class UI {

    private final PrintStream out;

    public UI(OutputStream out) {
        try {
            this.out = new PrintStream(out, true, "UTF-8");
        
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Unable to use UTF-8", ex);
        
        }
    }

    public void showOpening(final Configuration config) {
        out.println(config.version() + " -- " + config.description());
        out.println(config.copyright());
        out.println("Licensed under " + config.license());
        out.println();
    }

    public void showSimulation(final Assembly assembly, final RandomSimulation scenario) {
        out.println("ASSEMBLY: " + assembly.getName());
        out.println("SCENARIO: " + this.format(scenario));
    }

    private String format(Simulation scenario) {
        String observation = String.format("'%s' layer", scenario.observed().toString());
        if (observation.contains("*")) {
            observation = "system";
        }
        String control = String.format("'%s' layer", scenario.controlled().toString());
        if (control.contains("*")) {
            control = "system";
        }
        return String.format("Robustness of the %s to failure in the %s", observation, control);
    }

    public void showClosing() {
        out.println();
        out.println("That's all folks!");
    }

    public void showError(Exception ex) {
        out.println(ex);
    }

    public String showUsage() {
        final String EOL = java.lang.System.lineSeparator();
        return "Usage: trio [options] input.trio" + EOL
                + "where 'options' are:" + EOL
                + "  -o, --observe=TAG  the tag of the components whose activity shall be observed" + EOL
                + "  -c, --control=TAG  the tag of the components whose activity shall be controlled" + EOL
                + "  -r, --runs=INTEGER the number of sample for statistical evidence" + EOL
                + "  -t, --trace=FILE   the file where the generated data shall be stored" + EOL
                + "Example: trio -o result.csv --run=10000 system.trio";
    }

    public StatisticListener getListener() {

        return new IdleStatisticListener() {

            @Override
            public void onThreatRanking(Statistic context, List<Threat> indicator) {
                out.printf(" + Most threatening failure sequences: %n");

                for (Threat eachThreat : topN(indicator, 5)) {
                    out.printf("   - %7.4f -- %s %n", eachThreat.threatLevel(), eachThreat.failureSequence());
                }
            }

            @Override
            public void onSensitivityRanking(Statistic context, List<Sensitivity> indicator) {
                out.printf(" + Most sensitive components: %n");
                for (Sensitivity eachSensitivity : topN(indicator, 5)) {
                    out.printf("   - %7.2f -- %s %n", eachSensitivity.averageImpact(), eachSensitivity.component());
                }
            }

            @Override
            public void onRobustness(Statistic context, Robustness indicator) {
                out.printf("Robustness: %.4f %n", indicator.average());
            }


            private <T> List<T> topN(List<T> source, int n) {
                final ArrayList<T> selection = new ArrayList<T>();
                for (int i = 0; i < n && i < source.size(); i++) {
                    selection.add(source.get(i));
                }
                return selection;
            }
        };
    }

}
