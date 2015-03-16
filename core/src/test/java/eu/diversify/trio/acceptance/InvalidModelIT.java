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

import eu.diversify.trio.Configuration;
import eu.diversify.trio.acceptance.driver.TrioRequest;
import eu.diversify.trio.acceptance.driver.TrioResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * This test suite checks that errors in input Trio models are properly reported to
 * user
 */
@RunWith(Parameterized.class)
public class InvalidModelIT {

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

        results.add(new Object[]{"Unknown component", "samples/invalid/unknown_component.trio"});

        results.add(new Object[]{"Unknown component in tag section", "samples/invalid/unknown_component_on_tags.trio"});
        
        results.add(new Object[]{"Unknown keyword", "samples/invalid/unknown_keyword.trio"});
     
        return results;
    }

    private final String name;
    private final TrioRequest request;
    
    public InvalidModelIT(String name, String pathToFile) {
        this.name = name;
        this.request = new TrioRequest(pathToFile, ToolBox.randomName(15) + ".csv", "'*'", "'*'");
    }

    @Test
    public void userAcceptance() throws IOException, InterruptedException {
        final TrioResponse response = request.execute();
        System.out.println(response);

        response.assertInvalidSystemDetected();
    }

}
