
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
