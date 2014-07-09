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
package eu.diversify.trio.acceptance;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Check that the file is properly packaged and distributed
 */
@RunWith(JUnit4.class)
public class AcceptanceIT extends TestCase {

    
    @Test
    public void test() {
        final TrioRequest request = new TrioRequest();
        request.setInputFile("samples/sensapp/basic.trio");
         
        final TrioResponse response = request.execute();
        
        assertThat("version", response.getVersion(), is(equalTo("0.1"))); 
        assertThat("copyright owner", response.getCopyrightOwner(), is(equalTo("SINTEF ICT")));
        assertThat("copyright year", response.getCopyrightYear(), is(equalTo("2014"))); 
        assertThat("unexpected errors" + response.toString(), !response.hasError());
        assertThat("robustness", response.getRobustness(), is(closeTo(0.5, 1e-7)));
        
    }
}
