package eu.diversify.trio.graph;

import java.util.BitSet;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Specification of the adjacency matrix
 */
@RunWith(JUnit4.class)
public class AdjacencyMatrixTest {

    @Test
    public void matrixBuildFromBitSetShouldHaveACorrectEdgeCount() {
        AdjacencyMatrix graph = new AdjacencyMatrix(bitSetFrom("010101011"));
        assertThat(graph.edges().size(), is(equalTo(5)));
    }

    @Test
    public void matrixBuildFromBitSetShouldHaveACorrectNodeCount() {
        AdjacencyMatrix graph = new AdjacencyMatrix(bitSetFrom("010101011"));
        assertThat(graph.nodes().size(), is(equalTo(3)));
    }

    private BitSet bitSetFrom(final String binary) throws IllegalArgumentException {
        BitSet adjacency = new BitSet(binary.length());
        for (int eachDigit = 0; eachDigit < binary.length(); eachDigit++) {
            char digit = binary.charAt(eachDigit);
            switch (digit) {
                case '1':
                    adjacency.set(eachDigit);
                    break;
                case '0':
                    break;
                default:
                    throw new IllegalArgumentException("String should only contains '1' or '0' (found '" + digit + "')");
            }
        }
        return adjacency;
    }

}
