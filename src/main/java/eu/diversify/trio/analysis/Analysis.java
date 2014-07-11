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


package eu.diversify.trio.analysis;

import eu.diversify.trio.data.AbstractDataSetListener;
import eu.diversify.trio.data.DataSet;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Summarise a set of metrics of interest
 */
public class Analysis extends AbstractDataSetListener {

    private final Probability probability;
    private final Map<Metric, Distribution> distributions;

    public Analysis(DataSet data, Metric... metrics) {
        this.probability = new Probability();
        this.distributions = new HashMap<Metric, Distribution>();
        for (Metric eachMetric: metrics) {
            this.distributions.put(eachMetric, data.distributionOf(eachMetric));
        }
    } 
     
    public void showOn(OutputStream output) {
        final PrintStream out = new PrintStream(output);
        out.println("SEQUENCES SUMMARY:");
        out.println();
        out.printf("%20s %10s %10s %10s %10s\r\n", "metric (unit)", "mean", "min", "max", "std. dev");
        out.println("-----------------------------------------------------------------");
        for(Metric eachMetric: distributions.keySet()) {
            out.println(format(eachMetric, distributions.get(eachMetric)));
        }
    }

    private String format(Metric metric, Distribution values) {
        return String.format("%20s %10.2f %10.2f %10.2f %10.2f", 
                             String.format("%s (%s)", metric.getName(), metric.getUnit()), 
                             values.mean(), 
                             values.minimum(), 
                             values.maximum(), 
                             values.standardDeviation());
    }
    
}
