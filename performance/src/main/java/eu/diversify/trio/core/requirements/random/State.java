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

package eu.diversify.trio.core.requirements.random;

import java.util.Arrays;
import java.util.List;

/**
 * Hold the internal state of the expression under construction
 */
public final class State {

    public static final Command ADD_REQUIRE = new AddRequire();
    public static final Command ADD_AND = new AddAnd();
    public static final Command ADD_OR = new AddOr();
    public static final Command ADD_NOT = new AddNot();

    /*
     * Below are the possible states depending on whether one can add leaves,
     * branches or negation
     *
     * 000: (negationAllowed = FALSE ; isBigEnough() = FALSE ; wouldCloseTheTree() = FALSE)
     *
     *  STATE | ADD_NOT | ADD_AND | ADD_OR | ADD_REQUIRE | 
     * 0: 000 |         |    X    |    X   |      X      |
     * 1: 001 |         |    X    |    X   |             | 
     * 2: 010 |         |         |        |      X      | 
     * 3: 011 |         |         |        |      X      | 
     * 4: 100 |    X    |    X    |    X   |      X      | 
     * 5: 101 |    X    |    X    |    X   |             | 
     * 6: 110 |    X    |         |        |      X      | 
     * 7: 111 |    X    |         |        |      X      |
     * 
     */
    private static final List[] RELEVANT_COMMANDS = new List[]{
        Arrays.asList(new Command[]{ADD_AND, ADD_OR, ADD_REQUIRE}),
        Arrays.asList(new Command[]{ADD_AND, ADD_OR}),
        Arrays.asList(new Command[]{ADD_REQUIRE}),
        Arrays.asList(new Command[]{ADD_REQUIRE}),
        Arrays.asList(new Command[]{ADD_NOT, ADD_AND, ADD_OR, ADD_REQUIRE}),
        Arrays.asList(new Command[]{ADD_NOT, ADD_AND, ADD_OR}),
        Arrays.asList(new Command[]{ADD_NOT, ADD_REQUIRE}),
        Arrays.asList(new Command[]{ADD_NOT, ADD_REQUIRE})
    };

    private final int desiredSize;
    private int currentSize;
    private int minimumFinalSize;

    private int currentState;
    private boolean allowNegation;

    public State(int desiredSize) {
        this(desiredSize, 0, 1);
    }

    public State(int desiredSize, int currentSize, int minimumFinalSize) {
        this.desiredSize = desiredSize;
        this.currentSize = currentSize;
        this.minimumFinalSize = minimumFinalSize;
        this.allowNegation = true;
        this.currentState = compactState();

    }

    @SuppressWarnings("unchecked")
    public List<Command> relevantCommands() {
        return RELEVANT_COMMANDS[currentState];
    }

    private int compactState() {
        return (wouldCloseTheTree() ? 1 : 0)
                | (isBigEnough() ? 1 : 0) << 1
                | (allowNegation ? 1 : 0) << 2;
    }

    private boolean wouldCloseTheTree() {
        return minimumFinalSize - currentSize == 1;
    }

    private boolean isBigEnough() {
        return desiredSize == minimumFinalSize;
    }

    public void addNegation() {
        allowNegation = false;
        currentState = compactState();
    }

    public void addLeaf() {
        allowNegation = true;
        currentSize++;
        currentState = compactState();
    }

    public void addBranch() {
        allowNegation = true;
        minimumFinalSize++;
        currentState = compactState();
    }

    @Override
    public String toString() {
        return "[S=" + currentSize + " ; M=" + minimumFinalSize + "]";
    }
}
