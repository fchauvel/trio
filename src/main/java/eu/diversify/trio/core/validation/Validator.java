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

package eu.diversify.trio.core.validation;

import eu.diversify.trio.core.Component;
import eu.diversify.trio.core.DefaultSystemVisitor;
import eu.diversify.trio.core.System;
import eu.diversify.trio.core.Tag;
import eu.diversify.trio.core.requirements.Require;
import java.util.ArrayList;
import java.util.List;


/**
 * Traverse the model, and check that component referred in dependencies and by
 * tags are properly defined in the model.
 */
public class Validator extends DefaultSystemVisitor {

    private final List<Inconsistency> errors;
    private System currentSystem;
    private Component currentComponent;
    
    public Validator() {
        this.errors = new ArrayList<Inconsistency>();
        this.currentComponent = null;
        this.currentSystem = null;
    }

    @Override
    public void exit(Require require) {
        eu.diversify.trio.util.Require.notNull(currentSystem, "should not be null!");
        eu.diversify.trio.util.Require.notNull(currentComponent, "should not be null!");
        
        final String target = require.getRequiredComponent();
        if (!currentSystem.hasComponentNamed(target)) {
            final String message = String.format("Unknown dependencies '%s' for component '%s'", target, currentComponent.getName());
            errors.add(new Inconsistency(message));
        }
    }

    @Override
    public void exit(Tag tag) {
        eu.diversify.trio.util.Require.notNull(currentSystem, "should not be null!");
        
        for(String eachTarget: tag.getTargets()) {
            if (!currentSystem.hasComponentNamed(eachTarget)) {
                final String message = String.format("Unknown component '%s' in tag '%s'", eachTarget, tag.getLabel());
                errors.add(new Inconsistency(message));
            }
        }
    }

    @Override
    public void enter(System system) {
        this.currentSystem = system;
    }

    @Override
    public void enter(Component component) {
        this.currentComponent = component;
    }
    
    public boolean hasError() {
        return !errors.isEmpty();
    }
    
    public void check() throws InvalidSystemException {
        if (hasError()) {
            throw new InvalidSystemException(errors);
        }
    }

}
