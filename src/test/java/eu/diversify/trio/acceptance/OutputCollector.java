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

package eu.diversify.trio.acceptance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Collect all the data available in the given input stream
 */
class OutputCollector extends Thread {
    private final BufferedReader reader;
    private final StringBuilder buffer;

    public OutputCollector(InputStream inputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.buffer = new StringBuilder();
     }

    public void run() {
        String line = "";
        try {
            while (null != (line = this.reader.readLine())) {
                buffer.append(line);
                buffer.append(System.lineSeparator());
            }
            this.reader.close();
        } catch (IOException ex) {
            throw new RuntimeException("Error while reading", ex);
        }
    }

    public String getOutput() {
        return this.buffer.toString();
    }

}
