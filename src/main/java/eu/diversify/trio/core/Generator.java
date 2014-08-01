
package eu.diversify.trio.core;

import eu.diversify.trio.core.requirements.Conjunction;
import eu.diversify.trio.core.requirements.Disjunction;
import eu.diversify.trio.core.requirements.Negation;
import eu.diversify.trio.core.requirements.Require;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Generate randomised system description
 */
public class Generator {

    private final Random random;

    public Generator() {
        this(new Random(), DEFAULT_APLHA);
    }
    
    public Generator(Random random) {
        this(random, DEFAULT_APLHA);
    }
    private static final double DEFAULT_APLHA = 0.75;
    
    public Generator(double alpha) {
        this(new Random(), alpha);
    }

    public Generator(Random random, double alpha) {
        this.random = random;
        this.alpha = alpha;
    }

    public System randomSystem(int componentCount) {
        final List<String> names = componentNames(componentCount);
        final List<Component> components = new LinkedList<Component>();
        for(String eachName: names) {
            components.add(randomComponent(names, eachName));
        }
        final List<Tag> tags = new ArrayList<Tag>();
        return new System("randomly generated", components, tags);
    }

    private List<String> componentNames(int count) {
        final List<String> results = new ArrayList<String>(count);
        for (int i = 0; i < count; i++) {
            results.add("C" + (i + 1));
        }
        return results;
    }

    public Component randomComponent(List<String> names, String name) {
        return new Component(name, nextRequirement(names));
    }

    public Requirement nextRequirement(List<String> names) {
        return nextRequirement(names, 1);
    }

    public Requirement nextRequirement(List<String> names, int depth) {
        final double threshold = Math.pow(alpha, depth);
        double draw = random.nextDouble();
        if (draw > threshold) {
            return randomRequire(names);
        } else {
            draw = random.nextDouble();
            if (draw < 1D / 3) {
                return randomNegation(names, depth+1);
            } else if (draw < 2D / 3) {
                return randomConjunction(names, depth+1);
            } else {
                return randomDisjunction(names, depth+1);
            }
        }
    }
    
    private final double alpha;

    public Require randomRequire(List<String> names) {
        return new Require(anyFrom(names));
    }

    private String anyFrom(List<String> names) {
        final int size = names.size();
        if (size == 0) {
            throw new IllegalArgumentException("Unable to choose from an empty collection");
        }
        if (size == 1) {
            return names.get(0);
        }
        return names.get(random.nextInt(size));
    }

    public Negation randomNegation(List<String> names) {
        return randomNegation(names, 1);
    }

    public Negation randomNegation(List<String> names, int depth) {
        return new Negation(nextRequirement(names, depth));
    }

    public Conjunction randomConjunction(List<String> names) {
        return randomConjunction(names, 1);
    }

    public Conjunction randomConjunction(List<String> names, int depth) {
        final Requirement left = nextRequirement(names, depth);
        final Requirement right = nextRequirement(names, depth);
        return new Conjunction(left, right);
    }

    public Disjunction randomDisjunction(List<String> names) {
        return randomDisjunction(names, 1);
    }

    public Disjunction randomDisjunction(List<String> names, int depth) {
        final Requirement left = nextRequirement(names, depth);
        final Requirement right = nextRequirement(names, depth);
        return new Disjunction(left, right);
    }

}
