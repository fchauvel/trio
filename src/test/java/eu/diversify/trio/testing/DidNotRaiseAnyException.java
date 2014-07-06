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

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

/**
 * Check whether exception were reported on either 'stdout' or 'stderr' during
 * the run.
 */
public class DidNotRaiseAnyException extends TypeSafeMatcher<Run> {

    private static final List<String> errorKeywords
            = Arrays.asList(new String[]{
                "ERROR", "Error", "error", "Exception", "exception"
            });

    @Override
    protected boolean matchesSafely(Run run) {
        boolean match = true;
        final Iterator<String> iterator = errorKeywords.iterator();
        while (iterator.hasNext() && match) {
            final String eachKeyword = iterator.next();
            match &= !run.getStandardOutput().contains(eachKeyword)
                    && !run.getStandardError().contains(eachKeyword);
        }
        return match;
    }

    @Override
    public void describeTo(Description d) {
        d.appendText("unexpected error were reported");
    }

    @Factory
    public static <T> org.hamcrest.Matcher<Run> didNotReportAnyError() {
        return new DidNotRaiseAnyException();
    }

}
