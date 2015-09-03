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
 * This file is part of TRIO :: Graph.
 *
 * TRIO :: Graph is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Graph is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Graph.  If not, see <http://www.gnu.org/licenses/>.
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
 * This file is part of TRIO :: Graph.
 *
 * TRIO :: Graph is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Graph is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Graph.  If not, see <http://www.gnu.org/licenses/>.
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
package net.fchauvel.trio.graph.model;

import net.fchauvel.trio.graph.Services;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Directed Graph
 */
public class Graph {

    private static final long DEFAULT_GRAPH_ID = 1L;

    private final long id;
    
    private final Map<Integer, Vertex> vertexes;
    private final Map<Long, Edge> edges;

    private int vertexIdSequence;
    private long edgeIdSequence;

    public Graph() {
        this(DEFAULT_GRAPH_ID);
    }
    
    public Graph(long graphId) {
        this.id = graphId;
        this.vertexes = new HashMap<>();
        this.edges = new HashMap<>();
        vertexIdSequence = -1;
        edgeIdSequence = -1;
    }

    public long id() {
        return id;
    }
    
    public Collection<Vertex> vertexes() {
        return Collections.unmodifiableCollection(this.vertexes.values());
    }

    public int vertexCount() {
        return this.vertexes.size();
    }
    
    public Vertex vertexWithId(int id) {
        if (!vertexes.containsKey(id)) {
            throw new IllegalArgumentException("Unknown vertex with ID '" + id + "'");
        }
        return vertexes.get(id);
    }

    public Collection<Edge> edges() {
        return new ArrayList<>(this.edges.values());
    }
    
    public boolean hasEdge(Vertex source, Vertex destination) {
        validateVertex(source);
        validateVertex(destination);
        
        return destination.isSuccessorOf(source);
    }
    
    public Edge edgeWithId(long edgeId) {
        if (!edges.containsKey(edgeId)) {
            throw new IllegalArgumentException("Unknown edge with ID '" + edgeId + "'");
        }
        return edges.get(edgeId);
    }

    public boolean isEmpty() {
        return this.edges.isEmpty();
    }

    public int edgeCount() {
        return this.edges.size();
    }

    public Vertex createVertex() {
        final Vertex newVertex = new Vertex(nextVertexId());
        this.vertexes.put(newVertex.id(), newVertex);
        
        Services.registry().events().publish(new VertexCreated(id, newVertex.id()));
        
        return newVertex;
    }

    public void removeVertex(Vertex vertex) {
        validateVertex(vertex);

        for (Edge eachIncomingEdge : vertex.incomingEdges()) {
            disconnect(eachIncomingEdge);
        }

        for (Edge eachOutgoingEdge : vertex.outgoingEdges()) {
            disconnect(eachOutgoingEdge);
        }

        vertexes.remove(vertex.id());
    }

    private void validateVertex(Vertex vertex) throws IllegalArgumentException {
        if (vertex == null) {
            throw new IllegalArgumentException("Cannot use 'null' as a vertex");
        }
        if (!vertexes.containsKey(vertex.id())) {
            throw new IllegalArgumentException("Unknown vertex with ID '" + vertex.id() + "'");
        }
    }

    public Edge connect(Vertex source, Vertex target) {
        validateVertex(source);
        validateVertex(target);

        final Edge newEdge = new Edge(nextEdgeId(), source, target);
        source.addOutgoingEdge(newEdge);
        target.addIncomingEdge(newEdge);
        this.edges.put(newEdge.id(), newEdge);

        Services.registry().events().publish(new EdgeCreated(id, newEdge.id()));
        
        return newEdge;
    }

    public void disconnect(Edge edge) {
        if (edge == null) {
            throw new IllegalArgumentException("Cannot use 'null' as an edge");
        }

        edge.source().removeOutgoingEdge(edge);
        edge.destination().removeIncomingEdge(edge);
        edges.remove(edge.id());
    }

    private long nextEdgeId() {
        edgeIdSequence += 1;
        return edgeIdSequence;
    }

    private int nextVertexId() {
        vertexIdSequence += 1;
        return vertexIdSequence;
    }

}
