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
import eu.diversify.trio.core.requirements.stats.VariableCount;
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
 * Specification of the variable counter
 */
@RunWith(Parameterized.class)
public class VariableCountTest {
    
    private final String testName;
    private final Requirement requirement;
    private final int expectation;

    public VariableCountTest(String testName, Requirement requirement, int expectation) {
        this.testName = testName;
        this.requirement = requirement;
        this.expectation = expectation;
    }
    
    
    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> examples() {
        final List<Object[]> examples = new ArrayList<Object[]>();
        
        examples.add(new Object[]{"require", require("X"), 1});
        examples.add(new Object[]{"nothing", nothing(), 0});
        examples.add(new Object[]{"disjunction", require("X").or(require("Y")), 2});
        examples.add(new Object[]{"conjunction", require("X").and(require("Y")), 2});
        examples.add(new Object[]{"negation", not(require("X")), 1});
        examples.add(new Object[]{"disjunction with duplicates", require("X").or(require("X")), 1});
        
        return examples;
    }
    
    
    @Test
    public void shouldCountVariableCorrectly() {
        final VariableCount count = new VariableCount();
        
        evaluate(count).on(requirement);
        
        assertThat(count.get(), is(equalTo(expectation)));
    }
    
    

}
