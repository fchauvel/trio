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

import eu.diversify.trio.testing.Run;
import java.io.*;
import junit.framework.TestCase;
import org.cloudml.codecs.library.CodecsLibrary;
import org.cloudml.core.Deployment;
import org.cloudml.core.samples.SensApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static eu.diversify.trio.testing.DidNotRaiseAnyException.didNotReportAnyError;
import static eu.diversify.trio.testing.DidShowCopyright.didShowCopyright;
import static eu.diversify.trio.testing.DidShowRobustness.didShowRobustness;
import static eu.diversify.trio.testing.DidShowUsage.didShowUsage;
import static org.cloudml.core.builders.Commons.*;

import static org.hamcrest.MatcherAssert.*;

/**
 * Specification of user acceptance tests
 */
@RunWith(JUnit4.class)
public class AcceptanceIT extends TestCase {

    private static final String EXTINCTION_SEQUENCE_CSV = "extinction_sequence.csv";

    @Test
    public void usageShouldBeDisplayedWhenArgumentsAreWrongs() throws IOException, InterruptedException {
        Run run = Run.withCommandLine("-foo bar");

        assertThat(run, didShowUsage()); 
    }

    @Test
    public void sensappShouldHaveARobustnessOf75() throws FileNotFoundException, IOException, InterruptedException {
        Deployment deployment = SensApp.completeSensApp().build();

        final String testFile = "sensapp.json";
        new CodecsLibrary().saveAs(deployment, testFile);

        Run run = Run.withArguments("type", testFile, 10); 

        assertThat(run, didShowCopyright(2014, "SINTEF ICT"));
        assertThat(run, didNotReportAnyError());
        assertThat(run, didShowRobustness(75, 10));

        deleteFiles(testFile, EXTINCTION_SEQUENCE_CSV);
    }

    @Test
    public void oneSingleVMShouldHaveARobustnessOf100() throws IOException, InterruptedException {
        final String testFile = "test.json";
        createModelWithASingleVM(testFile);

        Run run = Run.withArguments("type", testFile, 10);

        assertThat(run, didShowCopyright(2014, "SINTEF ICT"));
        assertThat(run, didNotReportAnyError());
        assertThat(run, didShowRobustness(100.0, 1e-3));

        deleteFiles(testFile, EXTINCTION_SEQUENCE_CSV);
    }

    private void createModelWithASingleVM(String location) throws FileNotFoundException {
        Deployment deployment = aDeployment()
                .with(aProvider().named("Ec2"))
                .with(aVM()
                        .named("My VM")
                        .providedBy("Ec2"))
                .build();

        new CodecsLibrary().saveAs(deployment, location);
    }

    @Test
    public void twoSeparateVMsShouldHaveARobustnessOf100() throws IOException, InterruptedException {
        final String testFile = "test.json";
        createModelWithTwoSeparateVMs(testFile);

        Run run = Run.withArguments("type", testFile, 10);

        assertThat(run, didShowCopyright(2014, "SINTEF ICT"));
        assertThat(run, didNotReportAnyError());
        assertThat(run, didShowRobustness(100.0, 1e-3));

        deleteFiles(testFile, EXTINCTION_SEQUENCE_CSV);
    }

    private void createModelWithTwoSeparateVMs(String location) throws FileNotFoundException {
        Deployment deployment = aDeployment()
                .with(aProvider().named("Ec2"))
                .with(aVM()
                        .named("VM #1")
                        .providedBy("Ec2"))
                .with(aVM()
                        .named("VM #2")
                        .providedBy("Ec2"))
                .build();

        new CodecsLibrary().saveAs(deployment, location);
    }

    public void deleteFiles(String... fileNames) {
        for (String eachFile: fileNames) {
            final File file = new File(eachFile);
            file.delete();
        }
    }

}
