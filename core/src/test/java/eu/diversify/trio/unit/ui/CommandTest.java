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

import eu.diversify.trio.ui.Command;
import java.io.IOException;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static eu.diversify.trio.ui.Command.*;

/**
 * Specification of the Command object
 */
@RunWith(JUnit4.class)
public class CommandTest extends TestCase {

    @Test
    public void shouldProvideDefaultValuesForAllOptions() throws IOException {
        final String commandLine = "myfile.trio";
        Command command = Command.from(commandLine);

        verifyCommand(command, "myfile.trio", DEFAULT_OUTPUT_FILE, DEFAULT_RUN_COUNT, DEFAULT_OBSERVATION, DEFAULT_CONTROL);
    }

    @Test
    public void shouldAcceptShortVersionOfTraceFile() throws IOException {
        final String commandLine = "-t out.csv myfile.trio";
        Command command = Command.from(commandLine);

        verifyCommand(command, "myfile.trio", "out.csv", DEFAULT_RUN_COUNT, DEFAULT_OBSERVATION, DEFAULT_CONTROL);
    }

    @Test
    public void shouldAcceptLongVersionTraceFile() throws IOException {
        final String commandLine = "--trace=out.csv myfile.trio";
        Command command = Command.from(commandLine);

        verifyCommand(command, "myfile.trio", "out.csv", DEFAULT_RUN_COUNT, DEFAULT_OBSERVATION, DEFAULT_CONTROL);
    }

    @Test
    public void shouldAcceptShortVersionRunCount() throws IOException {
        final String commandLine = "-r 1234 myfile.trio";
        Command command = Command.from(commandLine);

        verifyCommand(command, "myfile.trio", DEFAULT_OUTPUT_FILE, 1234, DEFAULT_OBSERVATION, DEFAULT_CONTROL);
    }

    @Test
    public void shouldAcceptLongVersionRunCount() throws IOException {
        final String commandLine = "--runs=1234 myfile.trio";
        Command command = Command.from(commandLine);

        verifyCommand(command, "myfile.trio", DEFAULT_OUTPUT_FILE, 1234, DEFAULT_OBSERVATION, DEFAULT_CONTROL);
    }

    @Test
    public void shouldAcceptShortVersionForObserve() throws IOException {
        final String commandLine = "-o platform myfile.trio";
        Command command = Command.from(commandLine);

        verifyCommand(command, "myfile.trio", DEFAULT_OUTPUT_FILE, DEFAULT_RUN_COUNT, "platform", DEFAULT_CONTROL);
    }

    @Test
    public void shouldAcceptLongVersionObserve() throws IOException {
        final String commandLine = "--observe=platform myfile.trio";
        Command command = Command.from(commandLine);

        verifyCommand(command, "myfile.trio", DEFAULT_OUTPUT_FILE, DEFAULT_RUN_COUNT, "platform", DEFAULT_CONTROL);
    }

    @Test
    public void shouldAcceptShortVersionForControl() throws IOException {
        final String commandLine = "-c infra myfile.trio";
        Command command = Command.from(commandLine);

        verifyCommand(command, "myfile.trio", DEFAULT_OUTPUT_FILE, DEFAULT_RUN_COUNT, DEFAULT_OBSERVATION, "infra");
    }

    @Test
    public void shouldAcceptLongVersionForControl() throws IOException {
        final String commandLine = "--control=infra myfile.trio";
        Command command = Command.from(commandLine);

        verifyCommand(command, "myfile.trio", DEFAULT_OUTPUT_FILE, DEFAULT_RUN_COUNT, DEFAULT_OBSERVATION, "infra");
    }

    @Test
    public void shouldAcceptCombinationOfLongAndShortOptions() throws IOException {
        final String commandLine = "-t output.csv --runs=1234 myfile.trio";
        Command command = Command.from(commandLine);

        verifyCommand(command, "myfile.trio", "output.csv", 1234, DEFAULT_OBSERVATION, DEFAULT_CONTROL);
    }

    private void verifyCommand(Command command, String input, String output, int runCount, String observed, String controlled) {
        assertThat(command.getInput(), is(equalTo(input)));
        assertThat(command.getTraceFile(), is(equalTo(output)));
        assertThat(command.getRunCount(), is(equalTo(runCount)));
        assertThat(command.getObservation(), is(equalTo(observed)));
        assertThat(command.getControl(), is(equalTo(controlled)));
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

}
