/**
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

package net.fchauvel.trio.core.storage.parsing;

import net.fchauvel.trio.core.requirements.Requirement;
import net.fchauvel.trio.builder.TrioBaseVisitor;
import net.fchauvel.trio.builder.TrioParser;
import net.fchauvel.trio.core.requirements.Conjunction;
import net.fchauvel.trio.core.requirements.Disjunction;
import net.fchauvel.trio.core.requirements.Require;

/**
 * Walk the sub part of the AST about expression and produces the requirement
 * attached to a component.
 */
public class ExpressionBuilder extends TrioBaseVisitor<Requirement> {

    @Override
    public Requirement visitRequirements(TrioParser.RequirementsContext ctx) {
        return ctx.expression().accept(this);
    }

    @Override
    public Requirement visitReference(TrioParser.ReferenceContext ctx) {
        return new Require(ctx.ID().getText());
    }

    @Override
    public Requirement visitConjunction(TrioParser.ConjunctionContext ctx) {
        Requirement left = ctx.left.accept(this);
        Requirement right = ctx.right.accept(this);
        return new Conjunction(left, right);
    }

    @Override
    public Requirement visitDisjunction(TrioParser.DisjunctionContext ctx) {
        Requirement left = ctx.left.accept(this);
        Requirement right = ctx.right.accept(this);
        return new Disjunction(left, right);
    }

    @Override
    public Requirement visitBrackets(TrioParser.BracketsContext ctx) {
        return ctx.expression().accept(this);
    }


}
