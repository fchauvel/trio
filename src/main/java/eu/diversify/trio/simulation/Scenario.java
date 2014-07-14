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
/*
 */
package eu.diversify.trio.simulation;

import eu.diversify.trio.core.System;
import eu.diversify.trio.data.DataSet;
import eu.diversify.trio.data.Trace;
import eu.diversify.trio.filter.All;
import eu.diversify.trio.filter.Filter;

/**
 * The scenario taken as input by Trio
 */
public abstract class Scenario {

    private final System system;
    private final Filter observation;
    private final Filter control;

    public Scenario(System system) {
        this(system, All.getInstance(), All.getInstance());
    }

    public Filter getObservation() {
        return observation;
    }

    public Filter getControl() {
        return control;
    }

    public Scenario(System system, Filter observation, Filter control) {
        this.system = system;
        this.observation = observation;
        this.control = control;
    }
    

    public final Topology instantiate(DataSet report) {
        final Trace trace = new Trace(system.getComponentNames().size());
        report.include(trace);
        return new Topology(system, observation, control, new Listener(trace));
    }

    public final Topology run() {
        return run(new DataSet());
    }
    
    /**
     * Run this scenario and perform
     *
     * @param collector the dataset where the data must be collected
     * @return the resulting topology
     */
    public abstract Topology run(DataSet collector);

}
