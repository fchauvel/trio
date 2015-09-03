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
package net.fchauvel.trio.performance.ui;

import net.fchauvel.trio.performance.Arguments;
import net.fchauvel.trio.performance.setup.Setup;
import net.fchauvel.trio.performance.setup.SetupStore;
import net.fchauvel.trio.performance.storage.CsvRecorder;
import net.fchauvel.trio.performance.util.EventBroker;
import net.fchauvel.trio.performance.util.MicroBenchmark;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Entry point of the performance test
 */
public class Controller {

    public static void main(String[] args) {
        try {
            Controller runner = new Controller(Arguments.readFrom(args));
            runner.run();

        } catch (IllegalArgumentException error) {
            System.out.println("ERROR: " + error.getMessage());
        }
    }

    private final Arguments arguments;
    private final UI ui;
    private final EventBroker events;

    public Controller(Arguments arguments) {
        this(arguments, new UI());
    }

    public Controller(Arguments arguments, UI ui) {
        this.arguments = arguments;
        this.ui = ui;
        this.events = new EventBroker();
    }

    public void run() {

        ui.showOpening();

        final Setup setup = loadSetupFile();

        warmup(setup);

        final OutputStream outputFile = arguments.openOutputFile();

        benchmark(outputFile, setup);

        arguments.closeOutputFile(outputFile);

        ui.showClosing();
    }

    private void benchmark(final OutputStream outputFile, final Setup setup) {
        final MicroBenchmark benchmark = setup.benchmark(events);
        events.subscribe(benchmark.id(), ui.benchmarkView());

        final CsvRecorder recorder = new CsvRecorder(outputFile);
        events.subscribe(benchmark.id(), recorder);

        benchmark.run();
    }

    private void warmup(final Setup setup) {
        final MicroBenchmark warmup = setup.warmup(events);
        events.subscribe(warmup.id(), ui.warmupView());
        warmup.run();
    }

    private Setup loadSetupFile() {
        final SetupStore store = new SetupStore();
        final Properties properties = readProperties();
        final Setup setup = store.loadFromProperties(properties);

        System.out.println(setup.summary());

        return setup;
    }

    private Properties readProperties() {
        ui.info("Reading configuration in '" + arguments.getSetupFile() + "'");
        final Properties properties = new Properties();
        try {
            try (FileInputStream input = new FileInputStream(arguments.getSetupFile())) {
                properties.load(input);
            }

        } catch (IOException ex) {
            final String description
                    = String.format("Unable to open the setup file '%s' (%s)",
                            arguments.getSetupFile(),
                            ex.getMessage());

            ui.error(description);
            ui.info("Switching to the default setup");
        }
        return properties;
    }

}
