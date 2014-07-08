
package eu.diversify.trio;

import eu.diversify.trio.requirements.Nothing;

/**
 * The definition of a component in the system
 */
public class Component {
    
    private final String name;
    private final Requirement requirement;

    public Component(String name) {
        this(name, Nothing.getInstance()); 
    }
    
    public Component(String name, Requirement requirement) {
        this.name = name;
        this.requirement = requirement;
    }

    public String getName() {
        return name;
    }

    public Requirement getRequirement() {
        return requirement;
    }
    
    public boolean isSatisfiedIn(Topology topology) {
        return requirement.isSatisfiedBy(topology);
    }
    
}
