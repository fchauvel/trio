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

import eu.diversify.trio.core.requirements.RequirementFactory;
import eu.diversify.trio.core.requirements.Requirement;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Build a tree according to a sequence of commands addLeaf addOr addBranch
 */
final class Builder {

    private final Deque<Memento> pool = new LinkedList<Memento>();
    private final Deque<Memento> stack;
    private final RequirementFactory factory;
    private State state;
    private Requirement result;

    Builder(RequirementFactory factory) {
        this.stack = new LinkedList<Memento>();
        this.factory = factory;
    }
    
    void reset(int desiredSize) {
        this.result = null;
        this.state = new State(desiredSize);
    }

    List<Command> possibleCommands() {
        return state.relevantCommands();
    }

    Requirement getResult() {
        if (result == null) {
            throw new IllegalStateException("No result is available!");
        }
        return result;
    }

    boolean isRunning() {
        return !stack.isEmpty();
    }

    void addRequire(int index) {
        state.addLeaf();
        result = factory.createRequire(index);
        while (!stack.isEmpty() && result != null) {
            final Memento previous = stack.pop();
            switch (previous.role) {
                case AND_LEFT_BRANCH:
                    stack.push(get(ROLE.AND_RIGHT_BRANCH, result));
                    result = null;
                    break;
                case AND_RIGHT_BRANCH:
                    result = factory.createConjunction(previous.result, result);
                    break;
                case OR_LEFT_BRANCH:
                    stack.push(get(ROLE.OR_RIGHT_BRANCH, result));
                    result = null;
                    break;
                case OR_RIGHT_BRANCH:
                    result = factory.createDisjunction(previous.result, result);
                    break;
                case NOT_OPERAND:
                    result = factory.createNegation(result);
                    break;
            }
            free(previous);
        }
    }

    void addAnd() {
        state.addBranch();
        stack.push(get(ROLE.AND_LEFT_BRANCH, null));
    }

    void addOr() {
        state.addBranch();
        stack.push(get(ROLE.OR_LEFT_BRANCH, null));
    }

    void addNot() {
        state.addNegation();
        stack.push(get(ROLE.NOT_OPERAND, null));
    }

    private Memento get(ROLE role, Requirement tree) {
        if (pool.isEmpty()) {
            final Memento prebuilt = new Memento(role, tree);
            return prebuilt;
        } else {
            Memento prebuilt = pool.pop();
            prebuilt.reset(role, tree);
            return prebuilt;
        }
    }

    private void free(Memento memento) {
        memento.release();
        pool.push(memento);
    }

    private static enum ROLE {
        AND_LEFT_BRANCH,
        AND_RIGHT_BRANCH,
        OR_LEFT_BRANCH,
        OR_RIGHT_BRANCH,
        NOT_OPERAND;
    }

    private static class Memento {

        ROLE role;
        Requirement result;

        private Memento(ROLE role, Requirement result) {
            this.role = role;
            this.result = result;
        }

        public void reset(ROLE role, Requirement result) {
            this.role = role;
            this.result = result;
        }

        public void release() {
            this.role = null;
            this.result = null;
        }

    }

}
