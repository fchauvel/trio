/**
 *
 * This file is part of TRIO.
 *
 * TRIO is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * TRIO is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO. If not, see <http://www.gnu.org/licenses/>.
 */

package eu.diversify.trio.generator.requirements;

import eu.diversify.trio.core.requirements.RequirementFactory;
import eu.diversify.trio.core.requirements.Requirement;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Build a requirement according to a sequence of command such add OR, add AND, etc.
 */
public class Builder {

    private final Deque<Memento> pool = new LinkedList<>();

    private final RequirementFactory factory;
    private final Deque<Memento> stack;
    private Requirement result;

    public Builder(RequirementFactory factory) {
        this.stack = new LinkedList<>();
        this.factory = factory;
    }

    public boolean isResultReady() {
        return result != null;
    }

    public Requirement getResult() {
        if (!isResultReady()) {
            throw new IllegalStateException("No result available!");
        }
        return result;
    }

    public void closeOperator() {
        if (!canCloseOperator()) {
            throw new IllegalStateException("Cannot close the current operator");
        }
        
        buildOperator();
        backtrack();
    }
    
    public boolean canCloseOperator() {
        return !stack.isEmpty() && notOnNegation() && atLeastTwoOperands();
    }

    private boolean atLeastTwoOperands() {
        return stack.peek().results.size() >= 2;
    }

    private boolean notOnNegation() {
        return stack.peek().role != ROLE.NEGATION;
    }

    private void buildOperator() {
        Memento operator = stack.pop();
        switch (operator.role) {
            case CONJUNCTION:
                result = factory.createConjunction(operator.results);
                break;
            case DISJUNCTION:
                result = factory.createDisjunction(operator.results);
                break;
            default:
                throw new IllegalStateException("Cannot close a negation operator");

        }
    }

    public boolean isRunning() {
        return !stack.isEmpty();
    }

    public void addRequire(int index) {
        result = factory.createRequire(index);
        backtrack();
    }

    private void backtrack() {
        while (!stack.isEmpty() && result != null) {
            final Memento parent = stack.pop();
            switch (parent.role) {
                case CONJUNCTION:
                case DISJUNCTION:
                    parent.results.add(result);
                    stack.push(parent);
                    result = null;
                    break;
                case NEGATION:
                    result = factory.createNegation(result);
                    free(parent);
                    break;
            }
        }
    }

    public void openConjunction() {
        stack.push(get(ROLE.CONJUNCTION));
    }

    public void openDisjunction() {
        stack.push(get(ROLE.DISJUNCTION));
    }

    public void addNot() {
        stack.push(get(ROLE.NEGATION));
    }
    
    public List<Command> getAllowedCommands() {
        if (isResultReady()) {
            return new ArrayList<>(0);
        }
        
        List<Command> commands = new ArrayList<>(COMMAND_COUNT);
        commands.add(Command.ADD_REQUIRE);
        commands.add(Command.ADD_NEGATION);
        commands.add(Command.OPEN_CONJUNCTION);
        commands.add(Command.OPEN_DISJUNCTION);
        if (canCloseOperator()) {
            commands.add(Command.CLOSE);
        }
        
        return commands;
    }
    
    private  static final int COMMAND_COUNT = Command.values().length;
    
    protected int getLocalBranchCount() {
        if (stack.isEmpty()) return 0;
        return stack.peek().results.size();
    }

    private Memento get(ROLE role) {
        if (pool.isEmpty()) {
            final Memento prebuilt = new Memento(role);
            return prebuilt;
        } else {
            Memento prebuilt = pool.pop();
            prebuilt.reset(role);
            return prebuilt;
        }
    }

    private void free(Memento memento) {
        memento.release();
        pool.push(memento);
    }

    private static enum ROLE {
        CONJUNCTION,
        DISJUNCTION,
        NEGATION,
    }

    private static class Memento {

        ROLE role;
        List<Requirement> results;

        private Memento(ROLE role) {
            this.role = role;
            this.results = new LinkedList<>();
        }

        public void reset(ROLE role) {
            this.role = role;
            this.results = new LinkedList<>();
        }

        public void release() {
            this.role = null;
            this.results = null;
        }

    }

}
