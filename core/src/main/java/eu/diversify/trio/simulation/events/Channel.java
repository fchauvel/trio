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
package eu.diversify.trio.simulation.events;

import java.util.LinkedList;
import java.util.List;

/**
 * Dispatch event to all the registered listeners
 */
public class Channel implements Listener {

    private final List<Listener> listeners;

    public Channel() {
        this.listeners = new LinkedList<Listener>();
    }

    /**
     * Register the given listener so that it will be notified of any simulation
     * events that occurs.
     *
     * @param listener the listener object to be registered
     */
    public void subscribe(Listener listener) {
        checkListener(listener);
        listeners.add(listener);
    }

    private void checkListener(Listener listener) throws NullPointerException {
        if (listener == null) {
            throw new NullPointerException("Invalid simulation listener ('null' found)");
        }
    }

    public void simulationInitiated(int simulationId) {
        for (Listener eachListener: listeners) {
            eachListener.simulationInitiated(simulationId);
        }
    }

    public void sequenceInitiated(int simulationId, int sequenceId, List<String> observed, List<String> controlled) {
        for(Listener eachListener: listeners) {
            eachListener.sequenceInitiated(simulationId, sequenceId, observed, controlled);
        }
    }

    public void failure(int simulationId, int sequenceId, String failedComponent, List<String> impactedComponents) {
        for(Listener eachListener: listeners) {
            eachListener.failure(simulationId, sequenceId, failedComponent, impactedComponents);
        }
    }

    public void sequenceComplete(int simulationId, int sequenceId) {
        for(Listener eachListener: listeners) {
            eachListener.sequenceComplete(simulationId, sequenceId);
        }
    }

    public void simulationComplete(int simulationId) {
        for(Listener eachListener: listeners) {
            eachListener.simulationComplete(simulationId);
        }
    }

}
