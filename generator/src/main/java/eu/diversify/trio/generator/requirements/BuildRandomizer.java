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
package eu.diversify.trio.generator.requirements;

import eu.diversify.trio.core.requirements.Requirement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Randomize the construction of a given build
 */
public class BuildRandomizer {

    public static List<Integer> range(int count) {
        return range(0, count - 1);
    }

    public static List<Integer> range(int start, int end) {
        final List<Integer> range = new ArrayList<>(end - start);
        for (int index = start; index <= end; index++) {
            range.add(index);
        }
        return range;
    }

    private final Builder builder;
    private final Random random;
    private final Iterator<Integer> indices;

    public BuildRandomizer(Builder builder, Random random, List<Integer> indices) {
        if (builder == null) {
            throw new IllegalArgumentException("Invalid builder ('null' found)");
        }

        this.builder = builder;
        this.random = random;
        this.indices = indices.iterator();
    }

    public Requirement build() {
        while (!builder.isResultReady()) {
            final List<Command> candidates = builder.getAllowedCommands();
            final Command command = chooseAny(candidates);
            execute(command);
        }
        return builder.getResult();
    }

    protected void execute(Command command) {
        switch (command) {
            case OPEN_CONJUNCTION:
                builder.openConjunction();
                break;
            case OPEN_DISJUNCTION:
                builder.openDisjunction();
                break;
            case CLOSE:
                builder.closeOperator();
                break;
            case ADD_NEGATION:
                builder.addNot();
                break;
            case ADD_REQUIRE:
                builder.addRequire(indices.next());
                break;
        }
    }

    private <T> T chooseAny(List<T> candidates) {
        assert !candidates.isEmpty() : "Cannot choose from an empty collection";

        if (candidates.size() == 1) {
            return candidates.get(0);
        }
        return candidates.get(random.nextInt(candidates.size()));
    }

}
