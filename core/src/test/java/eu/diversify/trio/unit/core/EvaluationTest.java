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
import static net.fchauvel.trio.core.Evaluation.*;
import net.fchauvel.trio.core.AssemblyVisitor;
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

import static org.jmock.Expectations.any;

/**
 * Specification of the evaluation of a function (i.e., implemented as a
 * visitor) on a system.
 */
@RunWith(JUnit4.class)
public class EvaluationTest {

    private final Mockery context;

    public EvaluationTest() {
        context = new JUnit4Mockery();
    }

    @Test
    public void shouldTraverseRequire() {
        final Require data = RequirementSamples.require();
        final AssemblyVisitor function = context.mock(AssemblyVisitor.class);

        context.checking(new Expectations() {
            {
                oneOf(function).enter(data);
                oneOf(function).exit(data);
            }
        });

        evaluate(function).on(data);

        context.assertIsSatisfied();
    }

    @Test
    public void shouldTraverseNothing() {

        final Nothing data = RequirementSamples.nothing();
        final AssemblyVisitor function = context.mock(AssemblyVisitor.class);

        context.checking(new Expectations() {
            {
                oneOf(function).enter(data);
                oneOf(function).exit(data);
            }
        });

        evaluate(function).on(data);

        context.assertIsSatisfied();
    }

    @Test
    public void shouldTraverseConjunction() {
        final Conjunction data = RequirementSamples.conjunction();
        final AssemblyVisitor function = context.mock(AssemblyVisitor.class);

        context.checking(new Expectations() {
            {
                allowing(function).enter(with(any(Require.class)));
                allowing(function).exit(with(any(Require.class)));

                oneOf(function).enter(data);
                oneOf(function).exit(data);
            }
        });

        evaluate(function).on(data);

        context.assertIsSatisfied();
    }

    @Test
    public void shouldTraverseDisjunction() {
       final Disjunction data = RequirementSamples.disjunction();
        final AssemblyVisitor function = context.mock(AssemblyVisitor.class);

        context.checking(new Expectations() {
            {
                allowing(function).enter(with(any(Require.class)));
                allowing(function).exit(with(any(Require.class)));

                oneOf(function).enter(data);
                oneOf(function).exit(data);
            }
        });

        evaluate(function).on(data);

        context.assertIsSatisfied();
    }

    @Test
    public void shouldTraverseNegation() {
       final Negation data = RequirementSamples.negation();
        final AssemblyVisitor function = context.mock(AssemblyVisitor.class);

        context.checking(new Expectations() {
            {
                allowing(function).enter(with(any(Require.class)));
                allowing(function).exit(with(any(Require.class)));

                oneOf(function).enter(data);
                oneOf(function).exit(data);
            }
        });

        evaluate(function).on(data);

        context.assertIsSatisfied();
    }

    @Test
    public void shouldTraverseComponent() {

        final Component data = new Component("A");
        final AssemblyVisitor function = context.mock(AssemblyVisitor.class);

        context.checking(new Expectations() {
            {
                allowing(function).enter(with(any(Nothing.class)));
                allowing(function).exit(with(any(Nothing.class)));

                oneOf(function).enter(data);
                oneOf(function).exit(data);
            }
        });

        evaluate(function).on(data);

        context.assertIsSatisfied();
    }

}
