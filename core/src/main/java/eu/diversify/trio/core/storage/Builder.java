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

package eu.diversify.trio.core.storage;

import eu.diversify.trio.core.Component;
import eu.diversify.trio.core.requirements.Requirement;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.builder.TrioLexer;
import eu.diversify.trio.builder.TrioParser;
import java.io.IOException;
import java.io.InputStream;

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

    public Requirement requirementFrom(String text) {
        TrioLexer lexer = new TrioLexer(new ANTLRInputStream(text));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        TrioParser parser = new TrioParser(tokens);
        ParseTree tree = parser.requirements();
        return tree.accept(new ExpressionBuilder());
    }

    public Component componentFrom(String text) {
        TrioLexer lexer = new TrioLexer(new ANTLRInputStream(text));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        TrioParser parser = new TrioParser(tokens);
        ParseTree tree = parser.component();
        return tree.accept(new ComponentBuilder());
    }

    public Assembly systemFrom(String text) {
        TrioLexer lexer = new TrioLexer(new ANTLRInputStream(text));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        TrioParser parser = new TrioParser(tokens);
        ParseTree tree = parser.system();
        return tree.accept(new SystemBuilder());
    }

    public Assembly systemFrom(InputStream input) throws IOException, SyntaxError {
        SystemErrorListener listener = new SystemErrorListener();
        TrioLexer lexer = new TrioLexer(new ANTLRInputStream(input));
        lexer.removeErrorListeners();
        lexer.addErrorListener(listener);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        TrioParser parser = new TrioParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(listener);
        ParseTree tree = parser.system();
        listener.abortIfAnyError();
        return tree.accept(new SystemBuilder());
    }

}
