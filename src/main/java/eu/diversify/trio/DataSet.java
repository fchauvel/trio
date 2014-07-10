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

import java.util.ArrayList;
import java.util.List;

/**
 * Aggregate information obtained during extinction sequences
 */
public class DataSet {
        
    private final List<Trace> traces;
   
    public DataSet() {
        this.traces = new ArrayList<Trace>();
    }
  
    public void include(Trace trace) {
        this.traces.add(trace);
    }
    
    public Trace get(int index) {
        return traces.get(0);
    }
    
    public Distribution distributionOf(Metric metric) {
        final List<Double> values = new ArrayList<Double>(traces.size());
        for(Trace eachTrace: traces) {
            values.add(metric.computeOn(eachTrace));
        }
        return new Distribution(values);
    }
    
    public void saveAs(DataFormat format) {
        
    }

}
