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

package eu.diversify.trio.acceptance.driver;

import eu.diversify.trio.Configuration;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Request the execution of Trio
 */
public class TrioRequest {
    
    private final Configuration config;
    private String pathToTopology;
    private String destination;
    private String observation;
    private String control;
        
    public TrioRequest(String path, String trace, String observation, String control) {
        this.config = Configuration.forTest();
        this.pathToTopology = path;
        this.destination = trace;
        this.observation = observation;
        this.control = control;
    }
    public String getPathToTopology() {
        return (pathToTopology == null) ? "" : pathToTopology;
    }
    
    public String getDestination() {
        return (destination == null)? "" : destination;
    }    

    public String getObservation() {
        return observation;
    }

    public String getControl() {
        return control;
    }
    
    
    public TrioResponse execute() throws IOException, InterruptedException {
        String command = String.format("java -jar %s -o %s -c %s -t %s %s", config.jarFile(), observation, control, destination, pathToTopology);
        RunAsThread run = new RunAsThread(config.installationDirectory(), command.split("\\s+"));
        return new TrioResponse(run);
    } 

    public void deleteGeneratedFiles() throws IOException {
        Files.delete(Paths.get(config.installationDirectory()));
    }

}
