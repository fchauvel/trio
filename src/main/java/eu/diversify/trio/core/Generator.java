/**
 *
 * This file is part of TRIO.
 *
 * TRIO is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * TRIO is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO. If not, see <http://www.gnu.org/licenses/>.
 */
package eu.diversify.trio.core;

import eu.diversify.trio.core.requirements.Conjunction;
import eu.diversify.trio.core.requirements.Disjunction;
import eu.diversify.trio.core.requirements.Negation;
import eu.diversify.trio.core.requirements.Require;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Generate randomised system description
 */
public class Generator {

    private final Random random;

    public Generator() {
        this(new Random());
    }

    public Generator(Random random) {
        this.random = random;
    }

    public System randomSystem(int componentCount) {
        final Collection<String> names = componentNames(componentCount);
        final List<Component> components = new ArrayList<Component>();
        for (String eachName: names) {
            final List<String> otherNames = new ArrayList<String>(names);
            otherNames.remove(eachName);
            components.add(randomComponent(otherNames, eachName));
        }
        final List<Tag> tags = new ArrayList<Tag>();
        return new System("randomly generated", components, tags);
    }

    private List<String> componentNames(int count) {
        final List<String> results = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            results.add(String.format("C%d", i + 1));
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
        final double threshold = Math.pow(POWER_LAW, depth);
        double draw = random.nextDouble();
        if (draw > threshold) {
            return randomRequire(names);
        } else {
            draw = random.nextDouble();
            if (draw < 1D / 3) {
                return randomConjunction(names, depth+1);
            } else if (draw < 2D / 3) {
                return randomConjunction(names, depth+1);
            } else {
                return randomDisjunction(names, depth+1);
            }
        }
    }
    private static final double POWER_LAW = 0.75;

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
