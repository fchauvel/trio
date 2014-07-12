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


package eu.diversify.trio.filter;

import eu.diversify.trio.core.System;
import java.util.Set;

/**
 * Filter that filter nothing, it select all the components
 * 
 * Singleton
 */
public class All extends Filter {

    private static All instance;
    
    public static Filter getInstance() {
        if (instance == null) {
            instance = new All();
        }
        return instance;
    }
    
    private All() {};
    
    @Override
    public Set<String> resolve(System system) {
        return system.getComponentNames();
    }

    @Override
    public String toString() {
        return "*";
    }
    
    
    
}
