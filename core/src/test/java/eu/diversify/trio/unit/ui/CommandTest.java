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
package eu.diversify.trio.unit.ui;

import eu.diversify.trio.simulation.Scenario;
import eu.diversify.trio.Trio;
import eu.diversify.trio.core.Component;
import eu.diversify.trio.simulation.data.DataSet;
import eu.diversify.trio.simulation.data.Trace;
import eu.diversify.trio.simulation.actions.AbstractAction;
import eu.diversify.trio.ui.Command;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Specification of the Command object
 */
@RunWith(JUnit4.class)
public class CommandTest extends TestCase {

    @Test
    public void shouldProvideDefaultValuesForAllOptions() throws IOException {
        final String commandLine = "myfile.trio";
        Command command = Command.from(commandLine);

        final TrioSpy spy = new TrioSpy();
        command.execute(spy, new NullOutputStream());

        assertThat(spy.inputFile, is(equalTo("myfile.trio")));
        assertThat(spy.traceFile, is(equalTo(Command.DEFAULT_OUTPUT_FILE)));
        assertThat(spy.runCount, is(equalTo(Command.DEFAULT_RUN_COUNT)));
        assertThat(spy.observation, is(equalTo(Command.DEFAULT_OBSERVATION)));
        assertThat(spy.control, is(equalTo(Command.DEFAULT_CONTROL)));
    }

    @Test
    public void shouldAcceptShortVersionOfTraceFile() throws IOException {
        final String commandLine = "-t out.csv myfile.trio";
        Command command = Command.from(commandLine);

        final TrioSpy spy = new TrioSpy();
        command.execute(spy, new NullOutputStream());

        assertThat(spy.inputFile, is(equalTo("myfile.trio")));
        assertThat(spy.traceFile, is(equalTo("out.csv")));
        assertThat(spy.runCount, is(equalTo(Command.DEFAULT_RUN_COUNT)));
        assertThat(spy.observation, is(equalTo(Command.DEFAULT_OBSERVATION)));
        assertThat(spy.control, is(equalTo(Command.DEFAULT_CONTROL)));
    }

    @Test
    public void shouldAcceptLongVersionTraceFile() throws IOException {
        final String commandLine = "--trace=out.csv myfile.trio";
        Command command = Command.from(commandLine);

        final TrioSpy spy = new TrioSpy();
        command.execute(spy, new NullOutputStream());

        assertThat(spy.inputFile, is(equalTo("myfile.trio")));
        assertThat(spy.traceFile, is(equalTo("out.csv")));
        assertThat(spy.runCount, is(equalTo(Command.DEFAULT_RUN_COUNT)));
        assertThat(spy.observation, is(equalTo(Command.DEFAULT_OBSERVATION)));
        assertThat(spy.control, is(equalTo(Command.DEFAULT_CONTROL)));
    }

    @Test
    public void shouldAcceptShortVersionRunCount() throws IOException {
        final String commandLine = "-r 1234 myfile.trio";
        Command command = Command.from(commandLine);

        final TrioSpy spy = new TrioSpy();
        command.execute(spy, new NullOutputStream());

        assertThat(spy.inputFile, is(equalTo("myfile.trio")));
        assertThat(spy.traceFile, is(equalTo(Command.DEFAULT_OUTPUT_FILE)));
        assertThat(spy.runCount, is(equalTo(1234)));
        assertThat(spy.observation, is(equalTo(Command.DEFAULT_OBSERVATION)));
        assertThat(spy.control, is(equalTo(Command.DEFAULT_CONTROL)));
    }

    @Test
    public void shouldAcceptLongVersionRunCount() throws IOException {
        final String commandLine = "--runs=1234 myfile.trio";
        Command command = Command.from(commandLine);

        final TrioSpy spy = new TrioSpy();
        command.execute(spy, new NullOutputStream());

        assertThat(spy.inputFile, is(equalTo("myfile.trio")));
        assertThat(spy.traceFile, is(equalTo(Command.DEFAULT_OUTPUT_FILE)));
        assertThat(spy.runCount, is(equalTo(1234)));
        assertThat(spy.observation, is(equalTo(Command.DEFAULT_OBSERVATION)));
        assertThat(spy.control, is(equalTo(Command.DEFAULT_CONTROL)));
    }

    @Test
    public void shouldAcceptShortVersionForObserve() throws IOException {
        final String commandLine = "-o platform myfile.trio";
        Command command = Command.from(commandLine);

        final TrioSpy spy = new TrioSpy();
        command.execute(spy, new NullOutputStream());

        assertThat(spy.inputFile, is(equalTo("myfile.trio")));
        assertThat(spy.traceFile, is(equalTo(Command.DEFAULT_OUTPUT_FILE)));
        assertThat(spy.runCount, is(equalTo(Command.DEFAULT_RUN_COUNT)));
        assertThat(spy.observation, is(equalTo("platform")));
        assertThat(spy.control, is(equalTo(Command.DEFAULT_CONTROL)));
    }

