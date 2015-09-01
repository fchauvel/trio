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
package eu.diversify.trio.generator.requirements;

import eu.diversify.trio.core.requirements.CachedFactory;
import eu.diversify.trio.generator.requirements.Command;
import eu.diversify.trio.generator.requirements.FixedSizeBuilder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import org.junit.Test;

import static eu.diversify.trio.generator.requirements.Command.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItems;

/**
 * Specification of the fixed size builder
 */
public class FixedSizeBuilderTest {

    @Test
    public void shouldAllowBuildingBinaryExpression() {
        final int capacity = 2;
        FixedSizeBuilder builder = prepareBuilder(capacity);

        assertThat(builder.getAllowedCommands(), hasItem(OPEN_CONJUNCTION));
        assertThat(builder.getAllowedCommands(), hasItem(OPEN_DISJUNCTION));
    }

    @Test
    public void shouldPreventTooSmallRequirements() {
        final int capacity = 10;
        FixedSizeBuilder builder = prepareBuilder(capacity);

        assertThat(builder.getAllowedCommands(), not(hasItem(ADD_REQUIRE)));
    }

    @Test
    public void shouldAllowFillingUpTheRequirement() {
        final int capacity = 1;
        FixedSizeBuilder builder = prepareBuilder(capacity);

        assertThat(builder.getAllowedCommands(), hasItem(ADD_REQUIRE));
    }

    @Test
    public void shouldPreventEnlargingOperatorsWhenBigEnough() {
        final int capacity = 3;
        FixedSizeBuilder builder = prepareBuilder(capacity);

        builder.openConjunction();
        builder.openDisjunction();
        builder.addRequire(1);
        builder.addRequire(2);

        assertThat(builder.getAllowedCommands(), hasItems(new Command[]{CLOSE}));
    }

    @Test
    public void shouldPreventExceedingTheGivenSize() {
        final int capacity = 2;
        FixedSizeBuilder builder = prepareBuilder(capacity);

        builder.openConjunction();
        builder.addRequire(1);
        builder.addRequire(2);

        assertThat(builder.getAllowedCommands(), hasItems(new Command[]{CLOSE}));
    }

    @Test
    public void shouldPreventOpeningNewBranchesWhenBigEnough() {
        final int capacity = 2;
        FixedSizeBuilder builder = prepareBuilder(capacity);

        builder.openConjunction();

        assertThat(builder.getAllowedCommands(), not(hasItem(OPEN_CONJUNCTION)));
        assertThat(builder.getAllowedCommands(), not(hasItem(OPEN_DISJUNCTION)));
    }

    @Test
    public void shouldAllowOpeningBranchWhenPossible() {
        final int capacity = 4;
        FixedSizeBuilder builder = prepareBuilder(capacity);

        assertThat(builder.getAllowedCommands(), hasItem(OPEN_CONJUNCTION));
        assertThat(builder.getAllowedCommands(), hasItem(OPEN_DISJUNCTION));
    }

    @Test
    public void shouldPreventAnythingWhenDone() {
        final int capacity = 1;
        FixedSizeBuilder builder = prepareBuilder(capacity);

        builder.addRequire(1);

        assertThat(builder.getAllowedCommands(), is(empty()));
    }

    @Test
    public void shouldForbidDoubleNegation() {
        final int capacity = 10;
        FixedSizeBuilder builder = prepareBuilder(capacity);

        builder.addNot();

        assertThat(builder.getAllowedCommands(), not(hasItem(ADD_NEGATION)));
    }

    @Test
    public void shouldAllowNegationOneNewBranches() {
        final int capacity = 10;
        FixedSizeBuilder builder = prepareBuilder(capacity);

        builder.addNot();
        builder.openConjunction();

        assertThat(builder.getAllowedCommands(), hasItem(ADD_NEGATION));
    }

    @Test
    public void shouldAllowAddingToTheRootNode() {
        final int capacity = 10;
        FixedSizeBuilder builder = prepareBuilder(capacity);

        builder.openConjunction();
        builder.addRequire(1);
        builder.addRequire(2);

        assertThat(builder.getAllowedCommands(), containsInAnyOrder(OPEN_CONJUNCTION, OPEN_DISJUNCTION, ADD_NEGATION, ADD_REQUIRE));
    }

    @Test
    public void shouldNotFail() {
        final int capacity = 5;
        FixedSizeBuilder builder = prepareBuilder(capacity);

        builder.openConjunction();
        builder.addRequire(1);
        builder.openDisjunction();
        builder.addRequire(2);
        builder.addNot();
        builder.addRequire(3);
        builder.openConjunction();

        assertThat(builder.getAllowedCommands(), not(hasItem(OPEN_CONJUNCTION)));
        assertThat(builder.getAllowedCommands(), not(hasItem(OPEN_DISJUNCTION)));
    }

