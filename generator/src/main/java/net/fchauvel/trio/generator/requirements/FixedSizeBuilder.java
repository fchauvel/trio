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

import net.fchauvel.trio.core.requirements.RequirementFactory;
import static net.fchauvel.trio.generator.requirements.Command.*;
import java.util.List;

/**
 * Build a Requirement with a fixed number of leaf references
 */
public class FixedSizeBuilder extends Builder {

    private final int desiredSize;
    private int currentSize;
    private int minimumFinalSize;
    private Command lastCommand;

    /**
     * Create a new require with a fixed number of 'require' operator
     *
     * @param factory The factory to use to build requirements
     * @param desiredSize the number of 'require' the requirement must contain
     */
    public FixedSizeBuilder(RequirementFactory factory, int desiredSize) {
        super(factory);
        this.desiredSize = desiredSize;
        this.currentSize = 0;
        this.minimumFinalSize = 1;
        this.lastCommand = null;
    }

    @Override
    public void addNot() {
        addNegation();
        super.addNot();
        this.lastCommand = ADD_NEGATION;
    }

    @Override
    public void openDisjunction() {
        addBranch();
        super.openDisjunction();
        this.lastCommand = OPEN_DISJUNCTION;
    }

    @Override
    public void openConjunction() {
        addBranch();
        super.openConjunction();
        this.lastCommand = OPEN_CONJUNCTION;
    }

    @Override
    public void addRequire(int index) {
        addLeaf();
        super.addRequire(index);
        lastCommand = ADD_REQUIRE;
    }

    private void addNegation() {
        final int branchCount = getLocalBranchCount();
        if (branchCount < 0) {
            throw new RuntimeException("Invalid branch count '" + branchCount + "'");
        }
        if (branchCount > 1) {
            minimumFinalSize++;

        }
    }

    private void addBranch() {
        final int branchCount = getLocalBranchCount();
        if (branchCount == 0) {
            minimumFinalSize += 1;

        } else if (branchCount == 1) {
            minimumFinalSize += 1;

        } else if (branchCount > 1) {
            minimumFinalSize += 2;

        } else {
            throw new RuntimeException("Invalid branch count " + branchCount);
        }
    }

    private void addLeaf() {
        final int branchCount = getLocalBranchCount();
        if (branchCount == 0) {
            minimumFinalSize += 0;
            currentSize += 1;

        } else if (branchCount == 1) {
            minimumFinalSize += 0;
            currentSize += 1;

        } else if (branchCount > 1) {
            minimumFinalSize += 1;
            currentSize += 1;

        } else {
            throw new RuntimeException("Invalid branch count " + branchCount);
        }
    }
    
    @Override
    public List<Command> getAllowedCommands() {
        assert minimumFinalSize >= currentSize : "Illegal expansion! ";

        List<Command> commands = super.getAllowedCommands();

        if (hasOnlyOneBranch() && isTooSmall()) {
            commands.remove(ADD_REQUIRE);
        }

        if (!isTooSmall() && canCloseOperator()) {
            commands.remove(ADD_REQUIRE);
            commands.remove(ADD_NEGATION);
        }

        if (!isTooSmall() || !hasRoomForANewBranch()) {
            commands.remove(OPEN_CONJUNCTION);
            commands.remove(OPEN_DISJUNCTION);
        }

        if (lastCommand == OPEN_CONJUNCTION || lastCommand == OPEN_DISJUNCTION || lastCommand == ADD_NEGATION) {
            commands.remove(lastCommand);
        }

        if (isFilled() && isTooSmall()) {
            commands.remove(CLOSE);
        }

        assert !(isTooSmall() && commands.isEmpty()) : "No action can be triggered anymore! " + summary();
        return commands;
    }

    private String summary() {
        return String.format("[TFS = %d ; MFS = %d ; CS = %d]", desiredSize, minimumFinalSize, currentSize);
    }

    public boolean hasRoomForANewBranch() {
        int costOfNewBranch = getLocalBranchCount() < 2 ? 1 : 2;
        return desiredSize - minimumFinalSize >= costOfNewBranch;
    }

    public boolean isFilled() {
        return currentSize == minimumFinalSize;
    }

    private boolean isTooSmall() {
        return minimumFinalSize < desiredSize;
    }

    private boolean hasOnlyOneBranch() {
        return minimumFinalSize == 1;
    }

}
