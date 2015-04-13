package eu.diversify.trio.graph;

import eu.diversify.trio.graph.queries.EdgePredicate;
import eu.diversify.trio.graph.queries.NodePredicate;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cache the requests processed by a graph, both regarding nodes and edges.
 *
 * The cached entries are hold as soft references and can be reclaimed by the GC
 * when memory get scarce.
 */
public class CachedGraph implements Graph {

    private final Graph delegate;

    private final Map<EdgePredicate, SoftReference<List<Edge>>> edgeCache;
    private final Map<NodePredicate, SoftReference<List<Node>>> nodeCache;

    public CachedGraph(Graph delegate) {
        this.delegate = delegate;
        edgeCache = new HashMap<>();
        nodeCache = new HashMap<>();
    }

    @Override
    public List<Node> nodes() {
        return delegate.nodes();
    }

    @Override
    public List<Node> nodes(NodePredicate predicate) {
        if (nodeCache.containsKey(predicate)) {
            List<Node> result = nodeCache.get(predicate).get();
            if (result != null) {
                return result;
            }
        }
        List<Node> result = delegate.nodes(predicate);
        nodeCache.put(predicate, new SoftReference<>(result));
        return result;
    }

    @Override
    public List<Edge> edges() {
        return delegate.edges();
    }

    @Override
    public List<Edge> edges(EdgePredicate predicate) {
        if (edgeCache.containsKey(predicate)) {
            List<Edge> result = edgeCache.get(predicate).get();
            if (result != null) {
                return result;
            }
        }
        List<Edge> result = delegate.edges(predicate);
        edgeCache.put(predicate, new SoftReference<>(result));
        return result;
    }

    @Override
    public Edge connect(Node source, Node target) {
        final Edge result = delegate.connect(source, target);
        clearCache();
        return result;
    }

    @Override
    public void disconnect(Edge edge) {
        delegate.disconnect(edge);
        clearCache();
    }

    private void clearCache() {
        nodeCache.clear();
        edgeCache.clear();
    }

}
