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

package eu.diversify.trio;

import java.io.FileInputStream;
import java.io.IOException;

import static eu.diversify.trio.builder.Builder.build;

/**
 * The Trio application
 */
public class Trio {

    void analyse(String inputFile, String outputFile, int runCount) throws IOException {
        final System system = build().systemFrom(new FileInputStream(inputFile));
        final DataSet data = new DataSet();
        final Simulation simulation = new Simulation(system, data);
        simulation.randomExtinctionSequence(runCount);
        final Summary summary = new Summary(data, new Robustness(), new Length());        
        summary.showOn(java.lang.System.out);        
        data.saveAs(new CSV(outputFile));  
    }

}
