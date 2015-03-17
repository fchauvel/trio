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
package eu.diversify.trio.core.requirements;

/**
 * Factory class that ease the construction of requirements.
 */
public abstract class RequirementFactory {

    public Requirement createRequire(int hash) {
        return new Require("C" + hash);
    }

    public Requirement createRequire(String componentName) {
        return new Require(componentName);
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