    @Test
    public void shouldAcceptLongVersionObserve() throws IOException {
        final String commandLine = "--observe=platform myfile.trio";
        Command command = Command.from(commandLine);

        final TrioSpy spy = new TrioSpy();
        command.execute(spy, new NullOutputStream());

        assertThat(spy.inputFile, is(equalTo("myfile.trio")));
        assertThat(spy.traceFile, is(equalTo(Command.DEFAULT_OUTPUT_FILE)));
        assertThat(spy.runCount, is(equalTo(Command.DEFAULT_RUN_COUNT)));
        assertThat(spy.observation, is(equalTo("platform")));
        assertThat(spy.control, is(equalTo(Command.DEFAULT_CONTROL)));
    }

    @Test
    public void shouldAcceptShortVersionForControl() throws IOException {
        final String commandLine = "-c infra myfile.trio";
        Command command = Command.from(commandLine);

        final TrioSpy spy = new TrioSpy();
        command.execute(spy, new NullOutputStream());

        assertThat(spy.inputFile, is(equalTo("myfile.trio")));
        assertThat(spy.traceFile, is(equalTo(Command.DEFAULT_OUTPUT_FILE)));
        assertThat(spy.runCount, is(equalTo(Command.DEFAULT_RUN_COUNT)));
        assertThat(spy.observation, is(equalTo(Command.DEFAULT_OBSERVATION)));
        assertThat(spy.control, is(equalTo("infra")));
    }

    @Test
    public void shouldAcceptLongVersionForControl() throws IOException {
        final String commandLine = "--control=infra myfile.trio";
        Command command = Command.from(commandLine);

        final TrioSpy spy = new TrioSpy();
        command.execute(spy, new NullOutputStream());

        assertThat(spy.inputFile, is(equalTo("myfile.trio")));
        assertThat(spy.traceFile, is(equalTo(Command.DEFAULT_OUTPUT_FILE)));
        assertThat(spy.runCount, is(equalTo(Command.DEFAULT_RUN_COUNT)));
        assertThat(spy.observation, is(equalTo(Command.DEFAULT_OBSERVATION)));
        assertThat(spy.control, is(equalTo("infra")));
    }

    @Test
    public void shouldAcceptCombinationOfLongAndShortOptions() throws IOException {
        final String commandLine = "-t output.csv --runs=1234 myfile.trio";
        Command command = Command.from(commandLine);

        final TrioSpy spy = new TrioSpy();
        command.execute(spy, new NullOutputStream());

        assertThat(spy.inputFile, is(equalTo("myfile.trio")));
        assertThat(spy.traceFile, is(equalTo("output.csv")));
        assertThat(spy.runCount, is(equalTo(1234)));
        assertThat(spy.observation, is(equalTo(Command.DEFAULT_OBSERVATION)));
        assertThat(spy.control, is(equalTo(Command.DEFAULT_CONTROL)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectMissingInputFile() {
        final String commandLine = "-t output.csv --runs=1234";
        Command.from(commandLine);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNegativeRunCounts() {
        final String commandLine = "--runs=-1234 myFile.trio";
        Command.from(commandLine);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectZeroRunCounts() {
        final String commandLine = "--runs=0 myFile.trio";
        Command.from(commandLine);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectLongInvalidOptions() {
        final String commandLine = "--foo=bar myFile.trio";
        Command.from(commandLine);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectShortInvalidOptions() {
        final String commandLine = "-f bar myFile.trio";
        Command.from(commandLine);
    }

    private static class TrioSpy extends Trio {

        public String observation;
        public String inputFile;
        public String traceFile;
        public int runCount;
        public String control;

        @Override
        public void run(Scenario scenario, int runCount) {
            this.observation = scenario.observed().toString();
            this.control = scenario.controlled().toString();
            this.runCount = runCount;
            final DataSet dataSet = new DataSet();
            final Trace trace = new Trace(10);
            trace.record(AbstractAction.inactivate("X"), 4);
            trace.record(AbstractAction.inactivate("Y"), 3);
            trace.record(AbstractAction.inactivate("Z"), 0);
            dataSet.include(trace);
        }

        @Override
        public eu.diversify.trio.core.Assembly loadSystemFrom(String path) throws FileNotFoundException, IOException {
            this.inputFile = path;
            return new eu.diversify.trio.core.Assembly(new Component("A"));
        }

        @Override
        public void setTraceFile(String outputFile) {
            this.traceFile = outputFile;
        }
        
        

    }

    private class NullOutputStream extends OutputStream {

        @Override
        public void write(int b) throws IOException {
        }

    }
}
