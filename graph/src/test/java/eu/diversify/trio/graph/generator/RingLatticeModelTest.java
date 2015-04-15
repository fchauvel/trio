package eu.diversify.trio.graph.generator;

import static eu.diversify.trio.graph.Node.node;
import eu.diversify.trio.graph.util.Count;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

/**
 * Specification of the ring lattice neighborhood
 */
public class RingLatticeModelTest {

    @Test
    public void shouldAllowEdgesWithinTheUpperNeighborhood() {
        final RingLatticeGenerator neighborhood = new RingLatticeGenerator(new Count(100), new Count(10));
        assertThat(neighborhood.allowsEdgeBetween(node(5), node(6)), is(true));
    }

    @Test
    public void shouldAllowEdgesWithinTheLowerNeighborhood() {
        final RingLatticeGenerator neighborhood = new RingLatticeGenerator(new Count(100), new Count(10));
        assertThat(neighborhood.allowsEdgeBetween(node(5), node(4)), is(true));
    }

    @Test
    public void shouldForbidSelfLoops() {
        final RingLatticeGenerator neighborhood = new RingLatticeGenerator(new Count(40), new Count(6));
        for (int n = 0; n < 40; n++) {
            assertThat(neighborhood.allowsEdgeBetween(node(n), node(n)), is(false));
        }
    }

    @Test
    public void shouldForbidEdgesWithoutTheNeighborhood() {
        final RingLatticeGenerator neighborhood = new RingLatticeGenerator(new Count(100), new Count(6));
        assertThat(neighborhood.allowsEdgeBetween(node(5), node(16)), is(false));
    }

    @Test
    public void shoudlAllowEdgesOverTheFirstAndLastNodes() {
        final RingLatticeGenerator neighborhood = new RingLatticeGenerator(new Count(100), new Count(10));
        assertThat(neighborhood.allowsEdgeBetween(node(3), node(99)), is(true));
    }

    @Test
    public void shoudlPreventEdgesTooFarBeforeLastNodes() {
        final RingLatticeGenerator neighborhood = new RingLatticeGenerator(new Count(100), new Count(10));
        assertThat(neighborhood.allowsEdgeBetween(node(1), node(90)), is(false));
    }

}
