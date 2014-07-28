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

import java.io.FileWriter;
import java.io.IOException;
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
       
    public void accept(DataSetListener listener) {
        listener.enterDataSet(this);
        for(Trace eachTrace: traces) {
            eachTrace.accept(listener);
        }
        listener.exitDataSet(this);
    }

    public void include(Trace trace) {
        this.traces.add(trace);
    }

    public Trace get(int index) {
        return traces.get(0);
    }

}
