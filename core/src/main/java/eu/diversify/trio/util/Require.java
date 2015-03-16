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
package eu.diversify.trio.util;

import java.util.Collection;

/**
 * Emulate preconditions checks
 */
public class Require {

    public static void notEmpty(Collection<?> collection, String message, Object... parameters) {
        if (collection.isEmpty()) {
            illegalArgument(message, parameters);
        }
    }

    private static void illegalArgument(String message, Object[] parameters) throws IllegalArgumentException {
        throw new IllegalArgumentException(String.format(message, parameters));
    }

    public static void notNull(Object object, String message, Object... parameters) {
        if (object == null) {
            illegalArgument(message, parameters);
        }
    }

    public static void isTrue(boolean test, String message, Object... parameters) {
        if (!test) {
            illegalArgument(message, parameters);
        }
    }

}
