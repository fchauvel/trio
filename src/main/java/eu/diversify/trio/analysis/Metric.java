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
import eu.diversify.trio.data.State;
import eu.diversify.trio.data.Trace;
import java.util.HashMap;

/**
 * A metric is an value computed on a data set
 */
public abstract class Metric extends AbstractDataSetListener {

    private final Distribution distribution;

    private final String name;
    private final String unit;

    public Metric(String name, String unit) {
        this.name = name;
        this.unit = unit;
        this.distribution = new Distribution(name);
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

    @Override
    public void enterDataSet(DataSet dataSet) {
        distribution.clear(); 
    }

    public double valueOf(String key) {
        return Distribution.mean.of(distribution.valuesOf(key));
    }

    public Distribution distribution() {
        return distribution;
    }

}
