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
import eu.diversify.trio.core.Requirement;
import eu.diversify.trio.core.requirements.Require;
import static eu.diversify.trio.core.requirements.Factory.*;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Specifications of components
 */
@RunWith(JUnit4.class)
public class ComponentTest extends TestCase {

    @Test
    public void shouldProvideAccessToItsName() {
        final Component component = new Component("Foo");
        
        assertThat(component.getName(), is(equalTo("Foo"))); 
    }   
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNullNames() {
        new Component(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNullRequirement() {
        new Component("Foo", null);
    }
    
    @Test
    public void shouldProvideAccessToItsRequirement() {
        final Component component = new Component("Foo", new Require("Bar"));
        final Requirement expected = new Require("Bar");
        
        assertThat(component.getRequirement(), is(equalTo(expected)));   
    }
    
    
    @Test
    public void valencyShouldBeComputedCorrectly() {
        final Component c = new Component("A", require("B").or(require("C").and(require("D")))); 
        
        assertThat(c.getValency(), is(equalTo(3)));
    }
    
    
}
