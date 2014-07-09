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

import eu.diversify.trio.Requirement;
import eu.diversify.trio.requirements.Require;

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

}
