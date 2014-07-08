package eu.diversify.trio;

import java.util.ArrayList;
import java.util.List;

/**
 * Aggregate information obtained during extinction sequences
 */
public class Report {
        
    private final List<Trace> traces;
   
    public Report() {
        this.traces = new ArrayList<Trace>();
    }
  
    public void include(Trace trace) {
        this.traces.add(trace);
    }
    
    public Trace get(int index) {
        return traces.get(0);
    }

}
