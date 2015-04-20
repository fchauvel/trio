package eu.diversify.trio.performance;

import eu.diversify.trio.Trio;
import eu.diversify.trio.analysis.Analysis;
import eu.diversify.trio.analysis.RelativeRobustness;
import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.data.DataSet;
import eu.diversify.trio.generator.Generator2;
import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.graph.storage.csv.CsvReader;
import eu.diversify.trio.performance.util.Task;
import eu.diversify.trio.performance.util.TaskFactory;
import eu.diversify.trio.simulation.RandomFailureSequence;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Build task that simulate assembly build after graphs from CSV file on disks.
 *
 * The graph files are expected to be found in the directory "./graphs", and
 * their name must follow the pattern 'graph_25.csv' where 25 is the ID of the
 * graph.
 */
public class FileSimulationFactory implements TaskFactory {

    private final static Logger logger
            = Logger.getLogger(FileSimulationFactory.class.getName());

    private static final String GRAPH_DIRECTORY = "./graphs";
    private static final String GRAPH_FILE_PATTERN = "graph_(\\d+).csv";

    private final Trio trio;
    private final Generator2 generator;
    private Iterator<File> graphFiles;
    private final CsvReader reader;

    public FileSimulationFactory() {
        this.reader = new CsvReader();
        this.trio = new Trio();
        this.generator = new Generator2();
        this.graphFiles = allGraphFiles().iterator();
    }

    private List<File> allGraphFiles() throws IllegalStateException {
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
        final File graphDirectory = new File(GRAPH_DIRECTORY);
        if (!graphDirectory.exists()) {
            throw new IllegalStateException("Missing graph directory (expecting directory '" + GRAPH_DIRECTORY + "')");
        }
        if (!graphDirectory.isDirectory()) {
            throw new IllegalStateException("'" + GRAPH_DIRECTORY + "' must be a directory (file found)");
        }
        return graphDirectory;
    }

    private boolean isGraphFile(File anyFile) {
        return anyFile.isFile() && anyFile.getName().matches(GRAPH_FILE_PATTERN);
    }

    @Override
    public Task prepareNewTask() {
        requireMoreGraphs();

        final File graphFile = graphFiles.next();
        final FileInputStream graphInput = openGraphFile(graphFile);
        try {
            Graph graph = reader.read(graphInput);
            Assembly assembly = generator.nextAssembly(graph);
            return new SimulationTask(graphId(graphFile), trio, assembly);

        } catch (IOException ex) {
            logger.log(Level.INFO, "I/O error while reading graph file '" + graphFile.getName() + "'", ex);
            return prepareNewTask();

        } finally {
            closeGraphFile(graphInput, graphFile);

        }
    }

    private void requireMoreGraphs() throws IllegalStateException {
        if (!graphFiles.hasNext()) {
            throw new IllegalStateException("No more graph files to process");
        }
    }

    private int graphId(final File graphFile) throws NumberFormatException {
        final Pattern pattern = Pattern.compile(GRAPH_FILE_PATTERN);
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

    @Override
    public void reset() {
        graphFiles = allGraphFiles().iterator();
    }
    
    

    private static class SimulationTask implements Task {

        private static final String ROBUSTNESS = "robustness";

        private final int index;
        private final Trio trio;
        private final Assembly assembly;
        private final Map<String, Object> properties;
        private DataSet result;

        public SimulationTask(int index, Trio trio, Assembly assembly) {
            this.index = index;
            this.trio = trio;
            this.assembly = assembly;
            this.properties = new HashMap<>();
        }

        @Override
        public Map<String, Object> getProperties() {
            properties.put("id", index);

            Analysis analysis = trio.analyse(result);
            double robustness = analysis.metric(RelativeRobustness.NAME).distribution().mean();
            this.properties.put(ROBUSTNESS, robustness);

            return properties;
        }

        @Override
        public void execute() {
            result = trio.run(new RandomFailureSequence(assembly));
        }

    }

}
