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

package eu.diversify.trio.core.adapters;

import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.core.Evaluation;
import eu.diversify.trio.core.requirements.Requirement;
import eu.diversify.trio.core.requirements.stats.VariableCount;
import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.graph.model.Vertex;
import java.util.HashMap;
import java.util.Map;

/**
 * Extract the graph underlying an assembly
 */
public class GraphAdapter {
    
    public Graph adapt(Assembly assembly) {
        final Graph graph = new Graph();
        
        final Map<String, Integer> idByName = new HashMap<String, Integer>();
        
        createVertexes(assembly, graph, idByName);
        createEdges(assembly, graph, idByName);
                
        return graph;
    }

    private void createEdges(Assembly assembly, final Graph graph, final Map<String, Integer> idByName) {
        for(String eachComponent: assembly.getComponentNames()) {
            final Vertex source = graph.vertexWithId(idByName.get(eachComponent));
            final VariableCount dependencies = dependenciesOf(assembly, eachComponent);
            for(String eachDependency: dependencies.getVariableNames()) {
                final Vertex destination = graph.vertexWithId(idByName.get(eachDependency));
                graph.connect(source, destination);
            }
        }
    }

    private void createVertexes(Assembly assembly, final Graph graph, final Map<String, Integer> idByName) {
        for(String eachName: assembly.getComponentNames()) {
            final Vertex vertex = graph.createVertex();
            idByName.put(eachName, vertex.id());
        }
    }

    private VariableCount dependenciesOf(Assembly assembly, String eachComponent) {
        final Requirement dependencies = assembly.requirementOf(eachComponent).getRequirement();
        VariableCount variables = new VariableCount();
        Evaluation.evaluate(variables).on(dependencies);
        return variables;
    }
    
}
