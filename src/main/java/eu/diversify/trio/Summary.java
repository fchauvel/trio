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

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Summarise a set of metrics of interest
 */
public class Summary {

    private final Map<Metric, Distribution> distributions;

    public Summary(DataSet data, Metric... metrics) {
        this.distributions = new HashMap<Metric, Distribution>();
        for (Metric eachMetric: metrics) {
            this.distributions.put(eachMetric, data.distributionOf(eachMetric));
        }
        
    }
    
    public void showOn(OutputStream output) {
        final PrintStream out = new PrintStream(output);
        out.printf("Summary:\r\n");
        out.printf("%20s %10s %10s %10s %10s %10s\r\n", "metric", "unit", "mean", "min", "max", "std. dev");
        for(Metric eachMetric: distributions.keySet()) {
            out.println(format(eachMetric, distributions.get(eachMetric)));
        }
    }

    private String format(Metric metric, Distribution values) {
        return String.format("%20s %10s %10.2f %10.2f %10.2f %10.2f", 
                             metric.getName(), 
                             metric.getUnit(), 
                             values.mean(), 
                             values.minimum(), 
                             values.maximum(), 
                             values.standardDeviation());
    }
    
}
