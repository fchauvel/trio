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

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Specification of the Trio response object
 */
@RunWith(JUnit4.class)
public class TrioResponseTest extends TestCase {

    /**
     * A dummy run whose stdout and stderr are simply given as parameters;
     */
    private static class DummyRun implements Run {

        private final String stdout;
        private final String stderr;

        public DummyRun(String stdout) {
            this(stdout, "");
        }

        public DummyRun(String stdout, String stderr) {
            this.stdout = stdout;
            this.stderr = stderr;
        }

        public String getStandardError() {
            return stderr;
        }

        public String getStandardOutput() {
            return stdout;
        }

    }

    @Test
    public void assertRobustnessAboutShouldDetectRobustness() {
        final TrioResponse sut = new TrioResponse(new DummyRun(" + Robustness: 0.1234"));
        sut.assertRobustnessAbout(0.1234, 1e-6);
    }

    @Test(expected = AssertionError.class)
    public void assertRobustnessAboutShouldDetectMissingRobustness() {
        final TrioResponse sut = new TrioResponse(new DummyRun("blablabla bla\n\nblablabla"));
        sut.assertRobustnessAbout(0.1234, 1e-6);
    }

    @Test
    public void assertRobustnessAboutShouldDetectWithinMultilineText() {
        final String stdout
                = "blablabla bla\n"
                + "blablabla\n"
                + "Robustness: 0.1234\n"
                + "blablabla blabla";
        final TrioResponse sut = new TrioResponse(new DummyRun(stdout));
        sut.assertRobustnessAbout(0.1234, 1e-6);
    }

}
