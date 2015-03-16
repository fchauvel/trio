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
package eu.diversify.trio.core.requirements.stats;

import eu.diversify.trio.core.Component;
import eu.diversify.trio.core.DefaultSystemVisitor;
import eu.diversify.trio.core.requirements.Require;
import java.util.HashSet;
import java.util.Set;

/**
 * Count the variable used in a requirement. Variables occurring multiple times
 * are counted only once.
 */
public class VariableCount extends DefaultSystemVisitor {

    private final Set<String> variableNames;

    public VariableCount() {
        this.variableNames = new HashSet<String>();
    }
    
    @Override
    public void enter(Require require) {
        variableNames.add(require.getRequiredComponent());
    }
    
    
    @Override
    public void enter(Component component) {
        this.variableNames.clear();
    }

    public int get() {
        return variableNames.size();
    }
    
}
