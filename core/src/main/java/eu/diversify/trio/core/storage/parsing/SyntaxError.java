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


package eu.diversify.trio.core.storage.parsing;

import java.util.Collections;
import java.util.List;

/**
 * Raised when some syntax errors are detected while building the system
 */
public class SyntaxError extends Exception {
    
    private static final long serialVersionUID = 1L;

    private final List<String> errors;

    public SyntaxError(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder(CAPACITY);
        buffer.append("Invalid model: ").append(getMessage()).append(System.lineSeparator());
        for(String eachError: getErrors()) {
            buffer.append(" - ").append(eachError).append(System.lineSeparator());
        }
        return buffer.toString();
    }
    
    public static final int CAPACITY = 200;
    
    
    
}
