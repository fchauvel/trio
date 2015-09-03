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
/**
 * This file is part of TRIO :: Core.
 *
 * TRIO :: Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Core.  If not, see <http://www.gnu.org/licenses/>.
 */
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
/**
 * ====
 *     This file is part of TRIO :: Core.
 *
 *     TRIO :: Core is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     TRIO :: Core is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with TRIO :: Core.  If not, see <http://www.gnu.org/licenses/>.
 * ====
 *
 * ====
 *     This file is part of TRIO.
 *
 *     TRIO is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     TRIO is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with TRIO.  If not, see <http://www.gnu.org/licenses/>.
 * ====
 *
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
/**
 * This file is part of TRIO :: Core.
 *
 * TRIO :: Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TRIO :: Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO :: Core.  If not, see <http://www.gnu.org/licenses/>.
 */
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
package eu.diversify.trio.core;

import eu.diversify.trio.core.requirements.Conjunction;
import eu.diversify.trio.core.requirements.Disjunction;
import eu.diversify.trio.core.requirements.Negation;
import eu.diversify.trio.core.requirements.Nothing;
import eu.diversify.trio.core.requirements.Require;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Dispatch visit events (i.e., enter, exit) to other visitors, to factor out
 * the cost of traversing large models.
 */
public class Dispatcher extends DefaultAssemblyVisitor {

    private final List<AssemblyVisitor> listeners;

    public Dispatcher(AssemblyVisitor... listeners) {
        this(Arrays.asList(listeners));
    }

    public Dispatcher(List<AssemblyVisitor> listeners) {
        eu.diversify.trio.util.Require.notNull(listeners, "Invalid 'null' value provided as listeners!");
        eu.diversify.trio.util.Require.notEmpty(listeners, "Invalid empty list [] provided as listeners!");
        this.listeners = new ArrayList<AssemblyVisitor>(listeners);
    }

    @Override
    public final void enter(Nothing nothing) {
        for (AssemblyVisitor each: listeners) {
            each.enter(nothing);
        }
    }

    @Override
    public final void exit(Nothing nothing) {
        for (AssemblyVisitor each: listeners) {
            each.exit(nothing);
        }
    }

    @Override
    public final void enter(Require require) {
        for (AssemblyVisitor each: listeners) {
            each.enter(require);
        }
    }

    @Override
    public final void exit(Require require) {
        for (AssemblyVisitor each: listeners) {
            each.exit(require);
        }
    }

    @Override
    public final void enter(Conjunction conjunction) {
        for (AssemblyVisitor each: listeners) {
            each.enter(conjunction);
        }
    }

    @Override
    public final void exit(Conjunction conjunction) {
        for (AssemblyVisitor each: listeners) {
            each.exit(conjunction);
        }
    }

    @Override
    public final void enter(Disjunction disjunction) {
        for (AssemblyVisitor each: listeners) {
            each.enter(disjunction);
        }
    }

    @Override
    public final void exit(Disjunction disjunction) {
        for (AssemblyVisitor each: listeners) {
            each.exit(disjunction);
        }
    }

    @Override
    public final void enter(Negation negation) {
        for (AssemblyVisitor each: listeners) {
            each.enter(negation);
        }
    }

    @Override
    public final void exit(Negation negation) {
        for (AssemblyVisitor each: listeners) {
            each.exit(negation);
        }
    }

    @Override
    public final void enter(Component component) {
        for (AssemblyVisitor each: listeners) {
            each.enter(component);
        }
    }

    @Override
    public final void exit(Component component) {
        for (AssemblyVisitor each: listeners) {
            each.exit(component);
        }
    }

    @Override
    public final void enter(Tag tag) {
        for (AssemblyVisitor each: listeners) {
            each.enter(tag);
        }
    }

    @Override
    public final void exit(Tag tag) {
        for (AssemblyVisitor each: listeners) {
            each.exit(tag);
        }
    }
    
     @Override
    public final void enter(Assembly system) {
        for (AssemblyVisitor each: listeners) {
            each.enter(system);
        }
    }

    @Override
    public final void exit(Assembly system) {
        for (AssemblyVisitor each: listeners) {
            each.exit(system);
        }
    }

    @Override
    protected final void defaultVisit() {
        throw new RuntimeException("Should never be called!");
    }
    
    
}
