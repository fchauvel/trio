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
package eu.diversify.trio.performance.setup;

import eu.diversify.trio.performance.SimulationFactory;
import eu.diversify.trio.performance.GraphStore;
import eu.diversify.trio.performance.util.EventBroker;
import eu.diversify.trio.performance.util.MicroBenchmark;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the configuration of the performance test
 */
public class Setup {

    public static final double DEFAULT_WARMUP_RATIO = 0.05;
    public static final int DEFAULT_LAST_GRAPH_ID = 100;
    public static final int DEFAULT_FIRST_GRAPH_ID = 1;

    private final double warmupRatio;
    private final int firstGraphId;
    private final int lastGraphId;
    private final String graphDirectory;
    private final String graphFilePattern;

    public Setup() {
        this(DEFAULT_FIRST_GRAPH_ID, DEFAULT_LAST_GRAPH_ID, DEFAULT_WARMUP_RATIO, GraphStore.DEFAULT_GRAPH_PATH, GraphStore.DEFAULT_GRAPH_FILE_PATTERN);
    }

    public Setup(int firstGraphId, int lastGraphId, double warmupRatio, String graphDirectory, String graphFilePattern) {
        this.firstGraphId = firstGraphId;
        this.lastGraphId = lastGraphId;
        this.warmupRatio = warmupRatio;
        this.graphDirectory = graphDirectory;
        this.graphFilePattern = graphFilePattern;
    }

    public MicroBenchmark benchmark(EventBroker events) {
        return new MicroBenchmark(BENCHMARK_ID, taskStore(), tasks(), events);
    }

    public MicroBenchmark warmup(EventBroker events) {
        return new MicroBenchmark(WARMUP_ID, taskStore(), warmupTasks(), events);
    }

    private static final int BENCHMARK_ID = 1;
    private static final int WARMUP_ID = 2;

    
    private SimulationFactory taskStore() {
        return new SimulationFactory(graphStore());
    }

    private GraphStore graphStore() {
        GraphStore graphs = new GraphStore(graphDirectory, graphFilePattern);
        return graphs;
    }

    private List<Integer> warmupTasks() {
        final int length =  (int) Math.floor(warmupRatio * taskCount());
        final ArrayList<Integer> tasks = new ArrayList<>(length);
        for (int i = 0 ; i < length ; i++) {
            tasks.add(firstGraphId + i);
        }
        return tasks;
    }

    private ArrayList<Integer> tasks() {
        final ArrayList<Integer> tasks = new ArrayList<>(taskCount());
        for (int eachId = firstGraphId; eachId <= lastGraphId; eachId++) {
            tasks.add(eachId);
        }
        return tasks;
    }

    private int taskCount() {
        return lastGraphId - firstGraphId + 1;
    }

    public String summary() {
        final StringBuilder buffer = new StringBuilder();
        buffer
                .append(String.format(" - %d topologies(s) ;%n", taskCount()))
                .append(String.format(" - from files '%s' in '%s' ;%n", graphFilePattern, graphDirectory))
                .append(String.format(" - %d %% as warmup.%n", (int) (warmupRatio * 100)));
       
        return buffer.toString();
    }

}
