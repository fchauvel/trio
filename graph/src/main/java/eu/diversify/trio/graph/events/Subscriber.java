
package eu.diversify.trio.graph.events;

/**
 *
 */
public interface Subscriber {
   
    void onVertexCreated(int vertexId);

    void onVertexesConnected(long edgeId);

}
