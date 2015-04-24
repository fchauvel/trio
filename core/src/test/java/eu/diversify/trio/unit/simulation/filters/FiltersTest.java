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
package eu.diversify.trio.unit.simulation.filters;

import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.core.*;
import eu.diversify.trio.simulation.filter.All;
import eu.diversify.trio.simulation.filter.Filter;
import eu.diversify.trio.simulation.filter.TaggedAs;
import java.util.*;
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
public class FiltersTest extends TestCase {

    @Test
    public void filtersShouldBeEffective() {

        Assembly system = systemWithABC(defaultTags());

        Filter filter = new TaggedAs("X").or(new TaggedAs("Y").and(new TaggedAs("Z").not()));
        Set<String> result = filter.resolve(system);

        assertThat(result, containsInAnyOrder("A", "C"));
    }

    @Test
    public void allShouldSelectAllComponents() {
        Assembly system = systemWithABC(defaultTags());

        Filter filter = All.getInstance();
        Set<String> result = filter.resolve(system);

        assertThat(result, containsInAnyOrder("A", "B", "C"));
    }


    @Test
    public void twoDifferentFiltersShouldNotBeEqual() {
        Filter all = All.getInstance();
        Filter tax = new TaggedAs("X");

        assertThat(all, is(not(equalTo(tax))));
        assertThat(tax, is(not(equalTo(all))));
    }

    @Test
    public void twoDifferentFiltersShouldHaveFifferentHashCode() {
        Filter all = All.getInstance();
        Filter tax = new TaggedAs("X");

        assertThat(all.hashCode(), is(not(equalTo(tax.hashCode()))));
    }

    @Test
    public void twoSimilarFiltersShouldBeEqual() {
        Filter tax1 = new TaggedAs("X");
        Filter tax2 = new TaggedAs("X");

        assertThat(tax1, is(equalTo(tax2)));
        assertThat(tax2, is(equalTo(tax1)));
    }
    
        @Test
    public void twoSimilarFiltersShouldHaveTheSameHashcode() {
        Filter tax1 = new TaggedAs("X");
        Filter tax2 = new TaggedAs("X");

        assertThat(tax1.hashCode(), is(equalTo(tax2.hashCode())));
    }

    protected List<Tag> defaultTags() {
        List<Tag> tags = new ArrayList<Tag>();
        tags.add(new Tag("X", "A"));
        tags.add(new Tag("Y", "B", "C"));
        tags.add(new Tag("Z", "B"));
        return tags;
    }

    protected Assembly systemWithABC(List<Tag> tags) {
        List<Component> components = new ArrayList<Component>();
        components.add(new Component("A"));
        components.add(new Component("B"));
        components.add(new Component("C"));
        Assembly system = new Assembly("test", components, tags);
        return system;
    }

}