    @Test
    public void shouldNotAllowOpenningBranchWhereOnlyLeafCanFit() {
        final int capacity = 5;
        FixedSizeBuilder builder = prepareBuilder(capacity);

        builder.openDisjunction();
        builder.openConjunction();
        builder.addRequire(1);
        builder.addRequire(2);
        builder.closeOperator();
        builder.addRequire(3);
        builder.addRequire(4);

        assertThat(builder.getAllowedCommands(), not(hasItem(OPEN_CONJUNCTION)));
        assertThat(builder.getAllowedCommands(), not(hasItem(OPEN_DISJUNCTION)));
    }

    @Test
    public void shouldNotAllowClosingRequirementIfTooSmall() {
        final int capacity = 5;
        FixedSizeBuilder builder = prepareBuilder(capacity);

        builder.openDisjunction();
        builder.openConjunction();
        builder.openConjunction();
        builder.addRequire(1);
        builder.addRequire(2);
        builder.closeOperator();
        builder.addRequire(3);
        builder.closeOperator();
        builder.addRequire(4);

        assertThat(builder.getAllowedCommands(), not(hasItem(CLOSE)));
    }

    @Test
    public void shouldNotAllowAddingALeafThatWouldExceedSize() {
        final int capacity = 5;
        FixedSizeBuilder builder = prepareBuilder(capacity);

        builder.openDisjunction();
        builder.openDisjunction();
        builder.openDisjunction();
        builder.addRequire(1);
        builder.addRequire(2);
        builder.addRequire(3);
        builder.closeOperator();
        builder.addRequire(4);

        assertThat(builder.getAllowedCommands(), not(hasItem(ADD_REQUIRE)));
        assertThat(builder.getAllowedCommands(), not(hasItem(ADD_NEGATION)));
    }

    @Test
    public void shouldNotAllowAddingALeafThatWouldExceedSize2() {
        final int capacity = 5;
        FixedSizeBuilder builder = prepareBuilder(capacity);

        builder.openDisjunction();
        builder.addRequire(1);
        builder.openConjunction();
        builder.addRequire(2);
        builder.openDisjunction();
        builder.addRequire(2);
        builder.addRequire(3);
        builder.addRequire(4);
        builder.closeOperator();
        builder.closeOperator();

        assertThat(builder.getAllowedCommands(), not(hasItem(ADD_REQUIRE)));
        assertThat(builder.getAllowedCommands(), not(hasItem(ADD_NEGATION)));
    }

    @Test
    public void shouldNotAllowAddingANegationThatWouldExceedSize() {
        final int capacity = 5;
        FixedSizeBuilder builder = prepareBuilder(capacity);

        builder.openConjunction();
        builder.openDisjunction();
        builder.openConjunction();
        builder.addRequire(1);
        builder.addRequire(2);
        builder.addNot();
        builder.addRequire(2);

        assertThat(builder.getAllowedCommands(), not(hasItem(ADD_REQUIRE)));
        assertThat(builder.getAllowedCommands(), not(hasItem(ADD_NEGATION)));
    }

    @Test
    public void shouldPreventSequenceOfConjunction() {
        final int capacity = 5;
        FixedSizeBuilder builder = prepareBuilder(capacity);

        builder.openConjunction();

        assertThat(builder.getAllowedCommands(), not(hasItem(OPEN_CONJUNCTION)));
    }

    @Test
    public void shouldAllowBrokenSequenceOfConjunction() {
        final int capacity = 5;
        FixedSizeBuilder builder = prepareBuilder(capacity);

        builder.openConjunction();
        builder.openDisjunction();

        assertThat(builder.getAllowedCommands(), hasItem(OPEN_CONJUNCTION));
    }

    @Test
    public void shouldPreventSequenceOfDisjunction() {
        final int capacity = 5;
        FixedSizeBuilder builder = prepareBuilder(capacity);

        builder.openDisjunction();

        assertThat(builder.getAllowedCommands(), not(hasItem(OPEN_DISJUNCTION)));
    }

    @Test
    public void shouldAllowBrokenSequenceOfDisjunction() {
        final int capacity = 5;
        FixedSizeBuilder builder = prepareBuilder(capacity);

        builder.openDisjunction();
        builder.openConjunction();

        assertThat(builder.getAllowedCommands(), hasItem(OPEN_DISJUNCTION));
    }

    private FixedSizeBuilder prepareBuilder(final int capacity) {
        return new FixedSizeBuilder(new CachedFactory(), capacity);
    }

}
