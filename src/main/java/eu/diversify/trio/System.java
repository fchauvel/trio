package eu.diversify.trio;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Represent the specification of a system under study. A collection component,
 * uniquely identified by their name.
 */
public class System {

    private final Map<String, Component> components;

    public System(Component... components) {
        this(Arrays.asList(components));
    }

    public System(Collection<Component> components) {
        this.components = new HashMap<String, Component>();
        for (Component each: components) {
            this.components.put(each.getName(), each);
        }
    }

    public Collection<String> getComponentNames() {
        return components.keySet();
    }

    public Topology instantiate() {
        return new Topology(this);
    }

    public Topology instantiate(Report report) {
        final Trace trace = new Trace(components.size());
        report.include(trace);
        return new Topology(this, new Listener(trace));        
    }

    public Component requirementOf(String eachComponent) {
        return components.get(eachComponent);
    }

}
