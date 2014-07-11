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
package eu.diversify.trio.data;

import eu.diversify.trio.codecs.DataFormat;
import eu.diversify.trio.data.Trace;
import eu.diversify.trio.analysis.Distribution;
import eu.diversify.trio.analysis.Metric;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Aggregate information obtained during extinction sequences
 */
public class DataSet {

    private final List<Trace> traces;

    public DataSet() {
        this.traces = new ArrayList<Trace>();
    }
    
    public <T> T accept(DataSetVisitor<T> visitor) {
        return visitor.visitDataSet(this);
    }

    public void include(Trace trace) {
        this.traces.add(trace);
    }

    public Trace get(int index) {
        return traces.get(0);
    }

    public Distribution distributionOf(Metric metric) {
        final List<Double> values = new ArrayList<Double>(traces.size());
        for (Trace eachTrace: traces) {
            values.add(metric.computeOn(eachTrace));
        }
        return new Distribution(values);
    }

    public void saveAs(DataFormat format, String output) {
        FileWriter out = null;
        try {
            out = new FileWriter(output);
            int counter = 0;
            for (Trace eachTrace: traces) {
                counter++;
                out.write(eachTrace.to(format, counter));
            }
        } catch (IOException ex) {
            final String error = String.format("Error: Unexpected I/O error while riting to '%s' (%s).", output, ex.getMessage());
            throw new RuntimeException(error);

        } finally {
            try {
                if (out != null) {
                    out.close();
                }

            } catch (IOException ex) {
                final String error = String.format("Error: Unable to close '%s' (%s).", output, ex.getMessage());
                throw new RuntimeException(error);
            }
        }
    }

}
