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

/**
 * Provide default behaviour for listener
 */
public abstract class AbstractDataSetListener implements DataSetListener {

    public void enterDataSet(DataSet dataSet) {
        byDefault();
    }

    public void exitDataSet(DataSet dataSet) {
        byDefault();
    }

    public void enterTrace(Trace trace) {
        byDefault();
    }

    public void exitTrace(Trace trace) {
        byDefault();
    }

    public void enterState(State state) {
        byDefault();
    }

    public void exitState(State state) {
        byDefault();
    }

    protected void byDefault() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
