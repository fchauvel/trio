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
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Singleton instance which load the configuration trio
 */
public class Configuration {

    private static final String TEST = "/test.properties";
    private static final String MAIN = "/main.properties";

    private static final Map<String, Configuration> instances = new HashMap<String, Configuration>();

    public static Configuration forTest() {
        return getInstance(TEST);
    }

    public static Configuration forProduction() {
        return getInstance(MAIN);
    }

    private static Configuration getInstance(String key) {
        Configuration result = instances.get(key);
        if (result == null) {
            result = new Configuration(key);
            instances.put(key, result);
        }
        return result;
    }

    private final Properties properties;

    private Configuration(String source) {
        properties = new Properties();
        try {
            InputStream input = Configuration.class.getResourceAsStream(source);
            properties.load(input);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String get(String key) {
        return properties.getProperty(key);
    }

    public String trioName() {
        return get("trio.name");
    }

    public String versionNumber() {
        return get("trio.version");
    }

    public String version() {
        return String.format("%s v%s", trioName(), versionNumber());
    }
    
    public String description() {
        return get("trio.description");
    }

    public String copyrightOwner() {
        return get("trio.copyright.owner");
    }

    public String copyrightYears() {
        return get("trio.copyright.years");
    }
    
    public String copyright() {
        return String.format("Copyright (C) %s - %s", copyrightYears(), copyrightOwner());
    }
    
    public String license() {
        return get("trio.license");
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
