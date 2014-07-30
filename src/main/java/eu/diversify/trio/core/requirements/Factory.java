package eu.diversify.trio.core.requirements;

import eu.diversify.trio.core.Requirement;

/**
 * Some factory methods to ease the definition of requirements
 */
public class Factory {

    public static Requirement not(Requirement operand) {
        return new Negation(operand);
    }

    public static Requirement nothing() {
        return Nothing.getInstance();
    }

    public static Requirement require(String component) {
        return new Require(component);
    }

}
