
package eu.diversify.trio.analytics.events;

/**
 * Metadata describing a given statistic
 */
public class Statistic {
    
    private final int scenarioId;
    private final int sequenceId;
    private final String name;

    
    public Statistic(int scenarioId, int sequenceId, String name) {
        this.scenarioId = scenarioId;
        this.sequenceId = sequenceId;
        this.name = checkName(name);
    }

    public int getScenarioId() {
        return scenarioId;
    }

    public int getSequenceId() {
        return sequenceId;
    }

    public String getName() {
        return name;
    }
    
        
    private String checkName(String name) {
        if (name == null) {
            throw new NullPointerException("Invalid statistic name ('null' found)");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Invalid statistic name ('' empty string found)");
        }
        return name;
    } 

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.scenarioId;
        hash = 29 * hash + this.sequenceId;
        hash = 29 * hash + (this.name != null ? this.name.hashCode() : 0);
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
        final Statistic other = (Statistic) obj;
        if (this.scenarioId != other.scenarioId) {
            return false;
        }
        if (this.sequenceId != other.sequenceId) {
            return false;
        }
        return !((this.name == null) ? (other.name != null) : !this.name.equals(other.name));
    }
        
    @Override
    public String toString() {
        return String.format("%d/%d/%s", scenarioId, sequenceId, name);
    }
}