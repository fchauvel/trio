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
 * Check that a copyright notice was displayed on STDOUT
 */
public class DidShowCopyright extends TypeSafeMatcher<Run> {

    private final int year;
    private final String owner;

    public DidShowCopyright(int year, String owner) {
        super();
        this.year = year;
        this.owner = owner;
    }

    @Override
    protected boolean matchesSafely(Run run) {
        Matcher matcher = Pattern.compile("Copyright\\s+\\([cC]\\)\\s+(\\d+)\\s*-([^\\n]+)").matcher(run.getStandardOutput());
        if (matcher.find()) {
            final int shownYear = Integer.parseInt(matcher.group(1));
            final String shownOwner = matcher.group(2);
            return shownYear == year
                    && shownOwner.trim().equals(owner);
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(String.format("Copyright (C) %d - %s", year, owner));
    }

    @Factory
    public static <T> org.hamcrest.Matcher<Run> didShowCopyright(int year, String owner) {
        return new DidShowCopyright(year, owner);
    }

}
