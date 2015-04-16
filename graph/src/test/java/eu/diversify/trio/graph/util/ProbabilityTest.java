
package eu.diversify.trio.graph.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import org.junit.Test;

/**
 * Specification of the Probability value objects
 */
public class ProbabilityTest {
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldNeverBeNegative() {
        new Probability(-0.2);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldNeverBeAboveOne() {
        new Probability(1.3);
    }
    
    @Test
    public void shouldExposeTheActualValue() {
        final double errorTolerance = 1e-9; 
        final double value = 0.5;

        Probability p = new Probability(value); 
        
        assertThat(p.value(), is(closeTo(value, errorTolerance))); 
    }
    
    
}
