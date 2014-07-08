package eu.diversify.trio.requirements;

import eu.diversify.trio.Requirement;

/**
 *
 */
public abstract class AbstractRequirement implements Requirement {

    public final Requirement and(Requirement right) {
        return new Conjunction(this, right);
    }

    public final Requirement or(Requirement right) {
        return new Disjunction(this, right);
    }

    public final Requirement not() {
        return new Negation(this);
    }

    @Override
    public final boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof AbstractRequirement) {
            return toString().equals(object.toString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public abstract String toString();

}
