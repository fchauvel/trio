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
package eu.diversify.trio.acceptance.driver;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * The response of Trio
 */
public class TrioResponse {

    private final Run run;

    public TrioResponse(Run run) {
        this.run = run;
    }

    public String getVersion() {
        throw new UnsupportedOperationException();
    }

    public String getCopyrightOwner() {
        throw new UnsupportedOperationException();
    }

    public String getCopyrightYear() {
        throw new UnsupportedOperationException();
    }

    public double getRobustness() {
        throw new UnsupportedOperationException();
    }

    private static final List<String> ERROR_MARKER = Arrays.asList(new String[]{
        "error", "Error", "ERROR",
        "exception", "Exception", "EXCEPTION"});

    public void assertNoError() {
        for (String anyMarker: ERROR_MARKER) {
            assertThat("Suspicious '" + anyMarker + "' in stdout!\n" + run.toString(), run.getStandardOutput(), not(containsString(anyMarker)));
            assertThat("Suspicious '" + anyMarker + "' in stderr!\n" + run.toString(), run.getStandardError(), not(containsString(anyMarker)));
        }
    }

    public void assertRobustnessAbout(double expected, double tolerance) {
        final double actual = findRobustness();
        assertThat("robustness", actual, is(closeTo(expected, tolerance)));
    }

    private double findRobustness() {
        final String REGEX = "Robustness:\\s*(\\d*\\.\\d+)";
        final Matcher matcher = Pattern.compile(REGEX).matcher(run.getStandardOutput());
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        final String error = String.format("No robustness found (expected regex '%s').", REGEX);
        throw new AssertionError(error);
    }

    public String toString() {
        return run.toString();
    }

    /**
     * Check whether a syntax error was reported on the standard output and
     * standard error.
     */
    public void assertInvalidSystemDetected() {
        assertThat("Invalid models should be reported\n" + run.toString(), run.getStandardOutput(), containsString(SYNTAX_ERROR));
    }
    
    private static final String SYNTAX_ERROR = "Invalid model";
}
