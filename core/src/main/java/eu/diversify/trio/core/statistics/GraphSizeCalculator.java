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
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.diversify.trio.core.statistics;

import eu.diversify.trio.core.Component;
import eu.diversify.trio.core.DefaultAssemblyVisitor;
import eu.diversify.trio.core.requirements.stats.VariableCount;
import eu.diversify.trio.util.Require;

/**
 * Compute the number of vertices and edges in the graph underlying a given
 * assembly.
 */
class GraphSizeCalculator extends DefaultAssemblyVisitor {

    private final VariableCount variableCount;
    private int componentCount;
    private int edgeCount;

    public GraphSizeCalculator(VariableCount variableCount) {
        Require.notNull(variableCount, "Invalid value 'null' given as variable counter");
        this.variableCount = variableCount;
    }

    @Override
    public void exit(Component component) {
        componentCount++;
        edgeCount += variableCount.get();
    }

    public VariableCount getVariableCounter() {
        return this.variableCount;
    }

    public int getNodeCount() {
        return componentCount;
    }
    
    public int getEdgeCount() {
        return edgeCount;
    }

}
