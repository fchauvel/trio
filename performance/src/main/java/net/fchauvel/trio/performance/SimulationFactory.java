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

import net.fchauvel.trio.Trio;
import net.fchauvel.trio.analytics.events.IdleStatisticListener;
import net.fchauvel.trio.analytics.events.Statistic;
import net.fchauvel.trio.analytics.events.StatisticListener;
import net.fchauvel.trio.analytics.robustness.Robustness;
import net.fchauvel.trio.core.Assembly;
import net.fchauvel.trio.core.storage.InMemoryStorage;
import net.fchauvel.trio.core.storage.StorageError;
import net.fchauvel.trio.generator.Generator;
import net.fchauvel.trio.graph.model.Graph;
import net.fchauvel.trio.performance.util.Task;
import net.fchauvel.trio.performance.util.TaskStore;
import net.fchauvel.trio.simulation.RandomSimulation;
import net.fchauvel.trio.simulation.filter.All;
import java.io.IOException;
import java.util.Properties;

/**
 * Build task that simulate assembly build after graphs from CSV file on disks.
 */
public class SimulationFactory implements TaskStore {

    private final Generator generator;
    private final GraphStore graphs;

    public SimulationFactory(GraphStore graphs) {
        this.generator = new Generator();
        this.graphs = graphs;
    }

    @Override
    public Task fetch(int id) {
        try {
            final Graph data = graphs.fetch(id);
            final Assembly assembly = generator.nextAssembly(data);
            final Trio trio = new Trio(new InMemoryStorage(assembly));
            return new SimulationTask(id, trio);

        } catch (IOException ex) {
            throw new IllegalArgumentException("Unknown task ID " + id, ex);

        }
    }

    private static class SimulationTask implements Task {

        private static final String ROBUSTNESS_KEY = "robustness";

        private final int id;
        private final Trio trio;
        private final Properties properties;
 
        public SimulationTask(int id, Trio trio) {
            this.id = id;
            this.trio = trio;
            this.properties = new Properties();
        }

        @Override
        public int id() {
            return id;
        }

        @Override
        public void execute() {
            final RandomSimulation simulation = new RandomSimulation(All.getInstance(), All.getInstance());
            try {
                trio.run(simulation, RUN_COUNT, listener());
            
            } catch (StorageError ex) {
                throw new RuntimeException("Unable to access storage", ex);
            
            }
        }

        public StatisticListener listener() {
            return new IdleStatisticListener() {
                
                @Override
                public void onRobustness(Statistic context, Robustness indicator) {
                    properties.put(ROBUSTNESS_KEY, indicator.average());
                }

            };
        }

        @Override
        public Properties properties() {
            properties.put("id", id);
            return properties;
        }

        private static final int RUN_COUNT = 500;

    }

}
