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

package eu.diversify.trio.performance;

import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.graph.storage.csv.CsvReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Serve all the graphs in a given folder, based on a name pattern.
 *
 * The graph files are expected to be found in the directory "./graphs", and
 * their name must follow the pattern 'graph_25.csv' where 25 is the ID of the
 * graph.
 */
public class GraphStore {

    private final static Logger logger
            = Logger.getLogger(GraphStore.class.getName());

    public static final String DEFAULT_GRAPH_FILE_PATTERN = "graph_%d.csv";
    public static final String DEFAULT_GRAPH_PATH = "./graphs";

    private final String graphsPath;
    private final String graphFilePattern;

    public GraphStore() {
        this(DEFAULT_GRAPH_PATH, DEFAULT_GRAPH_FILE_PATTERN);
    }

    public GraphStore(String directory, String graphFilePattern) {
        this.graphsPath = directory;
        this.graphFilePattern = graphFilePattern;
    }

    
    public Graph fetch(int id) throws IOException {
        final CsvReader reader = new CsvReader();
        final File graphFile = graphFileFromId(id);
        final FileInputStream graphInput = openGraphFile(graphFile);
        try {
            return reader.read(graphInput);
        } finally {
            closeGraphFile(graphInput, graphFile);
        }
    }

    private File graphFileFromId(int id) {
        final String fileName = String.format(graphFilePattern, id);
        final String path = String.format("%s/%s", graphsPath, fileName);
        final File graphFile = new File(path);
        return graphFile;
    }

    private FileInputStream openGraphFile(final File graphFile) {
        FileInputStream graphInput = null;
        try {
            graphInput = new FileInputStream(graphFile);

        } catch (FileNotFoundException ex) {
            logger.log(Level.WARNING, "Unable to find graph file '" + graphFile.getName() + "'", ex);

        }
        return graphInput;
    }

    private void closeGraphFile(FileInputStream graphInput, final File graphFile) {
        try {
            if (graphInput != null) {
                graphInput.close();
            }
        } catch (IOException ex) {
            logger.log(Level.INFO, "I/O error while closing graph file '" + graphFile.getName() + "'", ex);
        }
    }

}
