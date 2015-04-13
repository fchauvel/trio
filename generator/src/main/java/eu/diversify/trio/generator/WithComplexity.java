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
package eu.diversify.trio.generator;

import eu.diversify.trio.core.requirements.*;

/**
 * Generates expression with a fixed complexity (i.e., a fixed number of logical
 * operators)
 */
public class WithComplexity extends RequirementGenerator {

    private int size;

    public WithComplexity(int capacity, double expectedSize) {
        super(capacity, expectedSize);
    }

    public WithComplexity(int capacity, double expectedSize, CachedFactory factory) {
        super(capacity, expectedSize, factory);
    }

    @Override
    protected void initialize() {
        size = 1;
    }

    @Override
    public double actualSize() {
        return size;
    }

    @Override
    public Requirement aNegation() {
        size += 1;
        return super.aNegation();
    }

    @Override
    public Requirement aDisjunction() {
        size += 2;
        return super.aDisjunction();
    }

    @Override
    public Requirement aConjunction() {
        size += 2;
        return super.aConjunction();
    }

}
