package eu.diversify.trio.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A 'tag' used to classify the component of the system
 */
public class Tag {

    private final String label;
    private final Set<String> targets;

    public Tag(String label, String... targets) {
        this(label, Arrays.asList(targets));
    }

    public Tag(String label, Collection<String> targets) {
        this.label = abortIfInvalid(label);
        this.targets = new HashSet<String>(abortIfInvalid(targets));
    }

    private String abortIfInvalid(String label) {
        if (label == null) {
            throw new IllegalArgumentException("Invalid 'null' value given a tag label!");
        }
        return label;
    }

    private Collection<String> abortIfInvalid(Collection<String> targets) {
        if (targets == null) {
            throw new IllegalArgumentException("Invalid 'null' values given as tag's target");
        }
        if (targets.isEmpty()) {
            throw new IllegalArgumentException("Invalid tags without any target ([] found)!");
        }
        return targets;
    }
    
    public void accept(SystemListener listener) {
        listener.enterTag(this);
        listener.exitTag(this);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.label.hashCode();
        hash = 97 * hash + this.targets.hashCode();
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
        final Tag other = (Tag) obj;
        return label.equals(other.label) && targets.equals(other.targets);
    }

    public String getLabel() {
        return label;
    }

    public Set<String> getTargets() {
        return Collections.unmodifiableSet(targets);
    }

    @Override
    public String toString() {
        return String.format("%s on %s", label, targets.toString());
    }

}
