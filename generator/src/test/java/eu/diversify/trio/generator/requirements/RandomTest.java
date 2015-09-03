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

import eu.diversify.trio.core.requirements.Requirement;
import eu.diversify.trio.generator.requirements.CachedLiteralFactory;
import eu.diversify.trio.core.requirements.stats.LeafCount;
import java.util.Random;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static eu.diversify.trio.core.Evaluation.evaluate;
import eu.diversify.trio.generator.requirements.BuildRandomizer;
import eu.diversify.trio.generator.requirements.Builder;
import eu.diversify.trio.generator.requirements.Command;
import eu.diversify.trio.generator.requirements.FixedSizeBuilder;
import java.util.LinkedList;
import java.util.List;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 */
@RunWith(JUnit4.class)
public class RandomTest {

    @Test
    //@Ignore
    public void shouldBuildRequirementsWithTwoVariables() {
        final int SIZE = 2;
        final int CAPACITY = 25;

        Requirement requirement = buildRequirement(CAPACITY, SIZE);

        LeafCount leafCount = leafCount(requirement);
        assertThat(leafCount.get(), is(equalTo(SIZE)));
    }

    private LeafCount leafCount(Requirement requirement) {
        final LeafCount leafCount = new LeafCount();
        evaluate(leafCount).on(requirement);
        return leafCount;
    }

    private Requirement buildRequirement(final int CAPACITY, final int SIZE) {
        //final LoggedRandomizer randomRequirement = prepareLoggedBuilder(CAPACITY, SIZE);
        final BuildRandomizer randomRequirement = prepareLoggedBuilder(CAPACITY, SIZE);
        final Requirement requirement = randomRequirement.build();
        return requirement;
    }

    @Test
    //@Ignore
    public void resultShouldHaveTheCorrectSize() {
        final int CAPACITY = 10000;
        final int SIZE = 10000;

        Requirement requirement = buildRequirement(CAPACITY, SIZE);
        LeafCount leafCount = leafCount(requirement);

        //assertThat(randomRequirement.summary(), leafCount.get(), is(equalTo(SIZE)));
        assertThat(leafCount.get(), is(equalTo(SIZE)));
    }

    public static BuildRandomizer prepareBuilder(int capacity, int size) {
        return new BuildRandomizer(new FixedSizeBuilder(new CachedLiteralFactory(capacity), size), new Random(), BuildRandomizer.range(capacity));
    }

    public static LoggedRandomizer prepareLoggedBuilder(int capacity, int size) {
        return new LoggedRandomizer(new FixedSizeBuilder(new CachedLiteralFactory(capacity), size), new Random(), BuildRandomizer.range(capacity));
    }

    @Test
    public void testAccumulation() {
        final int RUN_COUNT = 10000;
        final int SIZE = 100;
        final int CAPACITY = 10000;

        final BuildRandomizer randomRequirement = prepareBuilder(CAPACITY, SIZE);

        final Requirement[] results = new Requirement[RUN_COUNT];

        long start = System.currentTimeMillis();
        for (int runIndex = 0; runIndex < RUN_COUNT; runIndex++) {
            results[runIndex] = randomRequirement.build();
        }
        long end = System.currentTimeMillis();

        System.out.println(RUN_COUNT + " random requirement built in " + (end - start) + " ms.");
    }

    @Test
    //@Ignore
    public void testSpeed() {
        final int RUN_COUNT = 10000;
        final int SIZE = 5000;
        final int CAPACITY = 10000;

        final BuildRandomizer randomRequirement = prepareBuilder(CAPACITY, SIZE);

        long totalDuration = 0;
        for (int i = 0; i < RUN_COUNT; i++) {
            long start = System.currentTimeMillis();
            randomRequirement.build();
            long end = System.currentTimeMillis();
            totalDuration += end - start;
        }
        double average = ((double) totalDuration) / RUN_COUNT;

        System.out.println("Random requirement [S=" + SIZE + ", V=" + CAPACITY + "] built in " + average + " ms");

    }

    private static class LoggedRandomizer extends BuildRandomizer {

        private final List<Command> history;

        public LoggedRandomizer(Builder builder, Random random, List<Integer> indices) {
            super(builder, random, indices);
            history = new LinkedList<>();
        }

        @Override
        protected void execute(Command command) {
            super.execute(command);
            history.add(command);
        }

        public String summary() {
            return history.toString();
        }

    }

}
