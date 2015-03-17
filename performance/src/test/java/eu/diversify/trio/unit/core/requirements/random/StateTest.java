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
/*
 */
package eu.diversify.trio.unit.core.requirements.random;

import eu.diversify.trio.core.requirements.random.State;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 */
@RunWith(JUnit4.class)
public class StateTest {

    @Test
    public void addLeavesShouldBeDisallowedAtFirst() {
        final State state = new State(10);

        assertThat(state.relevantCommands(), not(hasItem(State.ADD_REQUIRE)));
        assertThat(state.relevantCommands(), hasItem(State.ADD_AND));
        assertThat(state.relevantCommands(), hasItem(State.ADD_OR));
        assertThat(state.relevantCommands(), hasItem(State.ADD_NOT));
    }

    @Test
    public void addingBranchShouldBeDisallowedWhenItExceedsTheDesiredSize() {
        final State state = new State(2);

        state.addBranch();

        assertThat(state.relevantCommands(), not(hasItem(State.ADD_AND)));
        assertThat(state.relevantCommands(), not(hasItem(State.ADD_OR)));

    }

    @Test
    public void addingBranchShouldDisallowedWhenTheTreeGetsTheProperSize() {
        final State state = new State(2);

        state.addBranch();
        state.addLeaf();

        assertThat(state.relevantCommands(), hasItem(State.ADD_REQUIRE));
        assertThat(state.relevantCommands(), not(hasItem(State.ADD_AND)));
        assertThat(state.relevantCommands(), not(hasItem(State.ADD_OR)));
        assertThat(state.relevantCommands(), hasItem(State.ADD_NOT));

    }

    @Test
    public void sequenceOfNegationShouldBeDisallowed() {
        final State state = new State(10);

        state.addBranch();
        state.addNegation();

        assertThat(state.relevantCommands(), not(hasItem(State.ADD_NOT)));
    }

    @Test
    public void negationShouldBeAllowAfterABranch() {
        final State state = new State(10);

        state.addBranch();
        state.addNegation();
        state.addBranch();

        assertThat(state.relevantCommands(), hasItem(State.ADD_NOT));
    }

    @Test
    public void negationShouldBeAllowedAfterAddingALeaf() {
        final State state = new State(10);

        state.addBranch();
        state.addNegation();
        state.addLeaf();

        assertThat(state.relevantCommands(), hasItem(State.ADD_NOT));
    }

}
