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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


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

        results.add(new Object[]{"UCC sensapp", "'*'", "'*'", "samples/sensapp.trio"});
        results.add(new Object[]{"UCC topo. no.1", "'*'", "'*'", "samples/sensapp_topo1.trio"});
        results.add(new Object[]{"UCC topo. no.1 service/infra", "service", "infra", "samples/sensapp_topo1.trio"});
        results.add(new Object[]{"UCC topo. no.1 service/platform", "service", "platform", "samples/sensapp_topo1.trio"});
        
        results.add(new Object[]{"UCC topo. no.2", "'*'", "'*'", "samples/sensapp_topo2.trio"});
        results.add(new Object[]{"UCC topo. no.2 service/infra", "service", "infra", "samples/sensapp_topo2.trio"});
        results.add(new Object[]{"UCC topo. no.2 service/platform", "service", "platform", "samples/sensapp_topo2.trio"});
        
        results.add(new Object[]{"UCC topo. no.3", "'*'", "'*'", "samples/sensapp_topo3.trio"});
        results.add(new Object[]{"UCC topo. no.3 service/infra", "service", "infra", "samples/sensapp_topo3.trio"});
        results.add(new Object[]{"UCC topo. no.3 service/platform", "service", "platform", "samples/sensapp_topo3.trio"});
        
        results.add(new Object[]{"UCC topo. no.4", "'*'", "'*'", "samples/sensapp_topo4.trio"});
        results.add(new Object[]{"UCC topo. no.4 service/infra", "service", "infra", "samples/sensapp_topo4.trio"});
        results.add(new Object[]{"UCC topo. no.4 service/platform", "service", "platform", "samples/sensapp_topo4.trio"});
      
        results.add(new Object[]{"UCC topo. no.5", "'*'", "'*'", "samples/sensapp_topo5.trio"});
        results.add(new Object[]{"UCC topo. no.5 service/infra", "service", "infra", "samples/sensapp_topo5.trio"});
        results.add(new Object[]{"UCC topo. no.5 service/platform", "service", "platform", "samples/sensapp_topo5.trio"});
              
        results.add(new Object[]{"UCC sensapp types", "'*'", "'*'", "samples/sensapp_types.trio"});

        return results;
    }

    private final String name;
    private final TrioRequest request;

    public AcceptanceIT(String name,  String observation, String control, String pathToFile) {
        this.name = name;
        this.request = new TrioRequest(pathToFile, ToolBox.randomName(15) + ".csv", observation, control);
    }

    @Test
    public void test() throws IOException, InterruptedException {
        final TrioResponse response = request.execute();
        System.out.println(response);

        response.assertNoError();
    }

}
