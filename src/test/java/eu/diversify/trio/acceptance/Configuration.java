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

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Singleton instance which load the configuration of the acceptance test from
 * 'src/test/resources/acceptance.properties'
 */
public class Configuration {

    private static final String ACCEPTANCE = "src/test/resources/acceptance.properties";
    
    private static Configuration instance;
    
    
    public static Configuration forTest() {
        if (instance == null) {
            instance = new Configuration(ACCEPTANCE);
        }
        return instance;
    }
    
    private final Properties properties;
    
    private Configuration(String source) {
        properties = new Properties();
        try { 
            properties.load(new FileReader(source));
      
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public String archive() {
        return properties.getProperty("dist.file");
    }
    
    public String pathToArchive() {
        return workingDirectory() + "/" + archive();
    }
    
    public String workingDirectory() {
        return properties.getProperty("test.working.directory");
    }
    
    public String jarFile() {
        return properties.getProperty("dist.jar.name");
    }
    
    public String distributionHome() {
        return properties.getProperty("dist.home");
    }
    
    public String installationDirectory() {
        return workingDirectory() + "/" + distributionHome();
    }
    
}
