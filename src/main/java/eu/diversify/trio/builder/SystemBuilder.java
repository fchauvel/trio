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
import eu.diversify.trio.System;
import eu.diversify.trio.Tag;
import java.util.ArrayList;
import java.util.List;

/**
 * Traverse the AST a build a System object
 */
public class SystemBuilder extends TrioBaseVisitor<System> {

    @Override
    public System visitSystem(TrioParser.SystemContext ctx) {
        final List<Component> components = extractComponents(ctx.component());
        final List<Tag> tags = extractTags(ctx);
        return new System(components, tags);
    }
    
    private List<Component> extractComponents(List<TrioParser.ComponentContext> components) {
        final ComponentBuilder componentBuilder = new ComponentBuilder();
        final List<Component> results = new ArrayList<Component>();
        for (TrioParser.ComponentContext eachComponent: components) {
            results.add(eachComponent.accept(componentBuilder));
        }
        return results;
    }

    private List<Tag> extractTags(TrioParser.SystemContext ctx) {
        final List<Tag> tags = new ArrayList<Tag>();
        if (ctx.tags() != null) {
            for (TrioParser.TagContext eachTag: ctx.tags().tag()) {
                tags.add(eachTag.accept(new TagBuilder()));
            }
        }
        return tags;
    }
    
}
