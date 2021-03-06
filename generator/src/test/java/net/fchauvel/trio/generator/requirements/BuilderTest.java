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
 * This file is part of TRIO :: Generator.
 *
 * TRIO :: Generator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Generator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Generator.  If not, see <http://www.gnu.org/licenses/>.
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
 * This file is part of TRIO :: Generator.
 *
 * TRIO :: Generator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Generator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Generator.  If not, see <http://www.gnu.org/licenses/>.
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
package net.fchauvel.trio.generator.requirements;

import net.fchauvel.trio.core.requirements.CachedFactory;
import net.fchauvel.trio.core.requirements.Conjunction;
import net.fchauvel.trio.core.requirements.Disjunction;
import static net.fchauvel.trio.core.requirements.Factory.require;
import net.fchauvel.trio.core.requirements.Negation;
import net.fchauvel.trio.core.requirements.Require;
import net.fchauvel.trio.core.requirements.Requirement;
import net.fchauvel.trio.core.requirements.RequirementFactory;
import net.fchauvel.trio.generator.requirements.Builder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

/**
 * Specification of the requirements builder
 */
public class BuilderTest {

    @Test
    public void resultShouldNotBeReadyAtFirst() {
        Builder builder = prepareBuilder();

        assertThat(builder.isResultReady(), is(false));
    }

    @Test(expected = IllegalStateException.class)
    public void resultShouldDisabledUntilItGetsReady() {
        Builder builder = prepareBuilder();
        builder.getResult();
    }

    @Test
    public void resultShouldEventuallyBeReady() {
        Builder builder = prepareBuilder();

        builder.addRequire(1);

        assertThat(builder.isResultReady(), is(true));
    }

    @Test
    public void shouldBuildRequire() {
        Builder builder = prepareBuilder();

        builder.addRequire(1);
        Requirement actual = builder.getResult();

        Requirement expected = new Require("C1");
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void shouldBuildBinaryConjunction() {
        Builder builder = prepareBuilder();

        builder.openConjunction();
        builder.addRequire(1);
        builder.addRequire(2);
        builder.closeOperator();
        Requirement actual = builder.getResult();

        Requirement expected = require("C1").and(require("C2"));
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void shouldNAryConjunction() {
        Builder builder = prepareBuilder();

        builder.openConjunction();
        builder.addRequire(1);
        builder.addRequire(2);
        builder.addRequire(3);
        builder.closeOperator();
        Requirement actual = builder.getResult();

        Requirement expected = new Conjunction(require("C1"), require("C2"), require("C3"));
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void shouldBuildBinaryDisjunction() {
        Builder builder = prepareBuilder();

        builder.openDisjunction();
        builder.addRequire(1);
        builder.addRequire(2);
        builder.closeOperator();
        Requirement actual = builder.getResult();

        Requirement expected = require("C1").or(require("C2"));
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void shouldNAryDisjunction() {
        Builder builder = prepareBuilder();

        builder.openDisjunction();
        builder.addRequire(1);
        builder.addRequire(2);
        builder.addRequire(3);
        builder.closeOperator();
        Requirement actual = builder.getResult();

        Requirement expected = new Disjunction(require("C1"), require("C2"), require("C3"));
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void shouldBuildNegation() {
        Builder builder = prepareBuilder();

        builder.addNot();
        builder.addRequire(1);
        Requirement actual = builder.getResult();

        Requirement expected = require("C1").not();
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void shouldBuildDoubleNegation() {
        Builder builder = prepareBuilder();

        builder.addNot();
        builder.addNot();
        builder.addRequire(1);
        Requirement actual = builder.getResult();

        Requirement expected = require("C1").not().not();
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void shouldBuildComplexExpression() {
        Builder builder = prepareBuilder();

        builder.openConjunction();
        builder.addNot();
        builder.addRequire(1);
        builder.addRequire(2);
        builder.openDisjunction();
        builder.addRequire(3);
        builder.addRequire(4);
        builder.addRequire(5);
        builder.closeOperator();
        builder.closeOperator();
        Requirement actual = builder.getResult();

        Requirement expected
                = new Conjunction(
                        new Negation(new Require("C1")),
                        new Require("C2"),
                        new Disjunction(
                                new Require("C3"),
                                new Require("C4"),
                                new Require("C5"))
                );

        assertThat(actual, is(equalTo(expected)));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldPreventClosingIncompleteConjunction() {
        Builder builder = prepareBuilder();

        builder.openConjunction();
        builder.closeOperator();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldPreventClosingIncompleteDisjunction() {
        Builder builder = prepareBuilder();

        builder.openDisjunction();
        builder.addRequire(1);
        builder.closeOperator();
    }
    
    @Test
    public void shouldDetectWhenClosingIsLegal() {
        Builder builder = prepareBuilder();

        builder.openDisjunction();
        builder.addRequire(1);
        builder.addRequire(2);
        
        assertThat(builder.canCloseOperator(), is(true));
    }
    
    
    @Test
    public void shouldDisableClosingWhenDone() {
        Builder builder = prepareBuilder();

        builder.openDisjunction();
        builder.addRequire(1);
        builder.addRequire(2);
        builder.closeOperator();
        
        assertThat(builder.canCloseOperator(), is(false));
    }
    
    @Test
    public void shouldDisableAllActionsWhenDone() {
         Builder builder = prepareBuilder();

        builder.openDisjunction();
        builder.addRequire(1);
        builder.addRequire(2);
        builder.closeOperator();
        
        assertThat(builder.getAllowedCommands(), is(empty()));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldPreventClosingANegation() {
        Builder builder = prepareBuilder();

        builder.addNot();
        builder.closeOperator();
    }

    private Builder prepareBuilder() {
        RequirementFactory factory = new CachedFactory();
        Builder builder = new Builder(factory);
        return builder;
    }

}
