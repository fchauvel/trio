/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.diversify.trio.graph.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A value object that constrained positive integer values
 */
public class CountTest {
    
    @Test
    public void shouldExposeTheirInnerValue() {
        final int value = 15;
        
        Count count = new Count(value); 
        
        assertThat(count.value(), is(equalTo(value)));
    }
    
    @Test
    public void shouldTestForOddity() {
        final int value = 15;
        Count count = new Count(value);
        
        assertThat(count.isEven(), is(false));
        assertThat(count.isOdd(), is(true));
    }

    
    @Test
    public void shouldTestForEveness() {
        final int value = 15;
        Count count = new Count(value);
        
        assertThat(count.isEven(), is(false));
        assertThat(count.isOdd(), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNegativeValue() {
        final int value = -3;
        
        new Count(value);
    }
    
}
