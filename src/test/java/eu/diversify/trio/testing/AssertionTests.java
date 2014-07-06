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

package eu.diversify.trio.testing;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static eu.diversify.trio.testing.DidShowRobustness.didShowRobustness;
import static eu.diversify.trio.testing.DidShowCopyright.didShowCopyright;
import static org.hamcrest.MatcherAssert.*;

/**
 * Specification of the RunInThread objects
 */
@RunWith(JUnit4.class)
public class AssertionTests extends TestCase {
    
    
    private static class DummyRun extends Run {

        private final String stdout;
        private final String stderr;
        
        public DummyRun(String stdout, String stderr) {
            this.stdout = stdout;
            this.stderr = stderr;
        }
        
        @Override
        public String getStandardError() {
            return this.stderr;
        }

        @Override
        public String getStandardOutput() {
            return this.stdout;
        }

        @Override
        public String toString() {
            return "DummyRun{" + "stdout=" + stdout + ", stderr=" + stderr + '}';
        }
        
    }

    
    @Test
    public void didShowRobustnessShouldAcceptCorrectRobustnessValue() {
        String inputText = "Robustness: 89.23 %";
        
        Run run = new DummyRun(inputText, "not relevant");
                
        assertThat(run, didShowRobustness(89.23,  1e-3));
    }
    
    @Test(expected = AssertionError.class)
    public void didShowRobustnessShouldRejectWrongRobustnessValue() {
        String inputText = "Robustness: 89.23 %";
        
        Run run = new DummyRun(inputText, "not relevant");
                
        assertThat(run, didShowRobustness(25.5, 1e-3));
    }
    
    @Test
    public void didShowRobustnessShouldhandleMultiLinesText() {
        String inputText = 
                "Some header text\r\n"
                + "Robustness: 89.23 %\r\n"
                + "Some footer text";
        
        Run run = new DummyRun(inputText, "not relevant");
                
        assertThat(run, didShowRobustness(89.23, 1e-3));
    }
    
    @Test
    public void didShowCopyrightShouldAcceptCorrectYearAndOwner() {
        String inputText = "Copyright (C) 2014 - Foo Inc.";
        
        Run run = new DummyRun(inputText, "not relevant");
                
        assertThat(run, didShowCopyright(2014, "Foo Inc."));  
    }
    

}