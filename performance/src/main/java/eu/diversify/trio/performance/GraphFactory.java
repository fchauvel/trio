package eu.diversify.trio.performance;

import eu.diversify.trio.graph.storage.csv.CsvReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Serve all the graphs in a given folder, based on a name pattern.
 *
 * The graph files are expected to be found in the directory "./graphs", and
 * their name must follow the pattern 'graph_25.csv' where 25 is the ID of the
 * graph.
 */
public class GraphFactory implements Iterator<GraphData> {

    private final static Logger logger
            = Logger.getLogger(GraphFactory.class.getName());

    public static final String GRAPH_FILE_PATTERN = "graph_(\\d+).csv";
    public static final String DEFAULT_GRAPH_PATH = "./graphs";

    private final String graphsPath;
    private final String graphFilePattern;

    private final List<File> allGraphFiles;
    private final Iterator<File> graphIterator;

    public GraphFactory() {
        this(DEFAULT_GRAPH_PATH, GRAPH_FILE_PATTERN);
    }

    public GraphFactory(String directory, String graphFilePattern) {
        this.graphsPath = directory;
        this.graphFilePattern = graphFilePattern;
        this.allGraphFiles = fetchGraphFiles();
        this.graphIterator = allGraphFiles.iterator();
    }

    private List<File> fetchGraphFiles() throws IllegalStateException {
        final List<File> graphFiles = new ArrayList<>();
        final File graphDirectory = openGraphDirectory();
        for (File anyFile : graphDirectory.listFiles()) {
            if (isGraphFile(anyFile)) {
                graphFiles.add(anyFile);
            }
        }
        return graphFiles;
    }

    private File openGraphDirectory() throws IllegalStateException {
        final File graphDirectory = new File(graphsPath);
        if (!graphDirectory.exists()) {
            throw new IllegalStateException("Missing graph directory (expecting directory '" + graphDirectory + "')");
        }
        if (!graphDirectory.isDirectory()) {
            throw new IllegalStateException("'" + graphDirectory + "' must be a directory (file found)");
        }
        return graphDirectory;
    }

    private boolean isGraphFile(File anyFile) {
        return anyFile.isFile() && anyFile.getName().matches(graphFilePattern);
    }

    @Override
    public boolean hasNext() {
        return graphIterator.hasNext();
    }

    @Override
    public GraphData next() {
        final CsvReader reader = new CsvReader();
        final File graphFile = graphIterator.next();
        final FileInputStream graphInput = openGraphFile(graphFile);
        try {
            return new GraphData(graphId(graphFile), graphFile.getPath(), reader.read(graphInput));

        } catch (IOException ex) {
            logger.log(Level.INFO, "I/O error while reading graph file '" + graphFile.getName() + "'", ex);
            return next();

        } finally {
            closeGraphFile(graphInput, graphFile);

        }
    }

    private int graphId(final File graphFile) throws NumberFormatException {
        final Pattern pattern = Pattern.compile(graphFilePattern);
        final Matcher matcher = pattern.matcher(graphFile.getName());
        if (matcher.matches()) {
            return Integer.parseInt(matcher.group(1));
        }
        throw new IllegalArgumentException("Cannot extract ID from graph file '" + graphFile.getName() + "'");
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
