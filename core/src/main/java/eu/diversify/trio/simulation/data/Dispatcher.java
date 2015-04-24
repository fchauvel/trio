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
package eu.diversify.trio.simulation.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Dispatch event coming from the data set to other listener
 */
public class Dispatcher extends AbstractDataSetListener {

    private final List<DataSetListener> listeners;

    public Dispatcher(DataSetListener... listeners) {
        this(Arrays.asList(listeners));
    }

    public Dispatcher(List<DataSetListener> listeners) {
        this.listeners = new ArrayList<DataSetListener>(listeners);
    }
    
    public final List<DataSetListener> getListeners() {
        return Collections.unmodifiableList(listeners); 
    }

    @Override
    public final void exitState(State state) {
        for (DataSetListener eachListener: listeners) {
            eachListener.exitState(state);
        }
    }

    @Override
    public final void enterState(State state) {
        for (DataSetListener eachListener: listeners) {
            eachListener.enterState(state);
        }
    }

    @Override
    public final void exitTrace(Trace trace) {
        for (DataSetListener eachListener: listeners) {
            eachListener.exitTrace(trace);
        }
    }

    @Override
    public final void enterTrace(Trace trace) {
        for (DataSetListener eachListener: listeners) {
            eachListener.enterTrace(trace);
        }
    }

    @Override
    public final void exitDataSet(DataSet dataSet) {
        for (DataSetListener eachListener: listeners) {
            eachListener.exitDataSet(dataSet);
        }
    }

    @Override
    public final void enterDataSet(DataSet dataSet) {
        for (DataSetListener eachListener: listeners) {
            eachListener.enterDataSet(dataSet);
        }
    }

}
