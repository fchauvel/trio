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
package eu.diversify.trio.builder;

import eu.diversify.trio.Component;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import eu.diversify.trio.System;

/**
 * Facade to the various 'builder' object that build sub part of the system
 */
public class Builder {

    /**
     * Factory method that increase the readibility of the calls
     */
    public static Builder build() {
        return new Builder();
    }

    public Component componentFrom(String text) {
        TrioLexer lexer = new TrioLexer(new ANTLRInputStream(text));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        TrioParser parser = new TrioParser(tokens);
        ParseTree tree = parser.system();
        return tree.accept(new ComponentBuilder());
    }

    public System systemFrom(String text) {
        TrioLexer lexer = new TrioLexer(new ANTLRInputStream(text));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        TrioParser parser = new TrioParser(tokens);
        ParseTree tree = parser.system();
        return tree.accept(new SystemBuilder());
    }

}
