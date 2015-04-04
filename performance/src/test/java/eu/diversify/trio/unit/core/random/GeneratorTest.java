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

package eu.diversify.trio.unit.core.random;

import eu.diversify.trio.core.Assembly;
import static eu.diversify.trio.core.Evaluation.evaluate;
import eu.diversify.trio.core.random.Generator;
import eu.diversify.trio.core.statistics.Density;
import eu.diversify.trio.util.random.Distribution;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import org.junit.Ignore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *
 */
@RunWith(JUnit4.class)
public class GeneratorTest {

    @Test
    public void durationForLargeSystems() {
        final int RUN_COUNT = 100; // Should 100
        final int COMPONENT_COUNT = 1000; // Should be 1000

        final Generator random = new Generator();

        System.out.println("Generation of " + RUN_COUNT + " system(s) of " + COMPONENT_COUNT + " component(s).");
      
        long totalDuration = 0;
        for (int index = 0; index < RUN_COUNT; index++) {      
            final Distribution distribution = Distribution.uniform(0, COMPONENT_COUNT);

            final long start = System.currentTimeMillis();
            random.assembly(COMPONENT_COUNT, distribution);
            final long end = System.currentTimeMillis();

            final long duration = end - start;
      
            totalDuration += duration;
        }

        double averageInSeconds = ((double) totalDuration) / RUN_COUNT / 1000 ;
        System.out.println();
        System.out.println("Average duration: " + averageInSeconds + " s.");
    }
    
    
    @Test
    public void shouldControlTheDensity() {
        final int ASSEMBLY_SIZE = 1000;
        final double DENSITY = 0.1;
        final double TOLERANCE = 1.5e-1;

        final Generator random = new Generator();
        Assembly assembly = random.assembly(ASSEMBLY_SIZE, DENSITY);
        
        Density density = new Density();
        evaluate(density).on(assembly);
        
        assertThat(density.getValue(), is(closeTo(DENSITY, TOLERANCE)));
    }

}
