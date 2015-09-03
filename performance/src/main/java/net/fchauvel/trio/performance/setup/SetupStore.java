/**
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
/**
 * This file is part of TRIO :: Performance.
 *
 * TRIO :: Performance is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Performance is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Performance.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
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
/**
 * This file is part of TRIO :: Performance.
 *
 * TRIO :: Performance is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Performance is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Performance.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
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
/**
 * This file is part of TRIO :: Performance.
 *
 * TRIO :: Performance is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Performance is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Performance.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
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
package net.fchauvel.trio.performance.setup;

import net.fchauvel.trio.performance.GraphStore;
import java.util.Properties;

/**
 * Load and Store setup object from various format
 */
public class SetupStore {

    /**
     * Load a setup serialized in the given properties table
     *
     * @param properties the properties table that contain the data
     * @return the extracted setup object
     */
    public Setup loadFromProperties(Properties properties) {
        return new Setup(
                extractInt(properties, FIRST_GRAPH_ID_KEY, Setup.DEFAULT_FIRST_GRAPH_ID),
                extractInt(properties, LAST_GRAPH_ID_KEY, Setup.DEFAULT_LAST_GRAPH_ID),
                extractDouble(properties, WARMUP_RATIO_KEY, Setup.DEFAULT_WARMUP_RATIO),
                extractString(properties, GRAPH_DIRECTORY_KEY, GraphStore.DEFAULT_GRAPH_PATH),
                extractString(properties, GRAPH_FILE_PATTERN_KEY, GraphStore.DEFAULT_GRAPH_FILE_PATTERN)
        );
    }

    private String extractString(Properties properties, String key, String defaultValue) throws IllegalArgumentException {
        String value = properties.getProperty(key);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }

    private int extractInt(Properties properties, String key, int defaultValue) throws IllegalArgumentException {
        int sampleCount = defaultValue;
        final String valueText = properties.getProperty(key);
        if (valueText != null) {
            try {
                sampleCount = Integer.parseInt(valueText);
            } catch (NumberFormatException nfe) {
                final String description = String.format("Key '%s' must hold an integer value (found '%s')", key, valueText);
                throw new IllegalArgumentException(description);
            }
        }
        return sampleCount;
    }

    private double extractDouble(Properties properties, String key, double defaultValue) throws IllegalArgumentException {
        double sampleCount = defaultValue;
        final String valueText = properties.getProperty(key);
        if (valueText != null) {
            try {
                sampleCount = Double.parseDouble(valueText);
            } catch (NumberFormatException nfe) {
                final String description = String.format("Key '%s' must hold an double value (found '%s')", key, valueText);
                throw new IllegalArgumentException(description);
            }
        }
        return sampleCount;
    }

    private static final String WARMUP_RATIO_KEY = "warmup.ratio";
    private static final String GRAPH_FILE_PATTERN_KEY = "graph.file.pattern";
    private static final String GRAPH_DIRECTORY_KEY = "graph.directory";
    private static final String LAST_GRAPH_ID_KEY = "last.graph.id";
    private static final String FIRST_GRAPH_ID_KEY = "first.graph.id";
}
