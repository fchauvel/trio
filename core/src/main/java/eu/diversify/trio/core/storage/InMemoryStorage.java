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
package eu.diversify.trio.core.storage;

import eu.diversify.trio.core.Assembly;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Hold a collection of assemblies in memory, indexed by name
 */
public class InMemoryStorage implements Storage {

    private Map<String, Assembly> assemblies;

    public InMemoryStorage() {
        this.assemblies = new HashMap<String, Assembly>();
    }
    
    public InMemoryStorage(Assembly single) {
        this.assemblies = new HashMap<String, Assembly>();
        this.assemblies.put("single", single);
    }

    public InMemoryStorage(Map<String, Assembly> assemblies) {
        this.assemblies = new HashMap<String, Assembly>(assemblies);
    }

    public Assembly first() throws StorageError {
        final List<String> keys = new ArrayList<String>(this.assemblies.keySet());
        if (keys.isEmpty()) {
            throw new StorageError("Empty storage");
        } else {
            return this.assemblies.get(keys.get(0));
        }
    }

}
