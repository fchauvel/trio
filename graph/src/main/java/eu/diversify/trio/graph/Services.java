
package eu.diversify.trio.graph;

import eu.diversify.trio.graph.events.Dispatcher;

/**
 * The service registry
 */
public class Services {
    
    private static Services services;
    
    public static Services registry() {
        if (services == null) {
            services = new Services();
        }
        return services;
    }
    
    private final GraphFactory factory;
    private final Dispatcher dispatcher;

    public Services() {
        this.factory = new GraphFactory();
        this.dispatcher = new Dispatcher();
    }
        
    public GraphFactory factory() {
        return factory;
    }
    
    public Dispatcher events() {
        return dispatcher;
    }
    
}
