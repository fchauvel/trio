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

import eu.diversify.trio.data.AbstractDataSetListener;
import eu.diversify.trio.data.DataSet;
import eu.diversify.trio.data.State;
import eu.diversify.trio.data.Trace;
import java.util.ArrayList;
import java.util.List;

/**
 * Compute the robustness of
 */
public class Robustness extends Metric {

    private final List<Double> robustnesses;
    private double robustness;
    
    public Robustness() {
        super("Robustness", "none");
        this.robustnesses = new ArrayList<Double>();
    }

    @Override
    protected void byDefault() throws UnsupportedOperationException {
        // Nothing to do
    }

    @Override
    public void enterDataSet(DataSet dataSet) {
        this.robustnesses.clear();
    }
    
    @Override
    public void exitTrace(Trace trace) {
        final List<Integer> disruptions = trace.disruptionLevels();
        for (int i = 1; i < disruptions.size(); i++) {
            final State previous = trace.afterDisruption(i - 1);
            final State current = trace.afterDisruption(i);
            int step = current.getDisruptionLevel() - previous.getDisruptionLevel();
            robustness += step * previous.getActivityLevel();
        }
        robustnesses.add(robustness);
    }

    @Override
    public void enterTrace(Trace trace) {
        this.robustness = 0;
    }

    
    public double value() {
        return robustness;
    }
    
    public Distribution distribution() {
        return new Distribution(robustnesses);
    }

}
