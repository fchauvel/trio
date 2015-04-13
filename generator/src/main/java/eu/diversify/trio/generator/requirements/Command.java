package eu.diversify.trio.core.requirements.random;

/**
 * The command that can be used to build a requirement
 */
public enum Command {

    OPEN_CONJUNCTION,
    OPEN_DISJUNCTION, 
    ADD_NEGATION, 
    ADD_REQUIRE, 
    CLOSE

}