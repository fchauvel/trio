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

import eu.diversify.trio.core.requirements.CachedFactory;
import eu.diversify.trio.core.requirements.Requirement;
import eu.diversify.trio.core.requirements.random.CachedLiteralFactory;
import eu.diversify.trio.core.requirements.random.Goal;
import eu.diversify.trio.core.requirements.stats.LeafCount;
import eu.diversify.trio.core.requirements.stats.NodeCount;
import java.util.Random;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static eu.diversify.trio.core.Evaluation.evaluate;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 */
@RunWith(JUnit4.class)
public class RandomTest {

    @Test
    //@Ignore
    public void resultShouldHaveTheCorrectSize() {
        final int SIZE = 10000;

        final Requirement requirement = new Goal(65, new CachedFactory()).build(SIZE);
        final LeafCount leafCount = new LeafCount();

        evaluate(leafCount).on(requirement); 

        assertThat(leafCount.get(), is(equalTo(SIZE)));
    }

    @Test
    public void testAccumulation() {
        final int RUN_COUNT = 10000;
        final int SIZE_LIMIT = 100;
        final int VARIABLE_CAPACITY = 10000;

        final Goal goal = new Goal(VARIABLE_CAPACITY, new CachedLiteralFactory(10000));

        final CachedFactory factory = new CachedFactory();
        final Requirement[] results = new Requirement[RUN_COUNT];

        long start = System.currentTimeMillis();
        for (int runIndex = 0; runIndex < RUN_COUNT; runIndex++) {
            int size = new Random().nextInt(SIZE_LIMIT);
            results[runIndex] = goal.build(size);
        }
        long end = System.currentTimeMillis();

        System.out.println(RUN_COUNT + " random requirement built in " + (end - start) + " ms.");
    }

    @Test
    //@Ignore
    public void testSpeed() {
        final int RUN_COUNT = 10000;
        final int REQUIREMENT_SIZE = 10;
        final int VARIABLE_CAPACITY = 10;

        long duration = 0;
        final Goal goal = new Goal(VARIABLE_CAPACITY);
        for (int i = 0; i < RUN_COUNT; i++) {
            duration += onRandomRequirement(goal, REQUIREMENT_SIZE);
        }
        double average = ((double) duration) / RUN_COUNT;

        System.out.println("Random requirement [S=" + REQUIREMENT_SIZE + ", V=" + VARIABLE_CAPACITY + "] built in " + average + " ms");
        //assertThat("Generation of random requirement is too long", average, is(lessThan(100D)));
    }

    private long onRandomRequirement(Goal goal, int size) {
        long start = System.currentTimeMillis();
        goal.build(size);
        long end = System.currentTimeMillis();
        return end - start;
    }

}
