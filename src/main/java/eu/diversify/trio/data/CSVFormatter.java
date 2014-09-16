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

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * Format a data set into a CSV file
 */
public class CSVFormatter extends AbstractDataSetListener {

    private final PrintStream out;
    private int traceCounter;

    public CSVFormatter(OutputStream output) {
        try {
            out = new PrintStream(output, true, "UTF-8");
        
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Unable to write UTF-8", ex);
        
        } 
    }

    @Override
    protected void byDefault() throws UnsupportedOperationException {
        // Do nothing
    }

    @Override
    public void enterDataSet(DataSet dataSet) {
        traceCounter = 0;
        out.print("sequence" + FIELD_SEPARATOR);
        out.print("action" + FIELD_SEPARATOR);
        out.print("disruption" + FIELD_SEPARATOR);
        out.print("activity" + FIELD_SEPARATOR);
        out.println("loss");
    }

    @Override
    public void enterTrace(Trace trace) {
        traceCounter++;
    }

    @Override
    public void enterState(State state) {
        out.print(traceCounter + FIELD_SEPARATOR);
        out.print(state.getTrigger().toString() + FIELD_SEPARATOR);
        out.print(state.getDisruptionLevel() + FIELD_SEPARATOR);
        out.print(state.getObservedActivityLevel() + FIELD_SEPARATOR);
        out.println(state.getLoss());
    }

    private static final String FIELD_SEPARATOR = ",";

}
