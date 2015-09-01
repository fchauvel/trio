/**
 * This file is part of TRIO :: Performance.
 *
 * TRIO :: Performance is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Performance is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Performance.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
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
package eu.diversify.trio.unit.performance;

import eu.diversify.trio.performance.Arguments;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

/**
 * Specification of the Options reader class
 */
public class OptionsTest {

    @Test
    public void shouldDetectSetupFile() {
        String[] args = new String[]{"-setup", "my_setup.properties"};

        Arguments options = Arguments.readFrom(args);

        assertThat(options.getSetupFile(), is(equalTo(args[1])));
        assertThat(options.getOutputFile(), is(equalTo(Arguments.DEFAULT_OUTPUT_FILE)));
    }

    @Test
    public void shouldDetectSetupFileWhenShortNotationIsUsed() {
        String[] args = new String[]{"-s", "my_setup.properties"};

        Arguments options = Arguments.readFrom(args);

        assertThat(options.getSetupFile(), is(equalTo(args[1])));
        assertThat(options.getOutputFile(), is(equalTo(Arguments.DEFAULT_OUTPUT_FILE)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectMissingSetupFile() {
        String[] args = new String[]{"-setup"};

        Arguments options = Arguments.readFrom(args);
    }

    @Test
    public void shouldDetectOutputFile() {
        String[] args = new String[]{"-output", "results.csv"};

        Arguments options = Arguments.readFrom(args);

        assertThat(options.getOutputFile(), is(equalTo(args[1])));
        assertThat(options.getSetupFile(), is(equalTo(Arguments.DEFAULT_SETUP_FILE)));
    }

    @Test
    public void shouldDetectOutputFileWhenShortNotationIsUsed() {
        String[] args = new String[]{"-o", "results.csv"};

        Arguments options = Arguments.readFrom(args);

        assertThat(options.getOutputFile(), is(equalTo(args[1])));
        assertThat(options.getSetupFile(), is(equalTo(Arguments.DEFAULT_SETUP_FILE)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectMissingOutputFile() {
        String[] args = new String[]{"-output"};

        Arguments options = Arguments.readFrom(args);
    }

    @Test
    public void shouldDetectBoth() {
        String[] args = new String[]{"-setup", "setup.properties", "-output", "results.csv"};

        Arguments options = Arguments.readFrom(args);

        assertThat(options.getSetupFile(), is(equalTo(args[1])));
        assertThat(options.getOutputFile(), is(equalTo(args[3])));
    }

    @Test
    public void shouldDetectBothInReverseOrder() {
        String[] args = new String[]{"-output", "results.csv", "-setup", "setup.properties"};

        Arguments options = Arguments.readFrom(args);

        assertThat(options.getSetupFile(), is(equalTo(args[3])));
        assertThat(options.getOutputFile(), is(equalTo(args[1])));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectUnknownOptions() {
        String[] args = new String[]{"-config", "results.csv"};

        Arguments options = Arguments.readFrom(args);
    }

}
