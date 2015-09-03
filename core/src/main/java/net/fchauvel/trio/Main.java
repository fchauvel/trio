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

package net.fchauvel.trio;

import net.fchauvel.trio.core.storage.StorageError;
import net.fchauvel.trio.core.storage.FileStorage;
import net.fchauvel.trio.simulation.Simulation;
import net.fchauvel.trio.ui.Command;
import net.fchauvel.trio.ui.UI;

/**
 * Entry point of the application
 */
public class Main {

    public static void main(String[] arguments) {
        
        final UI ui = new UI(System.out);
        final Configuration config = Configuration.forProduction();
        
        try {
            final Command command = Command.from(arguments);
            final Trio trio = new Trio(new FileStorage(command.getInput()));

            ui.showOpening(config);
            try {
                final Simulation simulation = command.getSimulation();
                trio.run(simulation, command.getRunCount(), ui.getListener());

            } catch (StorageError ex) {
                ui.showError(ex);

            }
            ui.showClosing();

        } catch (IllegalArgumentException ex) {
            ui.showError(ex);
            ui.showUsage();

        }
    }

}
