/**
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
 * This file is part of TRIO :: Core.
 *
 * TRIO :: Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Core.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
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
 * ====
 *     This file is part of TRIO :: Core.
 *
 *     TRIO :: Core is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     TRIO :: Core is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with TRIO :: Core.  If not, see <http://www.gnu.org/licenses/>.
 * ====
 *
 * ====
 *     This file is part of TRIO.
 *
 *     TRIO is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     TRIO is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with TRIO.  If not, see <http://www.gnu.org/licenses/>.
 * ====
 *
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
 * This file is part of TRIO :: Core.
 *
 * TRIO :: Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Core.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
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

import net.fchauvel.trio.core.Assembly;
import net.fchauvel.trio.core.Component;
import static net.fchauvel.trio.core.requirements.Factory.*;
import net.fchauvel.trio.core.storage.InMemoryStorage;
import net.fchauvel.trio.core.storage.Storage;
import net.fchauvel.trio.core.storage.parsing.Builder;
import java.util.HashMap;
import java.util.Map;

/**
 * Some typical system
 */
public class Samples {

    private static Storage storageWith(Assembly assembly) {
        final Map<String, Assembly> assemblies = new HashMap<String, Assembly>();
        assemblies.put("foo", assembly);
        return new InMemoryStorage(assemblies);
    }

    private static Assembly fromText(String text) {
        return new Builder().systemFrom(text);
    }

    public static Storage A_and_B_independent() {
        final Assembly assembly = new Assembly(
                new Component("A"),
                new Component("B"));
        return storageWith(assembly);
    }

    public static Storage A_require_B() {
        final Assembly assembly = new Assembly(
                new Component("A", require("B")),
                new Component("B"));
        return storageWith(assembly);
    }

    public static Storage A_require_B_or_C() {
        final Assembly assembly = new Assembly(
                new Component("A", require("B").or(require("C"))),
                new Component("B"),
                new Component("C"));
        return storageWith(assembly);
    }

    public static Storage A_require_B_and_C() {
        final Assembly assembly = new Assembly(
                new Component("A", require("B").and(require("C"))),
                new Component("B"),
                new Component("C"));
        return storageWith(assembly);
    }

    public static Storage ABC_with_circular_dependencies() {
        final Assembly assembly = new Assembly(
                new Component("A", require("B")),
                new Component("B", require("C")),
                new Component("C", require("A")));
        return storageWith(assembly);
    }

    public static Storage ABC_with_linear_dependencies() {
        final Assembly assembly = new Assembly(
                new Component("A", require("B")),
                new Component("B", require("C")),
                new Component("C"));
        return storageWith(assembly);
    }

    public static Storage ABC_with_meshed_AND() {
        final Assembly assembly = new Assembly(
                new Component("A", require("B").and(require("C"))),
                new Component("B", require("A").and(require("C"))),
                new Component("C", require("B").and(require("A"))));
        return storageWith(assembly);
    }

    public static Storage sample1() {
        final Assembly assembly = new Assembly(
                new Component("A", require("B").and(require("C"))),
                new Component("B"),
                new Component("C", require("D").or(require("E"))),
                new Component("D"),
                new Component("E")
        );
        return storageWith(assembly);
    }

    public static Storage oneClientAndOneServer() {
        final String text = "components: "
                + " - Client requires Server and VM "
                + " - Server requires VM"
                + " - VM "
                + "tags:"
                + " - 'internal' on Client, Server"
                + " - 'external' on VM";
        return storageWith(fromText(text));
    }

    public static Storage oneClientRequiresServerAndVM() {
        final String text = "components: "
                + " - Client requires Server and VM_Client "
                + " - Server requires VM_Server "
                + " - VM_Server "
                + " - VM_Client "
                + "tags:"
                + " - 'internal' on Client, Server"
                + " - 'external' on VM_Client, VM_Server";
        return storageWith(fromText(text));
    }

    public static Storage oneClientRequiresClusteredServers() {
        final String text 
                = "components: "
                + " - Client requires Server and VM_Client "
                + " - Server requires VM_Server1 or VM_Server2"
                + " - VM_Server1"
                + " - VM_Server2 "
                + " - VM_Client "
                + "tags:"
                + " - 'internal' on Client, Server"
                + " - 'external' on VM_Client, VM_Server1, VM_Server2";
        return storageWith(fromText(text));
    }
    
    public static Storage A_requires_B_and_C_with_MTTF() {
        final String text 
                = "components: "
                + " - SA requires RA"
                + " - SB requires RB"
                + " - RA [2.]"
                + " - RB [3.]"
                + "tags:"
                + " - 'service' on SA, SB"
                + " - 'resource' on RA, RB";
        return storageWith(fromText(text));
                
    }

}
