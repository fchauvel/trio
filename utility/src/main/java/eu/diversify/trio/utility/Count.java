
package eu.diversify.trio.utility;

/**
 * A value object to represent counts
 */
public class Count {
    
    private final int value;

    public Count(int value) {
        this.value = validate(value);
    }
    
    private int validate(int value) {
        if (value < 0) {
            final String description = String.format("A count must be positive (found %d)", value);
            throw new IllegalArgumentException(description);
        }
        return value;
    }
    
    public int value() {
        return this.value;
    }
    
    public boolean isEven() {
        return value % 2 == 0;
    }
    
    public boolean isOdd() {
        return !isEven();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.value;
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
        final Count other = (Count) obj;
        if (this.value != other.value) {
            return false;
        }
        return true;
    }
 
    
    
}
