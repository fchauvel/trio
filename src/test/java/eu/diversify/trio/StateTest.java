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
