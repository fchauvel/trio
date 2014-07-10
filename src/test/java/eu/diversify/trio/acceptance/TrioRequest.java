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
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Request the execution of Trio
 */
public class TrioRequest {
    
    private final Configuration config;
    private String pathToTopology;
    private String destination;
    
    
    public TrioRequest() {
        this.config = Configuration.forTest();
    }

    public String getPathToTopology() {
        return (pathToTopology == null) ? "" : pathToTopology;
    }

    public void setPathToTopology(String pathToTopology) {
        this.pathToTopology = pathToTopology;
    }
    
    public String getDestination() {
        return (destination == null)? "" : destination;
    }
    
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public TrioResponse execute() throws IOException, InterruptedException {
        String command = String.format("java -jar %s --output=%s %s", config.jarFile(), destination, pathToTopology);
        Run run = new Run(config.installationDirectory(), command.split("\\s+"));
        return new TrioResponse(run);
    } 

    public void deleteGeneratedFiles() throws IOException {
        Files.delete(Paths.get(config.installationDirectory()));
    }


}
