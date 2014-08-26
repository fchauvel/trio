/**
 *
 * This file is part of TRIO.
 *
 * TRIO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.diversify.trio.core.requirements.random;

import eu.diversify.trio.core.requirements.Conjunction;
import eu.diversify.trio.core.requirements.Disjunction;
import eu.diversify.trio.core.requirements.Negation;
import eu.diversify.trio.core.requirements.Require;
import eu.diversify.trio.core.requirements.Requirement;
import eu.diversify.trio.core.requirements.RequirementFactory;

/**
 * Pre-build the createRequire objects for given number of components, that will be
 shared among requirements.
 */
public class CachedLiteralFactory extends RequirementFactory {

    private static final int CAPACITY = 10191;
    private final int size;
    private final Require[] prebuilt;

    public CachedLiteralFactory(int limit) {
        assert limit < CAPACITY:
                "The given limit " + limit + " exceeds the actual capacity " + CAPACITY;
        
        this.size = limit;
        this.prebuilt = new Require[CAPACITY];
        for (int i = 0; i < size; i++) {
            prebuilt[i] = new Require("C" + i);
        }
    }
    
    public Requirement createRequire(int hash) {
        return (prebuilt[hash] != null) ? prebuilt[hash] : new Require("C" + hash);
    }

    public Requirement createRequire(String componentName) {
        int index = componentName.hashCode() % CAPACITY;
        return (prebuilt[index] != null) ? prebuilt[index] : new Require(componentName);
    }

    public Requirement createConjunction(Requirement left, Requirement right) {
        return new Conjunction(left, right);
    }

    public Requirement createDisjunction(Requirement left, Requirement right) {
        return new Disjunction(left, right);
    }

    public Requirement createNegation(Requirement operand) {
        return new Negation(operand);
    }

}
