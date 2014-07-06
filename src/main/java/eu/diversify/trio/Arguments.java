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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.cloudml.codecs.library.CodecsLibrary;  
import org.cloudml.core.Deployment;

/**
 * Parse the arguments provided on the command line
 */
public class Arguments {

    public static void showUsage() {
        final String EOL = System.lineSeparator();
        System.out.println("Usage: java -jar robustness-final.jar <level> <deployment> <run count>" + EOL
                + "where: " + EOL
                + " + <level>      level at which the robustness should be estimated. Can be either 'type' or 'instance'" + EOL
                + " + <deployment> the path of a the deployment model to analyse" + EOL
                + " + <run count>  the number of run to execute (for statistical evidence)");
    }

    public static Arguments parse(String[] arguments) {
        return new Arguments(arguments);
    }

    private final String level;
    private final String pathToDeployment;
    private final int runCount;

    public Arguments(String[] arguments) {
        if (arguments.length != 3) {
            final String error = String.format("Expected 3 parameters (found %d)", arguments.length);
            throw new IllegalArgumentException(error);
        }

        level = extractLevel(arguments[0]);
        pathToDeployment = extractDeployment(arguments[1]);
        runCount = extractRunCount(arguments[2]);
    }

    private String extractLevel(String text) {
        final String escaped = text.toLowerCase().trim();
        if (escaped.matches("type")) {
            return "type";
        }
        if (escaped.matches("instance")) {
            return "level";
        }
        final String error = String.format("Expected analysis level as argument no. 1 (either 'type' or 'instance', but found '%s'", text);
        throw new IllegalArgumentException(error);
    }

    private String extractDeployment(String text) {
        return text.trim();
    }

    private int extractRunCount(String text) {
        try {
            final int count = Integer.parseInt(text.trim());
            if (count <= 1) {
                final String error = String.format("Run count should be a positive value (found %d)", count);
                throw new IllegalArgumentException(error);
            }
            return count;

        } catch (NumberFormatException ex) {
            final String error = String.format("Illegal run count (incorrect number format)");
            throw new IllegalArgumentException(error, ex);
        }
    }

    public void execute() throws IOException {
        SequenceGroup sequences = new Simulator(level()).randomExtinctions(runCount);
       
        System.out.println(sequences.summary());
        try {
            sequences.toCsvFile("extinction_sequence.csv");
            System.out.printf("Sequence(s) stored in '%s'\n", new File("extinction_sequence.csv").getAbsolutePath());
            
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

    }

    private AbstractPopulation level() {
        try {
            final Deployment deployment = new CodecsLibrary().load(pathToDeployment);
            if (level.equals("type")) {
                return new TypeLevel(deployment);
            }
            if (level.equals("instance")) {
                throw new UnsupportedOperationException("Instance level is not yet supported");
            }

        } catch (FileNotFoundException ex) {
            final String error = String.format("Unable to fetch deployment at '%s'", pathToDeployment);
            throw new IllegalArgumentException(error);

        }

        throw new IllegalArgumentException("Unknown analysis level '" + level + "' (should be either 'type' or 'instance'");
    }

}
