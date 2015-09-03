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
package eu.diversify.trio.performance.ui;

import eu.diversify.trio.performance.util.Listener;
import static eu.diversify.trio.performance.util.MicroBenchmark.*;
import java.io.OutputStream;
import java.util.Properties;

/**
 * The text-based user interface
 */
public class UI {

    private BenchmarkView warmup;
    private BenchmarkView benchmark;

    public BenchmarkView warmupView() {
        if (warmup == null) {
            System.out.println("Warming-up ... ");
            warmup = new BenchmarkView(System.out);
        }
        return warmup;
    }

    public BenchmarkView benchmarkView() {
        if (benchmark == null) {
            System.out.println();
            System.out.println();
            System.out.println("Evaluating performance ... ");
            benchmark = new BenchmarkView(System.out);
        }
        return benchmark;
    }

    public void showOpening() {
        System.out.println("TRIO - Topology Robustness Indicator");
        System.out.println("Copyright (C) 2015 - SINTEF ICT");
        System.out.println();
    }

    public void showClosing() {
        System.out.println();
        System.out.println();
        System.out.println("That's all folks!");
    }

    public void info(String info) {
        System.out.println(info);
    }

    public void error(String error) {
        System.err.println(error);
    }

    public static class BenchmarkView implements Listener {

        private final Summary bar;
        private int taskCount;
        private double totalDuration;

        public BenchmarkView(OutputStream out) {
            this.taskCount = 0;
            this.bar = new Summary(out);
        }

        @Override
        public void onTaskCompleted(Properties properties) {
            taskCount++;
            setProgress(properties);
            setAverageDuration(properties);
            bar.refresh();
        }

        private void setProgress(Properties properties) throws NumberFormatException {
            final int totalTaskCount = fetch(properties, TOTAL_TASK_COUNT_KEY, Integer.class);
            final double progress = (double) taskCount / totalTaskCount;
            bar.setProgress(progress);
        }

        private void setAverageDuration(Properties properties) {
            final int totalTaskCount = fetch(properties, TOTAL_TASK_COUNT_KEY, Integer.class);
            totalDuration += fetch(properties, DURATION_KEY, Long.class);
            bar.setAverageDuration(totalDuration / totalTaskCount);
        }

        private <T> T fetch(Properties properties, String key, Class<T> type) throws NumberFormatException {
            final Object value = properties.get(key);
            if (value == null) {
                throw new IllegalArgumentException("Missing property '" + key + "'");
            }

            try {
                return type.cast(value);

            } catch (ClassCastException ex) {
                final String description = String.format("Property '%s' must be of type '%s' (found type '%s')", key, type.getName(), value.getClass().getName());
                throw new IllegalArgumentException(description, ex);

            }
        }

    }

}
