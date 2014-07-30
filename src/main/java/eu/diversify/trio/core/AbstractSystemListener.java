

package eu.diversify.trio.core;

import eu.diversify.trio.core.requirements.*;

/**
 * Default Implementation of the event handlers
 */
public class AbstractSystemListener implements SystemListener {

    
    protected void byDefault() {
        throw new UnsupportedOperationException("Not supported yet!");
    }

    public void enterSystem(System system) {
        byDefault();
    }

    public void exitSystem(System system) {
        byDefault();
    }

    public void enterComponent(Component component) {
        byDefault();
    }

    public void exitComponent(Component component) {
        byDefault();
    }

    public void enterConjunction(Conjunction conjunction) {
        byDefault();
    }

    public void exitConjunction(Conjunction conjunction) {
        byDefault();
    }

    public void enterDisjunction(Disjunction disjunction) {
        byDefault();
    }

    public void exitDisjunction(Disjunction disjunction) {
        byDefault();
    }

    public void enterNegation(Negation negation) {
        byDefault();
    }

    public void exitNegation(Negation negation) {
        byDefault();
    }

    public void enterNothing(Nothing nothing) {
        byDefault();
    }

    public void exitNothing(Nothing nothing) {
        byDefault();
    }

    public void enterRequire(Require require) {
        byDefault();
    }

    public void exitRequire(Require require) {
        byDefault();
    }

    public void enterTag(Tag tag) {
        byDefault();
    }

    public void exitTag(Tag tag) {
        byDefault();
    }
        
}
