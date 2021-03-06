/**
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
 * This file is part of TRIO :: Core.
 *
 * TRIO :: Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Core.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
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
 * ====
 *     This file is part of TRIO :: Core.
 *
 *     TRIO :: Core is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     TRIO :: Core is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with TRIO :: Core.  If not, see <http://www.gnu.org/licenses/>.
 * ====
 *
 * ====
 *     This file is part of TRIO.
 *
 *     TRIO is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     TRIO is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with TRIO.  If not, see <http://www.gnu.org/licenses/>.
 * ====
 *
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
 * This file is part of TRIO :: Core.
 *
 * TRIO :: Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Core.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
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
package eu.diversify.trio.unit.simulation;

import net.fchauvel.trio.simulation.AssemblyState;
import net.fchauvel.trio.core.Assembly;
import net.fchauvel.trio.core.Component;
import static net.fchauvel.trio.core.requirements.Factory.*;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Specification of a topology
 */
@RunWith(JUnit4.class)
public class TopologyTest extends TestCase {

  

    @Test
    public void capacityShouldRemainConstantRegardlessOfAction() {
        final Assembly system = new Assembly(new Component("Foo"), new Component("Bar"));
        final AssemblyState sut = new AssemblyState(system);

        sut.inactivate("Foo");

        assertThat("Survivor count", sut.size(), is(equalTo(2)));
    }

  
    @Test
    public void shouldSupportEnablingSpecificComponent() {
        final Assembly system = new Assembly(new Component("Foo"), new Component("Bar"));
        final AssemblyState sut = new AssemblyState(system);
        sut.inactivate("Foo");
        sut.activate("Foo");

        assertThat("Survivor count", sut.activeComponents().size(), is(equalTo(2)));
    }

    @Test
    public void shouldSupportDetectingExistenceOfActiveComponents() {
        final Assembly system = new Assembly(new Component("Foo"), new Component("Bar"));
        final AssemblyState sut = new AssemblyState(system);

        assertThat("should have active components", sut.hasActiveComponents());
    }

    @Test
    public void shouldSupportSelectingActiveComponents() {
        final Assembly system = new Assembly(new Component("Foo"), new Component("Bar"));
        final AssemblyState sut = new AssemblyState(system);

        assertThat("should have active components", sut.activeComponents(), contains("Foo", "Bar"));
    }

    @Test
    public void disjunctionShouldRemainActiveDespiteOneRequirementMissing() {
        final Assembly system = new Assembly(
                new Component("A", require("B").or(require("C"))),
                new Component("B"),
                new Component("C"));

        final AssemblyState sut = new AssemblyState(system);
        sut.inactivate("B");

        assertThat("A should be active", sut.isActive("A"));
    }

    @Test
    public void disjunctionShouldBeInactiveWhenAllRequirementsAreMissing() {
        final Assembly system = new Assembly(
                new Component("A", require("B").or(require("C"))),
                new Component("B"),
                new Component("C"));

        final AssemblyState sut = new AssemblyState(system);
        sut.inactivate("B");
        sut.inactivate("C");

        assertThat("A should not be active", !sut.isActive("A"));
    }

    @Test
    public void conjunctionShouldBeInactiveRegarlessOfWhichDependencyIsMissing() {
        final Assembly system = new Assembly(
                new Component("A", require("B").and(require("C"))),
                new Component("B"),
                new Component("C"));

        final AssemblyState sut = new AssemblyState(system);
        sut.inactivate("C");

        assertThat("A should not be active", !sut.isActive("A"));
    }

    @Test
    public void conjunctionShouldBeInactiveWhenAllDependenciesAreMissing() {
        final Assembly system = new Assembly(
                new Component("A", require("B").and(require("C"))),
                new Component("B"),
                new Component("C"));

        final AssemblyState sut = new AssemblyState(system);
        sut.inactivate("C");
        sut.inactivate("B");

        assertThat("A should not be active", !sut.isActive("A"));
    }

    @Test
    public void inactivityShouldPropagatesAlongDependencies() {
        final Assembly system = new Assembly(
                new Component("A", require("B")),
                new Component("B", require("C")),
                new Component("C"));

        final AssemblyState sut = new AssemblyState(system);
        sut.inactivate("C");

        assertThat("A should not be active", !sut.isActive("A"));
    }

}
