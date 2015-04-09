
package eu.diversify.trio.graph;

/**
 * Represent a node in a graph
 */
public class Node {    
     
    public static Node node(int index) {
        return new Node(index);
    }
    
    private final int index;

    public Node(int index) {
        this.index = index;
    }
    
    public int index() {
        return this.index;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.index;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (this.index != other.index) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "N#" + index;
    }
    
    
    
}
