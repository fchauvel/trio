

package eu.diversify.trio.core;

import eu.diversify.trio.core.requirements.Conjunction;
import eu.diversify.trio.core.requirements.Disjunction;
import eu.diversify.trio.core.requirements.Negation;
import eu.diversify.trio.core.requirements.Nothing;
import eu.diversify.trio.core.requirements.Require;

/**
 * Listen the traversal of a system
 */
public interface SystemListener {
    
    void enterSystem(System system);
    
    void exitSystem(System system);
    
    void enterComponent(Component component);
    
    void exitComponent(Component component);
    
    void enterConjunction(Conjunction conjunction);
    
    void exitConjunction(Conjunction conjunction);
    
    void enterDisjunction(Disjunction disjunction);
    
    void exitDisjunction(Disjunction disjunction);
    
    void enterNegation(Negation negation);
    
    void exitNegation(Negation negation);
    
    void enterNothing(Nothing nothing);
    
    void exitNothing(Nothing nothing);
    
    void enterRequire(Require require);
    
    void exitRequire(Require require);
    
    void enterTag(Tag tag);
    
    void exitTag(Tag tag);

}
