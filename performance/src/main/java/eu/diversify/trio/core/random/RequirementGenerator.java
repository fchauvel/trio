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

package eu.diversify.trio.core.random;

import eu.diversify.trio.core.requirements.*;
import java.util.Random;

/**
 *
 */
public abstract class RequirementGenerator {

    private static final double MAXIMUM_TOLERANCE = 10D;
    private static final double MINIMUM_TOLERANCE = -10D;

    private final CachedFactory factory;
    private final Random random;
    private final int capacity;
    private final double expectedSize;

    public RequirementGenerator(int capacity, double expectedSize) {
        this(capacity, expectedSize, new CachedFactory(), new Random());
    }

    public RequirementGenerator(int capacity, double expectedSize, CachedFactory factory) {
        this(capacity, expectedSize, factory, new Random());
    }

    public RequirementGenerator(int capacity, double expectedSize, Random random) {
        this(capacity, expectedSize, new CachedFactory(), random);
    }

    public RequirementGenerator(int capacity, double expectedSize, CachedFactory factory, Random random) {
        this.capacity = capacity;
        this.expectedSize = expectedSize;
        this.random = random;
        this.factory = factory;
    }

    protected int getCapacity() {
        return this.capacity;
    }

    protected Random getRandom() {
        return random;
    }

    public Requirement requirement() {
        initialize();
        final Requirement result = aRequirement();
        if (error() > MAXIMUM_TOLERANCE) {
            return requirement();
        }
        return result;
    }

    protected abstract void initialize();

    public Requirement aRequirement() {
        if (error() < MINIMUM_TOLERANCE) {
            return aRequire();
        }
        final int draw = random.nextInt(5);
        Requirement result = null;
        switch (draw) {
            case 0:
                result = nothing();
                break;
            case 1:
                result = aRequire();
                break;
            case 2:
                result = aConjunction();
                break;
            case 3:
                result = aDisjunction();
                break; 
            case 4:
                result = aNegation();
                break;
            default:
                throw new RuntimeException("Wrong number of logical operators");
        }
        return result;
    }

    public double error() {
        return 100D * (expectedSize - actualSize()) / expectedSize;
    }

    protected abstract double actualSize();

    public Requirement nothing() {
        return factory.nothing();
    }

    public Requirement aRequire() {
        final int selected = random.nextInt(capacity);
        return factory.createRequire("C" + selected);
    }

    public Requirement aConjunction() {
        return factory.createConjunction(aRequirement(), aRequirement());
    }

    public Requirement aDisjunction() {
        return factory.createDisjunction(aRequirement(), aRequirement());
    }

    public Requirement aNegation() {
        return factory.createNegation(aRequirement());
    }

}
