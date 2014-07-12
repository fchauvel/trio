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

import eu.diversify.trio.simulation.Scenario;
import eu.diversify.trio.codecs.CSV;
import eu.diversify.trio.analysis.Analysis;
import eu.diversify.trio.analysis.Length;
import eu.diversify.trio.analysis.Loss;
import eu.diversify.trio.analysis.Probability;
import eu.diversify.trio.analysis.Robustness;
import eu.diversify.trio.core.System;
import eu.diversify.trio.data.DataSet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static eu.diversify.trio.codecs.Builder.build;

/**
 * The Trio application
 */
public class Trio {

    public System loadSystemForm(String path) throws FileNotFoundException, IOException {
        return build().systemFrom(new FileInputStream(path)); 
    }
    
    public DataSet run(Scenario scenario, int runCount) {
         final DataSet dataCollector = new DataSet();
         for(int i=0 ; i<runCount; i++) {
             scenario.run(dataCollector);
         }
         return dataCollector;
    }
    
    public Analysis analyse(DataSet data) {
         final Analysis analysis = buildAnalysis();
         data.accept(analysis);
         return analysis;
    }

    public void saveDataAs(final DataSet data, String outputFile) {
        data.saveAs(new CSV(), outputFile);
    }
    
    private Analysis buildAnalysis() {
        final Robustness robustness = new Robustness();
        final Probability probability = new Probability();
        final Length length = new Length();
        final Loss loss = new Loss();
        return new Analysis(robustness, length, loss, probability);
    }

}
