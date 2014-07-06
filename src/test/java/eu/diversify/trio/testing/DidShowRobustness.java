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


package eu.diversify.trio.testing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

/**
 * Assert the robustness level displayed during the run
 */
public class DidShowRobustness extends TypeSafeMatcher<Run> {

    public static final double DEFAULT_TOLERANCE = 1e-6;

    private final double robustness;
    private final double tolerance;

    public DidShowRobustness(double robustness) {
        this(robustness, DEFAULT_TOLERANCE);
    }

    public DidShowRobustness(double robustness, double tolerance) {
        this.robustness = robustness;
        this.tolerance = tolerance;
    }

    @Override
    protected boolean matchesSafely(Run run) {
        final Matcher matcher = Pattern.compile("Robustness:\\s*(\\d+\\.\\d+)\\s*%").matcher(run.getStandardOutput());
        if (matcher.find()) {
            final double shownedRobustness = Double.parseDouble(matcher.group(1));
            return Math.abs(shownedRobustness - robustness) <= tolerance;
        }
        return false;
    }

    @Override
    public void describeTo(Description d) {
        d.appendText(String.format("Robustness: %.2f %% (+/- %.2f)", robustness, tolerance));
    }

    @Factory
    public static <T> org.hamcrest.Matcher<Run> didShowRobustness(double robustness, double tolerance) {
        return new DidShowRobustness(robustness, tolerance);
    }

}
