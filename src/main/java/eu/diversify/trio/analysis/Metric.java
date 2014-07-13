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
/**
 *
 * This file is part of TRIO.
 *
 * TRIO is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * TRIO is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO. If not, see <http://www.gnu.org/licenses/>.
 */
package eu.diversify.trio.analysis;

import eu.diversify.trio.data.AbstractDataSetListener;
import eu.diversify.trio.data.DataSet;
import eu.diversify.trio.data.State;
import eu.diversify.trio.data.Trace;
import java.util.HashMap;
import java.util.Map;

/**
 * A metric is an value computed on a data set
 */
public abstract class Metric extends AbstractDataSetListener {

    private final Map<String, Double> values;
    private String current;
    private int traceCounter;
    private int stateCounter;

    private final String name;
    private final String unit;

    public Metric(String name, String unit) {
        this.name = name;
        this.unit = unit;
        this.values = new HashMap<String, Double>();
    }

    public String name() {
        return this.name;
    }

    public String unit() {
        return this.unit;
    }

    @Override
    protected void byDefault() {
        // Nothing to do
    }

    protected String getTraceId() {
        return String.format("t#%d", traceCounter);
    }

    protected String getStateId() {
        return String.format("s#%d", stateCounter);
    }

    @Override
    public void enterDataSet(DataSet dataSet) {
        traceCounter = 0;
        stateCounter = 0;
        values.clear();
    }

    @Override
    public void enterState(State state) {
        stateCounter++;
    }

    @Override
    public void enterTrace(Trace trace) {
        traceCounter++;
    }

    protected void updateCurrent(String key, double value) {
        current = key;
        this.values.put(key, value);
    }

    public double value() {
        return values.get(current);
    }

    public Distribution distribution() {
        return new Distribution(values.values());
    }

}
