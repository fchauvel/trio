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


package net.fchauvel.trio.core;

import net.fchauvel.trio.core.requirements.Conjunction;
import net.fchauvel.trio.core.requirements.Disjunction;
import net.fchauvel.trio.core.requirements.Negation;
import net.fchauvel.trio.core.requirements.Nothing;
import net.fchauvel.trio.core.requirements.Require;

/**
 * Ability to traverse a system
 */
public interface AssemblyVisitor {

    void enter(Assembly system);

    void enter(Component component);

    void enter(Tag tag);

    void enter(Conjunction conjunction);

    void enter(Disjunction disjunction);

    void enter(Negation negation);

    void enter(Require require);

    void enter(Nothing nothing);

    void exit(Assembly system);

    void exit(Component component);

    void exit(Tag tag);

    void exit(Conjunction conjunction);

    void exit(Disjunction disjunction);

    void exit(Negation negation);

    void exit(Require require);

    void exit(Nothing nothing);
    
}
