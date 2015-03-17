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
package eu.diversify.trio.core.requirements.random;

/**
 *
 */
public final class AddRequire implements Command {

    private int value;
    private static final int DEFAULT_VALUE = 0;

    public AddRequire() {
        value = DEFAULT_VALUE;
    }

    @Override
    public void sendTo(Builder target) {
        target.addRequire(value);
    }

    @Override
    public void randomize(Goal goal) {
        value = goal.randomLeafIndex();
    }

    @Override
    public String toString() {
        return "ADD(require)";
    }

}
