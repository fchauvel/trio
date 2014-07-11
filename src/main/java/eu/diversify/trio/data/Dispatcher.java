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
package eu.diversify.trio.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Dispatch event coming from the data set to other listener
 */
public final class Dispatcher extends AbstractDataSetListener {

    private final Collection<DataSetListener> listeners;

    public Dispatcher(DataSetListener... listeners) {
        this(Arrays.asList(listeners));
    }

    public Dispatcher(Collection<DataSetListener> listeners) {
        this.listeners = new ArrayList<DataSetListener>(listeners);
    }

    @Override
    public void exitState(State state) {
        for (DataSetListener eachListener: listeners) {
            eachListener.exitState(state);
        }
    }

    @Override
    public void enterState(State state) {
        for (DataSetListener eachListener: listeners) {
            eachListener.enterState(state);
        }
    }

    @Override
    public void exitTrace(Trace trace) {
        for (DataSetListener eachListener: listeners) {
            eachListener.exitTrace(trace);
        }
    }

    @Override
    public void enterTrace(Trace trace) {
        for (DataSetListener eachListener: listeners) {
            eachListener.enterTrace(trace);
        }
    }

    @Override
    public void exitDataSet(DataSet dataSet) {
        for (DataSetListener eachListener: listeners) {
            eachListener.exitDataSet(dataSet);
        }
    }

    @Override
    public void enterDataSet(DataSet dataSet) {
        for (DataSetListener eachListener: listeners) {
            eachListener.enterDataSet(dataSet);
        }
    }

}
