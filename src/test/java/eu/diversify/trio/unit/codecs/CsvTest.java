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

package eu.diversify.trio.unit.codecs;

import eu.diversify.trio.codecs.CSV;
import eu.diversify.trio.data.Trace;
import java.lang.System;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import static eu.diversify.trio.simulation.actions.AbstractAction.*;

/**
 * Specification of the CSV format
 */
@RunWith(JUnit4.class)
public class CsvTest extends TestCase {

    
    @Test
    public void shouldConvertProperly() {
        final CSV csv = new CSV();
        
        final int sequenceIndex = 1;
        String csvSnippet = csv.convert(sequenceIndex, "inactivate X", 1, 23, 3);
        
        assertThat(csvSnippet, is(equalTo("1, inactivate X, 1, 23, 3")));
        
    }
    
    @Test
    public void shouldFormatTraceProperly() {
        final CSV csv = new CSV();
        final Trace trace = new Trace(23);
        
        trace.record(inactivate("A"), 22);
        trace.record(inactivate("B"), 12);
        trace.record(inactivate("C"), 8);
        trace.record(inactivate("D"), 0);
        
        final int index = 1;
        final String csvSnippet = trace.to(csv, index);
        
        final String EOL = System.lineSeparator();
        final String expectation = 
                "1, none, 0, 23, 0" + EOL +
                "1, inactivate A, 1, 22, 1" + EOL +
                "1, inactivate B, 2, 12, 10" + EOL +
                "1, inactivate C, 3, 8, 4" + EOL + 
                "1, inactivate D, 4, 0, 8" + EOL;
        
        assertThat(csvSnippet, is(equalTo(expectation)));
    }
    
}
