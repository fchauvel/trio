
package eu.diversify.trio.graph.events;

/**
 * The general event which can be emitted by the model
 */
public abstract class Event {
        
    private final long graphId;

    public Event(long graphId) {
        this.graphId = graphId;
    }

    public long getGraphId() {
        return graphId;
    }
        
    public abstract void sendTo(Subscriber handler);
    
}
