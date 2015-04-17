
package eu.diversify.trio.graph.model;

import eu.diversify.trio.graph.events.Event;
import eu.diversify.trio.graph.events.Subscriber;

/**
 * Edge creation
 */
public class EdgeCreated extends Event {
    
    private final long edgeId;

    public EdgeCreated(long graphId, long edgeId) {
        super(graphId);
        this.edgeId = edgeId;
    }
    
    
    @Override
    public void sendTo(Subscriber handler) {
        handler.onVertexesConnected(edgeId);
    }
    
}
