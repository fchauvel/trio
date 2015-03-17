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
package eu.diversify.trio.core.requirements.random;

import eu.diversify.trio.core.requirements.Nothing;
import eu.diversify.trio.core.requirements.RequirementFactory;
import eu.diversify.trio.core.requirements.Requirement;
import java.util.List;
import java.util.Random;

/**
 * Generate a random sequence of commands, sent to the builder
 */
public final class Goal {

    private int variableCount;
    private final Random random;
    private final Builder generation;

    public Goal(int variableCount) {
        this(variableCount, new CachedLiteralFactory(variableCount));
    }

    public Goal(int variableCount, RequirementFactory factory) {
        this.variableCount = variableCount;
        this.random = new Random();
        this.generation = new Builder(factory);

    }

    public void setVariableCount(int variableCount) {
        this.variableCount = variableCount;
    }

    public Requirement build(int desiredSize) {
        if (desiredSize == 0) {
            return Nothing.getInstance();
        }

        generation.reset(desiredSize);
        do {
            final Command command = pickAny(generation.possibleCommands());
            command.randomize(this);
            command.sendTo(generation);
        } while (generation.isRunning());

        return generation.getResult();
    }

    private Command pickAny(List<Command> candidates) {
        if (candidates.isEmpty()) {
            throw new IllegalArgumentException("Cannot pick from an empty collection");
        }
        if (candidates.size() == 1) {
            return candidates.get(0);
        }
        return candidates.get(random.nextInt(candidates.size()));
    }

    public int randomLeafIndex() {
        return random.nextInt(variableCount);
    }

}
