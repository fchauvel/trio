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
import java.util.ArrayList;
import java.util.List;

/**
 * Statistics about the loss 
 */
public class Loss extends Metric {

    private final List<Double> values;
    private double loss;
    
    public Loss() {
        super("Loss", "# components");
        values = new ArrayList<Double>();
    }

    @Override
    protected void byDefault() {}

    @Override
    public void exitState(State state) {
        loss = state.getLoss();
        values.add(loss);
    }

    @Override
    public void enterState(State state) {
        loss = 0;
    }

    @Override
    public void enterDataSet(DataSet dataSet) {
        values.clear();
    }
    
    @Override
    public void exitDataSet(DataSet dataSet) {
        
    }
       
    @Override
    public double value() {
        return loss;
    }

    @Override
    public Distribution distribution() {
        return new Distribution(values);
    }

    
    
}
