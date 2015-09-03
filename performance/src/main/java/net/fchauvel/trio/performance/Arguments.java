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
package net.fchauvel.trio.performance;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The options which can be given through the command line
 */
public class Arguments {

    public static final String DEFAULT_OUTPUT_FILE = "scalability.csv";
    public static final String DEFAULT_SETUP_FILE = "setup.properties";

    private String outputFileName;
    private String setupFile;

    public Arguments() {
        this(DEFAULT_SETUP_FILE, DEFAULT_OUTPUT_FILE);
    }

    public Arguments(String setupFile, String outputFile) {
        this.setupFile = setupFile;
        this.outputFileName = outputFile;
    }

    public OutputStream openOutputFile() throws IllegalArgumentException {
        OutputStream outputFile = null;
        try {
            outputFile = new FileOutputStream(outputFileName);

        } catch (FileNotFoundException error) {
            final String message
                    = String.format("Unable to open output file '%s' (%s)",
                            outputFileName,
                            error.getMessage());
            throw new IllegalArgumentException(message, error);
        }
        return outputFile;
    }

    public void closeOutputFile(final OutputStream outputFile) throws IllegalArgumentException {
        try {
            outputFile.close();

        } catch (IOException error) {
            String message
                    = String.format("Unable to close the selected output '%s' (%s)",
                            outputFileName,
                            error.getMessage());
            throw new IllegalArgumentException(message, error);
        }
    }

    public String getOutputFile() {
        return outputFileName;
    }

    public void setOutputFile(String outputFile) {
        this.outputFileName = outputFile;
    }

    public String getSetupFile() {
        return setupFile;
    }

    public void setSetupFile(String setupFile) {
        this.setupFile = setupFile;
    }

    private static List<String> SETUP_MARKERS = Arrays.asList(new String[]{"-setup", "-s"});
    private static List<String> OUTPUT_MARKERS = Arrays.asList(new String[]{"-output", "-o"});

    /**
     * Convert the raw command line arguments into an 'Option' object.
     *
     * @param args the raw command line argument
     * @return the corresponding Option object.
     */
    public static Arguments readFrom(String[] args) {
        final Arguments results = new Arguments();
        final Iterator<String> iterator = Arrays.asList(args).iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (SETUP_MARKERS.contains(next)) {
                extractSetupFile(iterator, results);
            } else if (OUTPUT_MARKERS.contains(next)) {
                extractOutputFile(iterator, results);
            } else {
                throw new IllegalArgumentException("Unknown option '" + next + "'");
            }
        }
        return results;
    }

    private static void extractOutputFile(final Iterator<String> iterator, final Arguments results) throws IllegalArgumentException {
        if (!iterator.hasNext()) {
            throw new IllegalArgumentException("Expecting output file");
        }
        results.setOutputFile(iterator.next());
    }

    private static void extractSetupFile(final Iterator<String> iterator, final Arguments results) throws IllegalArgumentException {
        if (!iterator.hasNext()) {
            throw new IllegalArgumentException("Expecting setup file");
        }
        results.setSetupFile(iterator.next());
    }

}
