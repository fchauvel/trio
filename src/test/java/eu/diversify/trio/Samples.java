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

package eu.diversify.trio;

import static eu.diversify.trio.requirements.Require.require;

/**
 *
 */
public class Samples {

    public static System A_require_B() {
        return new System(
            new Component("A", require("B")),
            new Component("B"));
    }
    
     public static System sample1() {
        return new System(
                new Component("A", require("B").and(require("C"))),
                new Component("B"),
                new Component("C", require("D").or(require("E"))),
                new Component("D"),
                new Component("E")
        );
    }
    
}
