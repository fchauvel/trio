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

import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

/**
 * Listen to error reported by the lexers of parsers and store them fore later
 * use.
 */
public class SystemErrorListener extends BaseErrorListener {

    private final List<String> errors;

    public SystemErrorListener() {
        this.errors = new LinkedList<String>();
    }

    /**
     * Check whether some error were detected and raise an exception in that
     * very case.
     *
     * @throws SyntaxError contains the error detected
     */
    public void abortIfAnyError() throws SyntaxError {
        if (!errors.isEmpty()) {
            throw new SyntaxError(errors);
        }
    }

    @Override
    public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, ATNConfigSet configs) {
        final String error = String.format("Line %d: expected %s", stopIndex, configs.toString());
        errors.add(error);
    }

    @Override
    public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, ATNConfigSet configs) {
        final String error = String.format("Line %d: conflict between %s", stopIndex, conflictingAlts.toString());
        errors.add(error);
    }

    @Override
    public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {
        final String error = String.format("Line %d: Ambiguity between %s", stopIndex, ambigAlts.toString());
        errors.add(error);
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        final String error = String.format("Line %d (position %d): %s", line, charPositionInLine, msg);
        errors.add(error);
    }

}
