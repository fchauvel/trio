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

import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.core.Component;

import static eu.diversify.trio.core.requirements.Factory.*;

/**
 * Some typical system
 */
public class Samples {

    public static Assembly A_and_B_independent() {
        return new Assembly(
                new Component("A"),
                new Component("B"));
    }

    public static Assembly A_require_B() {
        return new Assembly(
                new Component("A", require("B")),
                new Component("B"));
    }

    public static Assembly A_require_B_or_C() {
        return new Assembly(
                new Component("A", require("B").or(require("C"))),
                new Component("B"),
                new Component("C"));
    }

    public static Assembly A_require_B_and_C() {
        return new Assembly(
                new Component("A", require("B").and(require("C"))),
                new Component("B"),
                new Component("C"));
    }

    public static Assembly ABC_with_circular_dependencies() {
        return new Assembly(
                new Component("A", require("B")),
                new Component("B", require("C")),
                new Component("C", require("A")));
    }

    public static Assembly ABC_with_linear_dependencies() {
        return new Assembly(
                new Component("A", require("B")),
                new Component("B", require("C")),
                new Component("C"));
    }

    public static Assembly ABC_with_meshed_AND() {
        return new Assembly(
                new Component("A", require("B").and(require("C"))),
                new Component("B", require("A").and(require("C"))),
                new Component("C", require("B").and(require("A"))));
    }

    public static Assembly sample1() {
        return new Assembly(
                new Component("A", require("B").and(require("C"))),
                new Component("B"),
                new Component("C", require("D").or(require("E"))),
                new Component("D"),
                new Component("E")
        );
    }

    public static String oneClientAndOneServer() {
        return "components: "
                + " - Client requires Server and VM "
                + " - Server requires VM"
                + " - VM "
                + "tags:"
                + " - 'internal' on Client, Server"
                + " - 'external' on VM"
                ;
    }
    
    public static String oneClientRequiresServerAndVM() {
        return "components: "
            + " - Client requires Server and VM_Client "
            + " - Server requires VM_Server "
            + " - VM_Server "
            + " - VM_Client "
            + "tags:"
            + " - 'internal' on Client, Server"
            + " - 'external' on VM_Client, VM_Server";
    }
    
    public static String oneClientRequiresClusteredServers() {
        return "components: "
            + " - Client requires Server and VM_Client "
            + " - Server requires VM_Server1 or VM_Server2"
            + " - VM_Server1"
            + " - VM_Server2 "
            + " - VM_Client "
            + "tags:"
            + " - 'internal' on Client, Server"
            + " - 'external' on VM_Client, VM_Server1, VM_Server2";
    }
    
    

}
