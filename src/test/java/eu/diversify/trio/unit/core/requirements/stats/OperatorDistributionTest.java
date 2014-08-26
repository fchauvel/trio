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
package eu.diversify.trio.unit.core.requirements.stats;

import eu.diversify.trio.core.requirements.Requirement;
import eu.diversify.trio.core.requirements.stats.OperatorDistribution;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static eu.diversify.trio.core.Evaluation.evaluate;
import static eu.diversify.trio.core.requirements.Factory.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Specification of the operator distribution
 */
@RunWith(Parameterized.class)
public class OperatorDistributionTest {

    private final String testName;
    private final Requirement requirement;
    private final int[] expectations;

    public OperatorDistributionTest(String testName, Requirement requirement, int[] expectations) {
        this.testName = testName;
        this.requirement = requirement;
        this.expectations = expectations;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> examples() {
        final List<Object[]> examples = new ArrayList<Object[]>();

        examples.add(new Object[]{"require", require("X"), new int[]{0,1,0,0,0}});
        examples.add(new Object[]{"nothing", nothing(), new int[]{1,0,0,0,0}});
        examples.add(new Object[]{"and", require("X").and(require("Y")), new int[]{0,2,0,0,1}});
        examples.add(new Object[]{"or", require("X").or(require("Y")), new int[]{0,2,0,1,0}});
        examples.add(new Object[]{"not", not(require("X").or(require("Y"))), new int[]{0,2,1,1,0}});

        return examples;
    }
    
    @Test
    public void shouldCountOperatorsCorrectly() {
        final OperatorDistribution distribution = new OperatorDistribution();
        
        evaluate(distribution).on(requirement);
        
        assertThat(distribution.counts(), is(equalTo(expectations)));
    }

}
