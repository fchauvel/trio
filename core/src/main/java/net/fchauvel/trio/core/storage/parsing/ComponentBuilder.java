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

import net.fchauvel.trio.core.Component;
import net.fchauvel.trio.core.requirements.Requirement;
import net.fchauvel.trio.builder.TrioBaseVisitor;
import net.fchauvel.trio.builder.TrioParser;
import net.fchauvel.trio.core.requirements.Nothing;

/**
 * Build a component from the AST
 */
public class ComponentBuilder extends TrioBaseVisitor<Component> {

    @Override
    public Component visitComponent(TrioParser.ComponentContext ctx) {
        double mttf = Component.DEFAULT_MTTF;
        Requirement requirement = Nothing.getInstance();
        if (ctx.mttf() != null) {
            mttf = Double.parseDouble(ctx.mttf().REAL().getText());
        }
        if (ctx.requirements() != null) {
            requirement = ctx.requirements().accept(new ExpressionBuilder());
        }
        return new Component(ctx.ID().getText(), mttf, requirement);
    }
    
    
}
