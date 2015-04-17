package eu.diversify.trio.graph.statistics;

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
        Path path = new Path(new Long[]{0L, 1L, 3L});
        
        assertThat(path.length(), is(equalTo(3)));
    }

    @Test
    public void shouldBeExtensible() {
        Path path = new Path(new Long[]{1L, 2L});
        
        Path extended = path.append(3);
        
        assertThat(extended, is(equalTo(new Path(new Long[]{1L, 2L, 3L}))));
        
    }
    
       
    @Test
    public void concretePathShouldBeDefined() {
        Path path = new Path(new Long[]{1L, 2L});
        assertThat(path.isDefined(), is(true));
    }

    @Test
    public void shouldFindShortestPath() {
        Path p1 = new Path(new Long[]{1L, 2L, 3L});
        Path p2 = new Path(new Long[]{0L, 3L});

        Path shortest = Path.getShortest(p1, p2);

        assertThat(shortest, is(equalTo(p2)));

    }
}
