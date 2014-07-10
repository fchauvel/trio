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

import java.io.IOException;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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

    @BeforeClass
    public static void install() throws IOException {
        Configuration config = Configuration.forTest();
        ToolBox.unzipTo(config.pathToArchive(), config.workingDirectory());
    }

    @AfterClass
    public static void clean() {
        Configuration config = Configuration.forTest();
        ToolBox.delete(config.installationDirectory());
    }

    @Test
    public void test() throws IOException, InterruptedException {
        final TrioRequest request = new TrioRequest();
        request.setPathToTopology("samples/sensapp.trio");
        request.setDestination("test.csv");

        final TrioResponse response = request.execute();

        //assertThat("version", response.getVersion(), is(equalTo("0.1"))); 
        //assertThat("copyright owner", response.getCopyrightOwner(), is(equalTo("SINTEF ICT")));
        //assertThat("copyright year", response.getCopyrightYear(), is(equalTo("2014"))); 
        assertThat("unexpected errors" + response.toString(), !response.hasError());
        //assertThat("robustness", response.getRobustness(), is(closeTo(0.5, 1e-7)));

    }
    
     @Test
    public void sandbox() throws IOException, InterruptedException {
        final TrioRequest request = new TrioRequest();
        request.setPathToTopology("samples/sensapp_topo2.trio");
        request.setDestination("test.csv");

        final TrioResponse response = request.execute();
        System.out.println(response);
        
        //assertThat("version", response.getVersion(), is(equalTo("0.1"))); 
        //assertThat("copyright owner", response.getCopyrightOwner(), is(equalTo("SINTEF ICT")));
        //assertThat("copyright year", response.getCopyrightYear(), is(equalTo("2014"))); 
        assertThat("unexpected errors", !response.hasError());
        //assertThat("robustness", response.getRobustness(), is(closeTo(0.5, 1e-7)));

    }
    
}
