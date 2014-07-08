package eu.diversify.trio;

import eu.diversify.trio.requirements.Require;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.*;

/**
 * Specification of a system definition
 */
@RunWith(JUnit4.class)
public class SystemTest extends TestCase {

    @Test
    public void shouldInstantiateNewTopology() {
        final System system = new System(
                new Component("Foo", new Require("Bar")),
                new Component("Bar")
        );
        
        final Topology topology = system.instantiate();
        
        assertThat("", topology.isActive("Foo"));
        assertThat("", topology.isActive("Bar"));
    }

}
