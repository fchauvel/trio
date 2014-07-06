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



package eu.diversify.trio;

import java.io.IOException;
import junit.framework.TestCase;
import org.cloudml.codecs.library.CodecsLibrary;
import org.cloudml.core.Deployment;
import org.cloudml.core.samples.SensApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * A test to play with!
 */
@RunWith(JUnit4.class)
public class SandboxTest extends TestCase {

    @Test
    public void testSensapp() throws IOException {
        Deployment sensapp = SensApp.completeSensApp().build();
        new CodecsLibrary().saveAs(sensapp, "sensapp.json");
        
        SequenceGroup sequences = new Simulator(new TypeLevel(sensapp)).randomExtinctions(1000);
                
        System.out.println(sequences.summary());
        sequences.toCsvFile("extinction_sequence.csv");
    }
    
}
