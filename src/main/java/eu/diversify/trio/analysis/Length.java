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
/*
 */

package eu.diversify.trio.analysis;

import eu.diversify.trio.data.DataSet;
import eu.diversify.trio.data.State;
import eu.diversify.trio.data.Trace;
import java.util.ArrayList;
import java.util.List;

/**
 * Compute statistics about the length of sequences
 */
public class Length extends Metric {

    private double length;
    private final List<Double> values;
    
    public Length() {
        super("Length", "# actions");
        values = new ArrayList<Double>();
    }

    @Override
    public double value() {
        return length;
    }

    @Override
    public Distribution distribution() {
        return new Distribution(values);
    }

    @Override
    protected void byDefault() throws UnsupportedOperationException {
    }

    @Override
    public void exitState(State state) {
        values.add(length);
    }

    @Override
    public void enterState(State state) {
        length += 1;
    }

    @Override
    public void exitTrace(Trace trace) {
        super.exitTrace(trace); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void enterTrace(Trace trace) {
        length = 0;
    }

    @Override
    public void enterDataSet(DataSet dataSet) {
        values.clear();
    }

    
    
    
    
}
