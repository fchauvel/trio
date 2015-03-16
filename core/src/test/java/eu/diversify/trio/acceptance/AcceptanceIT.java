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

import eu.diversify.trio.acceptance.driver.TrioRequest;
import eu.diversify.trio.acceptance.driver.TrioResponse;
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

        results.add(new Object[]{"UCC sensapp", "'*'", "'*'", "samples/sensapp.trio", 0.15});
        results.add(new Object[]{"UCC topo. no.1", "'*'", "'*'", "samples/sensapp_topo1.trio", 0.14});
        results.add(new Object[]{"UCC topo. no.1 service/infra", "service", "infra", "samples/sensapp_topo1.trio", 0.2});
        results.add(new Object[]{"UCC topo. no.1 service/platform", "service", "platform", "samples/sensapp_topo1.trio", 0.14});
        
        results.add(new Object[]{"UCC topo. no.2", "'*'", "'*'", "samples/sensapp_topo2.trio", 0.15});
        results.add(new Object[]{"UCC topo. no.2 service/infra", "service", "infra", "samples/sensapp_topo2.trio", 0.2});
        results.add(new Object[]{"UCC topo. no.2 service/platform", "service", "platform", "samples/sensapp_topo2.trio", 0.13});
        
        results.add(new Object[]{"UCC topo. no.3", "'*'", "'*'", "samples/sensapp_topo3.trio", 0.15});
        results.add(new Object[]{"UCC topo. no.3 service/infra", "service", "infra", "samples/sensapp_topo3.trio", 0.17});
        results.add(new Object[]{"UCC topo. no.3 service/platform", "service", "platform", "samples/sensapp_topo3.trio", 0.01});
        
        results.add(new Object[]{"UCC topo. no.4", "'*'", "'*'", "samples/sensapp_topo4.trio", 0.19});
        results.add(new Object[]{"UCC topo. no.4 service/infra", "service", "infra", "samples/sensapp_topo4.trio", 0.32});
        results.add(new Object[]{"UCC topo. no.4 service/platform", "service", "platform", "samples/sensapp_topo4.trio", 0.14});
      
        results.add(new Object[]{"UCC topo. no.5", "'*'", "'*'", "samples/sensapp_topo5.trio", 0.20});
        results.add(new Object[]{"UCC topo. no.5 service/infra", "service", "infra", "samples/sensapp_topo5.trio", 0.3});
        results.add(new Object[]{"UCC topo. no.5 service/platform", "service", "platform", "samples/sensapp_topo5.trio", 0.13});
              
        results.add(new Object[]{"UCC sensapp types", "'*'", "'*'", "samples/sensapp_types.trio", 0.26});

        return results;
    }

    private final String name;
    private final TrioRequest request;
    private final double robustness;

    public AcceptanceIT(String name,  String observation, String control, String pathToFile, double robustness) {
        this.name = name;
        this.request = new TrioRequest(pathToFile, ToolBox.randomName(15) + ".csv", observation, control);
        this.robustness = robustness;
    }

    @Test
    public void userAcceptance() throws IOException, InterruptedException {
        final TrioResponse response = request.execute();
        System.out.println(response);

        response.assertNoError();
        response.assertRobustnessAbout(robustness, TOLERANCE);
    }
    
    private static final double TOLERANCE = 1e-1;

}
