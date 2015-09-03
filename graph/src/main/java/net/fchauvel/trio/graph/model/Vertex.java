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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Vertex in a graph
 */
public class Vertex {

    private final int id;
    private final Set<Edge> outgoingEdges;
    private final Set<Edge> incomingEdges;

    public Vertex(int id) {
        this.id = id;
        this.outgoingEdges = new HashSet<>();
        this.incomingEdges = new HashSet<>();
    }

    public int id() {
        return this.id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vertex other = (Vertex) obj;
        return this.id == other.id;
    }

    public boolean isDisconnected() {
        return outgoingEdges.isEmpty() && incomingEdges.isEmpty();
    }

    public Set<Edge> incomingEdges() {
        return Collections.unmodifiableSet(incomingEdges);
    }

    public Set<Edge> outgoingEdges() {
        return Collections.unmodifiableSet(outgoingEdges);
    }

    public Set<Vertex> successors() {
        final Set<Vertex> successors = new HashSet<>(outgoingEdges.size());
        for (Edge eachOutgoingEdge : outgoingEdges) {
            successors.add(eachOutgoingEdge.destination());
        }
        return Collections.unmodifiableSet(successors);
    }

    public Set<Vertex> predecessors() {
        final Set<Vertex> predecessors = new HashSet<>(incomingEdges.size());
        for (Edge eachOutgoingEdge : incomingEdges) {
            predecessors.add(eachOutgoingEdge.source());
        }
        return Collections.unmodifiableSet(predecessors);
    }

    public boolean isSuccessorOf(Vertex predecessor) {
        for (Edge anyIncomingEdge : incomingEdges) {
            if (anyIncomingEdge.comesFrom(predecessor)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPredecessorOf(Vertex successor) {
        return successor.isSuccessorOf(this);
    }

    void addOutgoingEdge(Edge newEdge) {
        assert newEdge != null : "Cannot add 'null' as an outgoing edge!";
        this.outgoingEdges.add(newEdge);
    }

    void addIncomingEdge(Edge newEdge) {
        assert newEdge != null : "Cannot add 'null' as an incoming edge!";
        incomingEdges.add(newEdge);
    }

    void removeOutgoingEdge(Edge edge) {
        assert edge != null : "Cannot remove the 'null' outgoing edge!";
        outgoingEdges.remove(edge);
    }

    void removeIncomingEdge(Edge edge) {
        assert edge != null : "Cannot remove the 'null' incoming edge!";
        incomingEdges.remove(edge);
    }

    @Override
    public String toString() {
        return "v" + id;
    }
    
    

}
