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

package eu.diversify.trio.unit.ui;

import eu.diversify.trio.Trio;
import eu.diversify.trio.analysis.Analysis;
import eu.diversify.trio.ui.Command;
import java.io.IOException;
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
        command.execute(spy);

        assertThat(spy.inputFile, is(equalTo("myfile.trio")));
        assertThat(spy.outputFile, is(equalTo(Command.DEFAULT_OUTPUT_FILE)));
        assertThat(spy.runCount, is(equalTo(Command.DEFAULT_RUN_COUNT)));
    }

    @Test
    public void shouldAcceptShortVersionOfOutputFile() throws IOException {
        final String commandLine = "-o out.csv myfile.trio";
        Command command = Command.from(commandLine);

        final TrioSpy spy = new TrioSpy();
        command.execute(spy);

        assertThat(spy.inputFile, is(equalTo("myfile.trio")));
        assertThat(spy.outputFile, is(equalTo("out.csv")));
        assertThat(spy.runCount, is(equalTo(Command.DEFAULT_RUN_COUNT)));
    }

    @Test
    public void shouldAcceptLongVersionOutputFile() throws IOException {
        final String commandLine = "--output=out.csv myfile.trio";
        Command command = Command.from(commandLine);

        final TrioSpy spy = new TrioSpy();
        command.execute(spy);

        assertThat(spy.inputFile, is(equalTo("myfile.trio")));
        assertThat(spy.outputFile, is(equalTo("out.csv")));
        assertThat(spy.runCount, is(equalTo(Command.DEFAULT_RUN_COUNT)));
    }

    @Test
    public void shouldAcceptShortVersionRunCount() throws IOException {
        final String commandLine = "-r 1234 myfile.trio";
        Command command = Command.from(commandLine);

        final TrioSpy spy = new TrioSpy();
        command.execute(spy);

        assertThat(spy.inputFile, is(equalTo("myfile.trio")));
        assertThat(spy.outputFile, is(equalTo(Command.DEFAULT_OUTPUT_FILE)));
        assertThat(spy.runCount, is(equalTo(1234)));
    }

    @Test
    public void shouldAcceptLongVersionRunCount() throws IOException{
        final String commandLine = "--runs=1234 myfile.trio";
        Command command = Command.from(commandLine);

        final TrioSpy spy = new TrioSpy();
        command.execute(spy);

        assertThat(spy.inputFile, is(equalTo("myfile.trio")));
        assertThat(spy.outputFile, is(equalTo(Command.DEFAULT_OUTPUT_FILE)));
        assertThat(spy.runCount, is(equalTo(1234)));
    }

    @Test
    public void shouldAcceptCombinationOfLongAndShortOptions() throws IOException {
        final String commandLine = "-o output.csv --runs=1234 myfile.trio";
        Command command = Command.from(commandLine);

        final TrioSpy spy = new TrioSpy();
        command.execute(spy);

        assertThat(spy.inputFile, is(equalTo("myfile.trio")));
        assertThat(spy.outputFile, is(equalTo("output.csv")));
        assertThat(spy.runCount, is(equalTo(1234)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectMissingInputFile() {
        final String commandLine = "-o output.csv --runs=1234";
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

        public String inputFile;
        public String outputFile;
        public int runCount;

        @Override
        public Analysis analyse(String inputFile, String outputFile, int runCount) {
            this.inputFile = inputFile;
            this.outputFile = outputFile;
            this.runCount = runCount;
            return null;
        }

    }
}
