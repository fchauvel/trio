package eu.diversify.trio.graph.model;

/**
 * Edge: a directed link between two vertexes
 */
public class Edge {

    private final long id;
    private final Vertex source;
    private final Vertex target;

    public Edge(Long edgeId, Vertex source, Vertex target) {
        this.id = edgeId;
        this.source = source;
        this.target = target;
    }

    public long id() {
        return this.id;
    }
    
    public Vertex source() {
        return source;
    }
    
    public boolean comesFrom(Vertex vertex) {
        return source.equals(vertex);
    }
    
    public Vertex destination() {
        return target;
    }

}
