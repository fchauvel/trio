
package eu.diversify.trio.unit.core.adapters;

import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.core.Component;
import eu.diversify.trio.core.adapters.GraphAdapter;
import static eu.diversify.trio.core.requirements.Factory.not;
import static eu.diversify.trio.core.requirements.Factory.require;
import eu.diversify.trio.graph.model.Graph;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

/**
 *
 */
public class GraphAdapterTest {
  
    @Test
    public void shouldConvertAssemblyToGraph() {
        Assembly assembly = new Assembly(
                new Component("C1", require("C2")),
                new Component("C2", require("C3").and(require("C4"))),
                new Component("C3", require("C5").or(require("C6"))),
                new Component("C4", not(require("C7"))),
                new Component("C5"),
                new Component("C6"),                
                new Component("C7")
                );
        
        GraphAdapter adapter = new GraphAdapter();
        Graph graph = adapter.adapt(assembly);
        
        assertThat(graph.vertexCount(), is(equalTo(assembly.size()))); 
        assertThat(graph.edgeCount(), is(equalTo(6)));
                
    }
    
}
