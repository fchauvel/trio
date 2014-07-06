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

package eu.diversify.trio.testing;

import java.io.IOException;

/**
 * The interface of a run
 */
public abstract class Run {


    public static RunInThread withCommandLine(String... extraArguments) throws IOException, InterruptedException {
        final String[] all = new String[3 + extraArguments.length];
        all[0] = "java";
        all[1] = "-jar";
        all[2] = "robustness-final.jar";
        System.arraycopy(extraArguments, 0, all, 3, extraArguments.length);
        return new RunInThread(".", all);
    }

    public static Run withArguments(String type, String testFile, int i) throws IOException, InterruptedException {
        return new RunInThread(".",
                               new String[]{"java", "-jar",
                                            "robustness-final.jar",
                                            type, 
                                            testFile,
                                            String.format("%d", i)});
    }

    public abstract String getStandardError();

    public abstract String getStandardOutput();

}
