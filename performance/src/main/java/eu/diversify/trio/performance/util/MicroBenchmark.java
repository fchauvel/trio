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
package eu.diversify.trio.performance.util;

import java.util.Collection;
import java.util.Properties;

/**
 * Run a sequence of tasks and measure their duration.
 */
public class MicroBenchmark {

    private static final int DEFAULT_ID = 0;

    public static final String TOTAL_TASK_COUNT_KEY = "total task count";
    public static final String DURATION_KEY = "duration";

    private final int id;
    private final TaskStore store;
    private final Collection<Integer> tasks;
    private final EventBroker events;

    public MicroBenchmark(TaskStore store, Collection<Integer> tasks) {
        this(DEFAULT_ID, store, tasks);
    }

    public MicroBenchmark(int id, TaskStore store, Collection<Integer> tasks) {
        this(id, store, tasks, new EventBroker());
    }

    public MicroBenchmark(int id, TaskStore store, Collection<Integer> tasks, EventBroker events) {
        this.id = id;
        this.tasks = tasks;
        this.store = store;
        this.events = events;
    }

    /**
     * @return the unique identifier of this benchmark
     */
    public int id() {
        return this.id;
    }

    public void run() {
        for (int eachId : tasks) {
            final Task task = store.fetch(eachId);
            final Properties performance = monitor(task);
            events.taskCompleted(id, aggregateProperties(task, performance));
        }
    }

    private Properties aggregateProperties(Task task, final Properties performance) {
        final Properties properties = new Properties();
        properties.put(TOTAL_TASK_COUNT_KEY, tasks.size());
        properties.putAll(task.properties());
        properties.putAll(performance);
        return properties;
    }

    private Properties monitor(Task task) {
        final Properties performances = new Properties();
        long start = System.nanoTime();
        task.execute();
        long end = System.nanoTime();
        performances.put(DURATION_KEY, end - start);

        return performances;
    }

}
