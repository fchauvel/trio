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

import java.io.IOException;
import org.cloudml.core.Deployment;

/**
 * Entry point of the robustness calculator
 */
public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("CloudML Robustness Calculator v0.1");
        System.out.println("Copyright (c) 2014 - SINTEF ICT");
        System.out.println("");
        
        try {
            Arguments.parse(args).execute();

        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
            Arguments.showUsage();

        }
    }

}
