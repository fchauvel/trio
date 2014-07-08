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

package eu.diversify.trio;

import eu.diversify.trio.State;
import eu.diversify.trio.Action;
import eu.diversify.trio.actions.Inactivate;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Specification of a state, which is an entry in a trace
 */
@RunWith(JUnit4.class)
public class StateTest extends TestCase{
    
    @Test
    public void shouldHandleInactivationProperly() {
        final int disruption = 3;
        final int stillActive = 10;
        final int loss = 1;
        
        final State sut = new State(new Inactivate("A"), disruption, stillActive, loss);
        
        final Action trigger = new Inactivate("B");
        final State next = sut.update(trigger, 8);
                
        assertThat("action", next.getTrigger(), is(equalTo(trigger))); 
        assertThat("inactivated", next.getDisruptionLevel(), is(equalTo(disruption + 1)));
        assertThat("still active", next.getActivityLevel(), is(equalTo(8)));
        assertThat("loss", next.getLoss(), is(equalTo(2)));
    }
    

}
