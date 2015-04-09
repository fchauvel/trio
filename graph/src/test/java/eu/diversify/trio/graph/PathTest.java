package eu.diversify.trio.graph;

import static eu.diversify.trio.graph.Node.node;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

/**
 * Specification of paths in a a graph
 */
public class PathTest {

    @Test
    public void shouldProvideTheirLength() {
        Path path = new Path(node(0), node(1), node(3));

        assertThat(path.length(), is(equalTo(2)));
    }

    @Test
    public void shouldBeExtensible() {
        Path path = new Path(node(1), node(2));
        
        Path extended = path.append(node(3));
        
        assertThat(extended, is(equalTo(new Path(node(1), node(2), node(3)))));
        
    }
    
    @Test
    public void infinitePathShouldHaveAnInfiniteLength() {
        Path path = Path.infinite(node(1));
        assertThat(path.length(), is(equalTo(Integer.MAX_VALUE)));
    }
    
    @Test
    public void infinitePathShouldNotBeDefined() {
        Path path = Path.infinite(node(1));
        assertThat(path.isDefined(), is(false));
    }
    
    
    @Test
    public void concretePathShouldBeDefined() {
        Path path = new Path(node(1), node(2));
        assertThat(path.isDefined(), is(true));
    }

    @Test
    public void shouldFindShortestPath() {
        Path p1 = new Path(node(0), node(1), node(3));
        Path p2 = new Path(node(0), node(3));

        Path shortest = Path.getShortest(p1, p2);

        assertThat(shortest, is(equalTo(p2)));

    }
}
