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
import eu.diversify.trio.builder.TrioBaseVisitor;
import eu.diversify.trio.builder.TrioParser;
import eu.diversify.trio.core.requirements.Nothing;

/**
 * Build a component from the AST
 */
public class ComponentBuilder extends TrioBaseVisitor<Component> {

    @Override
    public Component visitComponent(TrioParser.ComponentContext ctx) {
        Requirement requirement = Nothing.getInstance();
        if (ctx.requirements() != null) {
            requirement = ctx.requirements().accept(new ExpressionBuilder());
        }
        return new Component(ctx.ID().getText(), requirement);
    }
    
    
}
