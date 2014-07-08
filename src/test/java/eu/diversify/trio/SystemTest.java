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
