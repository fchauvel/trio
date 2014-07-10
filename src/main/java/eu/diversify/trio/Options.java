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

import java.lang.System;


/**
 * The options which can be passed to trio
 */
public class Options {
    
    
    public static Options from(String[] commandLine) {
        return new Options(commandLine);
    }
    
    private Options(String[] commandLine) {
        
    }

    public void invoke() {
        throw new UnsupportedOperationException("Not yet implemented!");
    }
    
    
    private String usage() {
        final String EOL = System.lineSeparator();
        return  "Usage: trio [options] input.trio" + EOL +
                "where 'options' are:" + EOL +
                "  -o, --out=FILE     the file where the generated data shall be stored" + EOL +
                "  -r, --run=INTEGER  the number of sample for statistical evidence";
    }
    
}
