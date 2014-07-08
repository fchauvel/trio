package eu.diversify.trio.requirements;

import eu.diversify.trio.Topology;
import eu.diversify.trio.requirements.AbstractRequirement;

/**
 * The true boolean value. Singleton value
 *
 */
public class Nothing extends AbstractRequirement {

    private static Nothing instance = null;

    public static Nothing getInstance() {
        if (instance == null) {
            instance = new Nothing();
        }
        return instance;
    }

    public boolean isSatisfiedBy(Topology population) {
        return true;
    }

    @Override
    public String toString() {
        return String.format("none");
    }

}
