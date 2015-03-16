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
/*
 */

package eu.diversify.trio.core;

import eu.diversify.trio.core.requirements.*;

/**
 * Ability to traverse a system
 */
public interface SystemVisitor {

    void enter(System system);

    void enter(Component component);

    void enter(Tag tag);

    void enter(Conjunction conjunction);

    void enter(Disjunction disjunction);

    void enter(Negation negation);

    void enter(Require require);

    void enter(Nothing nothing);

    void exit(System system);

    void exit(Component component);

    void exit(Tag tag);

    void exit(Conjunction conjunction);

    void exit(Disjunction disjunction);

    void exit(Negation negation);

    void exit(Require require);

    void exit(Nothing nothing);
    
}
