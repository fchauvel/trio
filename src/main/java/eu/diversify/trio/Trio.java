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
/**
 *
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
package eu.diversify.trio;

import eu.diversify.trio.simulation.Scenario;
import eu.diversify.trio.analysis.Analysis;
import eu.diversify.trio.analysis.Threat;
import eu.diversify.trio.analysis.Length;
import eu.diversify.trio.analysis.Loss;
import eu.diversify.trio.analysis.Probability;
import eu.diversify.trio.analysis.RelativeRobustness;
import eu.diversify.trio.analysis.Robustness;
import eu.diversify.trio.core.System;
import eu.diversify.trio.data.CSVFormatter;
import eu.diversify.trio.data.DataSet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static eu.diversify.trio.codecs.Builder.build;

/**
 * The Trio application
 */
public class Trio {

    public System loadSystemFrom(String path) throws FileNotFoundException, IOException {

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            return build().systemFrom(fileInputStream);

        } catch (IOException ex) {
            throw new RuntimeException("Unable to open the stream from '" + path + "'", ex);

        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                
                } catch (IOException ex) {
                    throw new RuntimeException("Unable to close the stream from '" + path + "'", ex);
                }
            }
        }
    }

    public DataSet run(Scenario scenario, int runCount) {
        final DataSet dataCollector = new DataSet();
        for (int i = 0; i < runCount; i++) {
            scenario.run(dataCollector);
        }
        return dataCollector;
    }

    public DataSet run(Scenario scenario) {
        final DataSet dataCollector = new DataSet();
        scenario.run(dataCollector);
        return dataCollector;
    }

    public Analysis analyse(DataSet data) {
        final Analysis analysis = buildAnalysis();
        data.accept(analysis);
        return analysis;
    }

    public void saveDataAs(final DataSet data, String outputFile) {
        OutputStream output = null;
        try {
            output = new FileOutputStream(outputFile);
            CSVFormatter formatter = new CSVFormatter(output);
            data.accept(formatter);

        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);

        } finally {
            if (output != null) {
                try {
                    output.close();

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private Analysis buildAnalysis() {
        final Robustness robustness = new Robustness();
        final RelativeRobustness rRobustness = new RelativeRobustness(robustness);
        final Probability probability = new Probability();
        final Length length = new Length();
        final Loss loss = new Loss();
        final Threat fragility = new Threat(rRobustness, probability);
        return new Analysis(robustness, rRobustness, length, loss, probability, fragility);
    }

}
