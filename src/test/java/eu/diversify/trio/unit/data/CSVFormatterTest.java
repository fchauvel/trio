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
package eu.diversify.trio.unit.data;

import eu.diversify.trio.data.CSVFormatter;
import eu.diversify.trio.data.DataSet;
import eu.diversify.trio.data.State;
import eu.diversify.trio.data.Trace;
import java.io.ByteArrayOutputStream;

import static eu.diversify.trio.simulation.actions.AbstractAction.*;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 *
 */
@RunWith(JUnit4.class)
public class CSVFormatterTest extends TestCase {

    private static final String EOL = System.lineSeparator();
    private final CSVFormatter formatter;
    private final ByteArrayOutputStream csv;

    public CSVFormatterTest() {
        csv = new ByteArrayOutputStream();
        formatter = new CSVFormatter(csv);
    }

    @Test
    public void stateShouldBeProperlyFormatted() {
        final State state = new State(inactivate("X"), 1, 5, 3, 1);

        state.accept(formatter);

        final String expectation = "0,inactivate X,1,5,1" + EOL;

        assertThat(csv.toString(), is(equalTo(expectation)));
    }

    @Test
    public void traceShouldBeProperlyFormatted() {
        final Trace trace = new Trace(10, 5);
        trace.record(inactivate("A"), 9, 4);
        trace.record(inactivate("B"), 4, 3);
        trace.record(inactivate("C"), 3, 2);
        trace.record(inactivate("D"), 0, 1);

        trace.accept(formatter);

        final String expectation
                = "1,none,0,10,0" + EOL
                + "1,inactivate A,1,9,1" + EOL
                + "1,inactivate B,2,4,5" + EOL
                + "1,inactivate C,3,3,1" + EOL
                + "1,inactivate D,4,0,3" + EOL;

        assertThat(csv.toString(), is(equalTo(expectation)));
    }

    @Test
    public void shouldFormatDataSetCorrectly() {
        final DataSet dataset = new DataSet();
        final Trace trace = new Trace(10, 5);
        trace.record(inactivate("A"), 9, 4);
        trace.record(inactivate("B"), 4, 3);
        trace.record(inactivate("C"), 3, 2);
        trace.record(inactivate("D"), 0, 1);
        dataset.include(trace);
        dataset.include(trace);

        dataset.accept(formatter);

        final String expectation
                = "sequence,action,disruption,activity,loss" + EOL
                + "1,none,0,10,0" + EOL
                + "1,inactivate A,1,9,1" + EOL
                + "1,inactivate B,2,4,5" + EOL
                + "1,inactivate C,3,3,1" + EOL
                + "1,inactivate D,4,0,3" + EOL
                + "2,none,0,10,0" + EOL
                + "2,inactivate A,1,9,1" + EOL
                + "2,inactivate B,2,4,5" + EOL
                + "2,inactivate C,3,3,1" + EOL
                + "2,inactivate D,4,0,3" + EOL;

        assertThat(csv.toString(), is(equalTo(expectation)));
    }

}
