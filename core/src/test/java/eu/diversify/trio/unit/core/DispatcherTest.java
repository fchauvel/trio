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
package eu.diversify.trio.unit.core;

import net.fchauvel.trio.core.Component;
import net.fchauvel.trio.core.Dispatcher;
import net.fchauvel.trio.core.AssemblyVisitor;
import net.fchauvel.trio.core.Tag;
import net.fchauvel.trio.core.requirements.Conjunction;
import net.fchauvel.trio.core.requirements.Disjunction;
import net.fchauvel.trio.core.requirements.Negation;
import net.fchauvel.trio.core.requirements.Nothing;
import net.fchauvel.trio.core.requirements.Require;
import eu.diversify.trio.unit.core.requirements.RequirementSamples;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Specification of the visitor dispatch
 */
@RunWith(JUnit4.class)
public class DispatcherTest {

    private final Mockery context = new JUnit4Mockery();
    private final AssemblyVisitor listenerB;
    private final AssemblyVisitor listenerA;
    private final Dispatcher dispatch;

    public DispatcherTest() {
        this.listenerA = context.mock(AssemblyVisitor.class, "listener A");
        this.listenerB = context.mock(AssemblyVisitor.class, "listener B");
        this.dispatch = new Dispatcher(listenerA, listenerB);
    }

    @Test
    public void enterNothingShouldBeProperlyDispatched() {
        final Nothing data = RequirementSamples.nothing();

        context.checking(new Expectations() {
            {
                oneOf(listenerA).enter(data);
                oneOf(listenerB).enter(data);
            }
        });

        dispatch.enter(data);

        context.assertIsSatisfied();
    }

    @Test
    public void exitNothingShouldBeProperlyDispatched() {
        final Nothing data = RequirementSamples.nothing();

        context.checking(new Expectations() {
            {
                oneOf(listenerA).exit(data);
                oneOf(listenerB).exit(data);
            }
        });

        dispatch.exit(data);

        context.assertIsSatisfied();
    }

    @Test
    public void enterRequireShouldBeProperlyDispatched() {
        final Require data = RequirementSamples.require();

        context.checking(new Expectations() {
            {
                oneOf(listenerA).enter(data);
                oneOf(listenerB).enter(data);
            }
        });

        dispatch.enter(data);

        context.assertIsSatisfied();
    }

    @Test
    public void exitRequireShouldBeProperlyDispatched() {
        final Require data = RequirementSamples.require();

        context.checking(new Expectations() {
            {
                oneOf(listenerA).exit(data);
                oneOf(listenerB).exit(data);
            }
        });

        dispatch.exit(data);

        context.assertIsSatisfied();
    }

    @Test
    public void enterConjunctionShouldBeProperlyDispatched() {
        final Conjunction data = RequirementSamples.conjunction();

        context.checking(new Expectations() {
            {
                oneOf(listenerA).enter(data);
                oneOf(listenerB).enter(data);
            }
        });

        dispatch.enter(data);

        context.assertIsSatisfied();
    }

    @Test
    public void exitConjunctionShouldBeProperlyDispatched() {
        final Conjunction data = RequirementSamples.conjunction();

        context.checking(new Expectations() {
            {
                oneOf(listenerA).exit(data);
                oneOf(listenerB).exit(data);
            }
        });

        dispatch.exit(data);

        context.assertIsSatisfied();
    }

    @Test
    public void enterDisjunctionShouldBeProperlyDispatched() {
        final Disjunction data = RequirementSamples.disjunction();

        context.checking(new Expectations() {
            {
                oneOf(listenerA).enter(data);
                oneOf(listenerB).enter(data);
            }
        });

        dispatch.enter(data);

        context.assertIsSatisfied();
    }

    @Test
    public void exitDisjunctionShouldBeProperlyDispatched() {
        final Disjunction data = RequirementSamples.disjunction();

        context.checking(new Expectations() {
            {
                oneOf(listenerA).exit(data);
                oneOf(listenerB).exit(data);
            }
        });

        dispatch.exit(data);

        context.assertIsSatisfied();
    }

    @Test
    public void enterNegationShouldBeProperlyDispatched() {
        final Negation data = RequirementSamples.negation();

        context.checking(new Expectations() {
            {
                oneOf(listenerA).enter(data);
                oneOf(listenerB).enter(data);
            }
        });

        dispatch.enter(data);

        context.assertIsSatisfied();
    }

    @Test
    public void exitNegationShouldBeProperlyDispatched() {
        final Negation data = RequirementSamples.negation();

        context.checking(new Expectations() {
            {
                oneOf(listenerA).exit(data);
                oneOf(listenerB).exit(data);
            }
        });

        dispatch.exit(data);

        context.assertIsSatisfied();
    }

    @Test
    public void enterComponentShouldBeProperlyDispatched() {
        final Component data = new Component("Foo");

        context.checking(new Expectations() {
            {
                oneOf(listenerA).enter(data);
                oneOf(listenerB).enter(data);
            }
        });

        dispatch.enter(data);

        context.assertIsSatisfied();
    }

    @Test
    public void exitComponentShouldBeProperlyDispatched() {
        final Component data = new Component("Foo");

        context.checking(new Expectations() {
            {
                oneOf(listenerA).exit(data);
                oneOf(listenerB).exit(data);
            }
        });

        dispatch.exit(data);

        context.assertIsSatisfied();
    }

    @Test
    public void enterTagShouldBeProperlyDispatched() {
        final Tag data = new Tag("Foo", "Bar");

        context.checking(new Expectations() {
            {
                oneOf(listenerA).enter(data);
                oneOf(listenerB).enter(data);
            }
        });

        dispatch.enter(data);

        context.assertIsSatisfied();
    }

    @Test
    public void exitTagShouldBeProperlyDispatched() {
        final Tag data = new Tag("Foo", "Bar");

        context.checking(new Expectations() {
            {
                oneOf(listenerA).exit(data);
                oneOf(listenerB).exit(data);
            }
        });

        dispatch.exit(data);

        context.assertIsSatisfied();
    }
    
    @Test
    public void enterSystemShouldBeProperlyDispatched() {
        final net.fchauvel.trio.core.Assembly data = new net.fchauvel.trio.core.Assembly(new Component("A"));

        context.checking(new Expectations() {
            {
                oneOf(listenerA).enter(data);
                oneOf(listenerB).enter(data);
            }
        });

        dispatch.enter(data);

        context.assertIsSatisfied();
    }

    @Test
    public void exitSystemShouldBeProperlyDispatched() {
        final net.fchauvel.trio.core.Assembly data = new net.fchauvel.trio.core.Assembly(new Component("A"));

        context.checking(new Expectations() {
            {
                oneOf(listenerA).exit(data);
                oneOf(listenerB).exit(data);
            }
        });

        dispatch.exit(data);

        context.assertIsSatisfied();
    }

}
