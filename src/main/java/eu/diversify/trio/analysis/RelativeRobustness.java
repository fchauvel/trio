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

import eu.diversify.trio.data.DataSet;
import eu.diversify.trio.data.State;
import eu.diversify.trio.data.Trace;
import java.util.ArrayList;
import java.util.List;

/**
 * Compute a relative robustness
 */
public class RelativeRobustness extends Metric {

    private double value;
    private final List<Double> values;
    private final Robustness robustness;
    
    public RelativeRobustness(Robustness robustness) {
        super("relative robustness", "%");
        this.robustness = robustness;
        this.values = new ArrayList<Double>();
    }

    @Override
    protected void byDefault() throws UnsupportedOperationException {
    }
    
    
    @Override
    public void exitTrace(Trace trace) {
        final double min = trace.getObservationCapacity();
        final double max = trace.getObservationCapacity() * trace.getControlCapacity();
        value = (robustness.value() - min) / (max - min);
        values.add(value);
    }

    @Override
    public void enterTrace(Trace trace) {
        value = 0;
    }

    @Override
    public void exitDataSet(DataSet dataSet) {
        
    }

    @Override
    public void enterDataSet(DataSet dataSet) {
        super.enterDataSet(dataSet); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public double value() {
        return value;
    }

    @Override
    public Distribution distribution() {
        return new Distribution(values);
    }

    
    
}
