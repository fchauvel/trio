
package eu.diversify.trio;

import eu.diversify.trio.requirements.Require;
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
    
    @Test
    public void shouldProvideAccessToItsRequirement() {
        final Component component = new Component("Foo", new Require("Bar"));
        final Requirement expected = new Require("Bar");
        
        assertThat(component.getRequirement(), is(equalTo(expected)));   
    }
    
    
}
