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
package eu.diversify.trio;

import java.io.IOException;
import java.lang.System;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The options which can be passed to trio
 */
public class Command {

    public static final String DEFAULT_OUTPUT_FILE = "results.csv";
    public static final int DEFAULT_RUN_COUNT = 1000;

    public static Command from(String[] commandLine) {
        return new Command(commandLine);
    }

    public static Command from(String commandLine) {
        return from(commandLine.split("\\s+"));
    }

    private String inputFile;
    private String outputFile;
    private int runCount;

    private Command(String[] commandLine) {
        final List<String> parameters = Arrays.asList(commandLine);
        outputFile = DEFAULT_OUTPUT_FILE;
        runCount = DEFAULT_RUN_COUNT;
        extractOptions(parameters);
    }

    private void extractOptions(List<String> parameters) {
        final Iterator<String> command = parameters.iterator();
        while (command.hasNext()) {
            final String parameter = command.next();
            if (parameter.matches(".*\\.trio")) {
                inputFile = parameter;
                break;

            } else if (parameter.startsWith("--")) {
                parseLongOptions(parameter);

            } else if (parameter.startsWith("-")) {
                parseShortOptions(command, parameter);

            } else {
                final String error = String.format("Unknown argument '%s'. Expecting either a path to a TRIO file or an option.", parameter);
                throw new IllegalArgumentException(error);
            }
        }
        if (inputFile == null) {
            throw new IllegalArgumentException("Missing input TRIO file!");
        }
    }

    private void parseShortOptions(final Iterator<String> command, final String eachParameter) throws IllegalArgumentException {
        if (!command.hasNext()) {
            final String error = String.format("Missing value after '%s'", eachParameter);
            throw new IllegalArgumentException(error);
        }
        if (eachParameter.equals("-o")) {
            outputFile = command.next();

        } else if (eachParameter.equals("-r")) {
            extractRunCountFrom(command.next());

        } else {
            final String error = String.format("Unknown short option '%s'", eachParameter);
            throw new IllegalArgumentException(error);
        }
    }

    private void parseLongOptions(final String eachParameter) throws IllegalArgumentException {
        final String parts[] = eachParameter.split("=");
        if (parts[0].equals("--output")) {
            outputFile = parts[1];

        } else if (parts[0].equals("--runs")) {
            extractRunCountFrom(parts[1]);

        } else {
            final String error = String.format("Unknown long option '%s'! Expecting either '--runs' or '--output'", parts[0]);
            throw new IllegalArgumentException(error);
        }
    }

    private void extractRunCountFrom(String text) throws IllegalArgumentException {
        try {
            runCount = Integer.parseInt(text);
            if (runCount < 1) {
                final String error = String.format("Run count shall be at least 1! (found %d)", runCount);
                throw new IllegalArgumentException(error);
            }

        } catch (NumberFormatException ex) {
            final String error = String.format("Invalid number of runs '%s", text);
            throw new IllegalArgumentException(error);

        }
    }

    public void execute(Trio handler) throws IOException {
        handler.analyse(inputFile, outputFile, runCount);
    }

    public static String usage() {
        final String EOL = System.lineSeparator();
        return "Usage: trio [options] input.trio" + EOL
                + "where 'options' are:" + EOL
                + "  -o, --output=FILE  the file where the generated data shall be stored" + EOL
                + "  -r, --runs=INTEGER the number of sample for statistical evidence" + EOL
                + "Example: trio -o result.csv --run=10000 system.trio";
    }

}
