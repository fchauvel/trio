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

package eu.diversify.trio.graph.model;

import eu.diversify.trio.graph.events.Event;
import eu.diversify.trio.graph.events.Subscriber;



/**
 *
 */
public class VertexCreated extends Event {
    
    private final int vertexId;

    public VertexCreated(long graphId, int vertexId) {
        super(graphId);
        this.vertexId = vertexId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.vertexId;
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
        final VertexCreated other = (VertexCreated) obj;
        return this.vertexId == other.vertexId;
    }

    @Override
    public String toString() {
        return vertexId + " created";
    }

    @Override
    public void sendTo(Subscriber handler) {
        handler.onVertexCreated(vertexId);
    }
    
    
    
}
