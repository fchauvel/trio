/**
 *
 * This file is part of TRIO.
 *
 * TRIO is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * TRIO is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO. If not, see <http://www.gnu.org/licenses/>.
 */
package eu.diversify.trio.acceptance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Check that the file is properly packaged and distributed
 */
@RunWith(Parameterized.class)
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

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> samples() {
        final Collection<Object[]> results = new ArrayList<Object[]>();

        results.add(new Object[]{"sensapp", "samples/sensapp.trio"});
        results.add(new Object[]{"UCC topo. no.1", "samples/sensapp_topo1.trio"});
        results.add(new Object[]{"UCC topo. no.2", "samples/sensapp_topo2.trio"});
        results.add(new Object[]{"UCC topo. no.3", "samples/sensapp_topo3.trio"});
        results.add(new Object[]{"UCC topo. no.4", "samples/sensapp_topo4.trio"});

        return results;
    }

    private final String name;
    private final String pathToFile;

    public AcceptanceIT(String name, String pathToFile) {
        this.name = name;
        this.pathToFile = pathToFile;
    }

    @Test
    public void test() throws IOException, InterruptedException {
        final TrioRequest request = new TrioRequest();
        request.setPathToTopology(pathToFile);
        request.setDestination(ToolBox.randomName(10));

        final TrioResponse response = request.execute();
        System.out.println(response);

        assertThat("unexpected errors" + response.toString(), !response.hasError());

    }

}
