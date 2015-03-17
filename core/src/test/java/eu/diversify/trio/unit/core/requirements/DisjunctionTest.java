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


package eu.diversify.trio.unit.core.requirements;

import eu.diversify.trio.core.DefaultAssemblyVisitor;
import eu.diversify.trio.core.AssemblyVisitor;
import eu.diversify.trio.core.requirements.Conjunction;
import eu.diversify.trio.core.requirements.Disjunction;
import eu.diversify.trio.core.requirements.Require;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Specification of the logical disjunction
 */
@RunWith(JUnit4.class)
public class DisjunctionTest {

    @Test
    public void beginShouldTriggerEnterOnTheVisitor() {
        final Mockery context = new JUnit4Mockery();

        final Disjunction disjunction = RequirementSamples.disjunction();

        final AssemblyVisitor visitor = context.mock(AssemblyVisitor.class);

        context.checking(new Expectations() {
            {
                oneOf(visitor).enter(disjunction);
            }
        });

        disjunction.begin(visitor);

        context.assertIsSatisfied();
    }


    @Test
    public void endShouldTriggerEnterOnTheVisitor() {
        final Mockery context = new JUnit4Mockery();
 
        final Disjunction disjunction =  RequirementSamples.disjunction();  

        final AssemblyVisitor visitor = context.mock(AssemblyVisitor.class);

        context.checking(new Expectations() {
            {
                oneOf(visitor).exit(disjunction);
            }
        });

        disjunction.end(visitor);

        context.assertIsSatisfied();
    }
    
}
