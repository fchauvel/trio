/*
 */

package eu.diversify.trio;

import static eu.diversify.trio.requirements.Require.require;

/**
 *
 */
public class Samples {

    public static System A_require_B() {
        return new System(
            new Component("A", require("B")),
            new Component("B"));
    }
    
     public static System sample1() {
        return new System(
                new Component("A", require("B").and(require("C"))),
                new Component("B"),
                new Component("C", require("D").or(require("E"))),
                new Component("D"),
                new Component("E")
        );
    }
    
}
