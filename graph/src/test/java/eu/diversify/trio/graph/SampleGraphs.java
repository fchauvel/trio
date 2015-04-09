
package eu.diversify.trio.graph;

/**
 * Defines various graphs for testing purpose
 */
public class SampleGraphs {
    
     public static Graph twoNodesRing() {
        boolean matrixData[][] = new boolean[][]{
            {false, true},
            {true, false}
        };
        return new AdjacencyMatrix(matrixData);
    }
     
     public static Graph twoPaths() {
        boolean matrixData[][] = new boolean[][]{
            {false, true,   true    },
            {false, false,  false   },
            {false, true,   false   }
        };
        return new AdjacencyMatrix(matrixData);
    }
    
     
     public static Graph aLoopInTheMiddle() {
         boolean matrixData[][] = new boolean[][]{
            {false, true,   false,  false   },
            {false, false,  true,   false   },
            {false, true,   false,  true    },
            {false, false,  false,  false    }
        };
        return new AdjacencyMatrix(matrixData);
     }
}
