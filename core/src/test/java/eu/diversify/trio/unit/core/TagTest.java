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

package eu.diversify.trio.unit.core;

import eu.diversify.trio.core.Component;
import eu.diversify.trio.core.DefaultSystemVisitor;
import eu.diversify.trio.core.SystemVisitor;
import eu.diversify.trio.core.Tag;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import junit.framework.TestCase;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Specification of the tag
 */
@RunWith(JUnit4.class)
public class TagTest extends TestCase {

    @Test
    public void shouldHaveALabel() {
        Tag tag = new Tag("foo", "C1", "C2");

        assertThat(tag.getLabel(), is(equalTo("foo")));
    }

    @Test
    public void shouldHaveTargets() {
        Tag tag = new Tag("foo", "C1", "C2");

        assertThat(tag.getTargets(), containsInAnyOrder("C1", "C2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNullNames() {
        new Tag(null, Arrays.asList(new String[]{"AA", "BB"}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNullTargets() {
        Collection<String> targets = null;
        new Tag("foo", targets);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectEmptyTargets() {
        Collection<String> targets = new ArrayList<String>();
        new Tag("foo", targets);
    }
    
       @Test
    public void beginShouldTriggerEnterOnTheVisitor() {
        final Mockery context = new JUnit4Mockery();

        final Tag tag = new Tag("foo", "on x");

        final SystemVisitor visitor = context.mock(SystemVisitor.class);

        context.checking(new Expectations() {
            {
                oneOf(visitor).enter(tag);
            }
        });

        tag.begin(visitor);

        context.assertIsSatisfied();
    }

    @Test
    public void endShouldTriggerExitOnTheVisitor() {
        final Mockery context = new JUnit4Mockery();

        final Tag tag = new Tag("foo", "on x");

        final SystemVisitor visitor = context.mock(SystemVisitor.class);

        context.checking(new Expectations() {
            {
                oneOf(visitor).exit(tag);
            }
        });

        tag.end(visitor);

        context.assertIsSatisfied();
    } 
}
