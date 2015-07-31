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

import eu.diversify.trio.analytics.robustness.FailureSequenceAggregator;
import eu.diversify.trio.analytics.robustness.RobustnessAggregator;
import eu.diversify.trio.analytics.sensitivity.SensitivityRanking;
import eu.diversify.trio.analytics.threats.ThreatRanking;
import eu.diversify.trio.simulation.Scenario;

import eu.diversify.trio.core.storage.SyntaxError;
import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.core.validation.InvalidSystemException;
import eu.diversify.trio.core.validation.Validator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static eu.diversify.trio.core.storage.Builder.build;
import static eu.diversify.trio.core.Evaluation.evaluate;
import eu.diversify.trio.simulation.events.Channel;

/**
 * The Trio application
 */
public class Trio {

    private final Channel simulation;
    private final eu.diversify.trio.analytics.events.Channel analytics;
    private ThreatRanking threatRanking;
    private SensitivityRanking sensitivityRanking;
    private FailureSequenceAggregator failureSequences;
    private RobustnessAggregator robustness;

public Trio() {
        this(new Channel(), new eu.diversify.trio.analytics.events.Channel());
    }

    public Trio(Channel simulation, eu.diversify.trio.analytics.events.Channel analytics) {
        this.simulation = simulation;
        this.analytics = analytics;
        this.failureSequences = new FailureSequenceAggregator(simulation, analytics);
        this.robustness = new RobustnessAggregator(simulation, analytics, analytics);
        this.sensitivityRanking = new SensitivityRanking(simulation, analytics);
        this.threatRanking = new ThreatRanking(simulation, analytics, analytics);
    }
    
    
    public eu.diversify.trio.analytics.events.Channel analyses() {
        return analytics;
    }

    public Assembly loadSystemFrom(String path) throws FileNotFoundException, IOException, SyntaxError {

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

    /**
     * Validate the consistency of the given model.
     *
     * @param system the system whose validity is unsure
     * @throws InvalidSystemException if there are some inconsistencies in the
     * model
     */
    public void validate(Assembly system) throws InvalidSystemException {
        Validator validity = new Validator();
        evaluate(validity).on(system);
        validity.check();
    }

    public void run(Scenario scenario, int runCount) {
        simulation.simulationInitiated(scenario.id());
        for (int i = 0; i < runCount; i++) {
            scenario.run(simulation);
        }
        simulation.simulationComplete(scenario.id());
    }

    public void run(Scenario scenario) {
        run(scenario, 1);
    }

    public void setTraceFile(String outputFile) {
        // TODO: useless method, to be remove (but break a lot of test)
    }

}
