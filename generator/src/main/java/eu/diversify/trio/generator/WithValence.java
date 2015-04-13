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
/*
 */
package eu.diversify.trio.generator;

import eu.diversify.trio.core.requirements.Require;
import java.util.BitSet;

/**
 * Generator where the size is the number of variables used in the expression.
 * Variable can occurs several times, but will be counted only once.
 */
public class WithValence extends RequirementGenerator {

    private final BitSet isUsed;

    public WithValence(int size, double valence) {
        super(size, valence);
        this.isUsed = new BitSet(size);
    }

    @Override
    public Require aRequire() {
        final int selected = getRandom().nextInt(getCapacity());
        isUsed.set(selected, true);
        return new Require("C" + selected);
    }

    @Override
    protected void initialize() {
        isUsed.clear();
    }

    @Override
    public double actualSize() {
        return isUsed.cardinality();
    }

}
