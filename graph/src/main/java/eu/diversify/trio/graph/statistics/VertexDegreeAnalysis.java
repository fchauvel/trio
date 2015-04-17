package eu.diversify.trio.graph.statistics;

import eu.diversify.trio.graph.Services;
import eu.diversify.trio.graph.events.Subscriber;
import eu.diversify.trio.graph.model.Edge;
import eu.diversify.trio.graph.model.Graph;
import eu.diversify.trio.graph.model.Vertex;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class VertexDegreeAnalysis {

    private final Graph graph;
    private final Map<Integer, Integer> inDegrees;
    private int totalInDegree;
    private final Map<Integer, Integer> outDegrees;
    private int totalOutDegree;

    public VertexDegreeAnalysis(Graph subject) {
        this.graph = subject;
        this.inDegrees = new HashMap<>();
        this.outDegrees = new HashMap<>();

        for (Vertex eachVertex : graph.vertexes()) {
            final int inDegree = eachVertex.incomingEdges().size();
            this.inDegrees.put(eachVertex.id(), inDegree);
            totalInDegree += inDegree;

            final int outDegree = eachVertex.outgoingEdges().size();
            this.outDegrees.put(eachVertex.id(), outDegree);
            totalOutDegree += outDegree;
        }

        Services.registry().events()
                .subscribe(subject.id(), new Subscriber() {

                    @Override
                    public void onVertexCreated(int vertexId) {
                        inDegrees.put(vertexId, graph.vertexWithId(vertexId).incomingEdges().size());
                        outDegrees.put(vertexId, graph.vertexWithId(vertexId).outgoingEdges().size());
                    }

                    @Override
                    public void onVertexesConnected(long edgeId) {
                        Edge edge = graph.edgeWithId(edgeId);
                        updateInDegrees(edge);
                        updateOutDegree(edge);
                    }

                    private void updateOutDegree(Edge edge) {
                        int sd = outDegrees.get(edge.source().id());
                        outDegrees.put(edge.source().id(), sd + 1);
                        totalOutDegree += 1;
                    }

                    private void updateInDegrees(Edge edge) {
                        int dd = inDegrees.get(edge.destination().id());
                        inDegrees.put(edge.destination().id(), dd + 1);
                        totalInDegree += 1;
                    }

                });
    }

    public int inDegreeOf(int vertexId) {
        assert inDegrees.containsKey(vertexId): "Unknown vertex with id " + vertexId;
        return inDegrees.get(vertexId);
    }

    public int inDegreeOf(Vertex vertex) {
        return inDegreeOf(vertex.id());
    }

    public int outDegreeOf(int vertexId) {
        assert outDegrees.containsKey(vertexId): "Unknown vertex with id " + vertexId;
        return outDegrees.get(vertexId);
    }

    public int outDegreeOf(Vertex vertex) {
        return outDegreeOf(vertex.id());
    }
    
    public int totalInDegree() {
        return totalInDegree;
    }
    
    public int totalOutDegree() {
        return totalOutDegree;
    }

}
