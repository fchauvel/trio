
package eu.diversify.trio.graph.events;

/**
 *
 */
public interface Subscriber {
   
    void onVertexCreated(int vertexId);

    public void onVertexesConnected(long edgeId);

}
