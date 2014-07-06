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

package eu.diversify.trio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.cloudml.core.*;

/**
 * Failure propagation at the type level
 */
public class TypeLevel extends AbstractPopulation {

    private static final String IS_KILLED = "Killed";
    private static final String NO = "false";
    private static final String YES = "true";


    private final Deployment deployment;

    public TypeLevel(Deployment deployment) {
        this.deployment = deployment;
    }

    @Override
    public void reviveAll() {
        for (Component each: deployment.getComponents()) {
            revive(each);
        }
    }

    private void revive(final Component component) {
        if (component.hasProperty(IS_KILLED)) {
            component.getProperties().get(IS_KILLED).setValue(NO);
        } else {
            component.getProperties().add(new Property(IS_KILLED, NO));
        }
    }
    
    @Override
    public void kill(String victim) {
        int killed = 0;
        final Component component = deployment.getComponents().firstNamed(victim);
        if (component != null) {
            markAsDead(component);
            killed = 1;
        }

        while (killed != 0) {
            killed = 0;
            for (Component c: deployment.getComponents()) {
                if (isAlive(c) && !canStillBeStarted(c)) {
                    killed++;
                    markAsDead(c);
                }
            }
        }
    }

    @Override
    public List<String> getSurvivorNames() {
        return collectNames(selectAliveComponents());
    }

    @Override
    public List<String> getIndividualNames() {
        return collectNames(deployment.getComponents());
    }

    private List<String> collectNames(Collection<? extends Component> components) {
        final List<String> result = new ArrayList<String>();
        for (Component each: components) {
            result.add(each.getName());
        }
        return result;
    }

    private boolean canStillBeStarted(Component c) {
        return canBeProvisioned(c) && allMandatoryDependenciesCanBeResolved(c);
    }

    private boolean canBeProvisioned(Component c) {
        if (isDead(c)) {
            return false;
        }
        if (c.isExternal()) {
            return true;
        }

        for (Component other: deployment.getComponents()) {
            if (isAlive(other) && other.canHost(c.asInternal())) {
                return true;
            }
        }
        return false;
    }

    private boolean allMandatoryDependenciesCanBeResolved(Component c) {
        if (c.isExternal()) {
            return true;
        }
        InternalComponent internal = c.asInternal();
        boolean all = true;
        for (RequiredPort eachDependency: internal.getRequiredPorts()) {
            all &= canBeResolved(eachDependency);
            if (!all) {
                return all;
            }
        }
        return all;
    }

    private boolean canBeResolved(RequiredPort port) {
        for (Relationship eachRelationship: deployment.getRelationships()) {
            if (eachRelationship.getRequiredEnd() == port) {
                if (isAlive(eachRelationship.getServerComponent())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isAlive(Component c) {
        return !isDead(c);
    }

    private boolean isDead(Component c) {
        return c.hasProperty(IS_KILLED, YES);
    }
    
    private void markAsDead(Component c) {
        c.getProperties().get(IS_KILLED).setValue(YES);
    }

    private List<Component> selectAliveComponents() {
        final List<Component> selection = new ArrayList<Component>();
        for (Component each: deployment.getComponents()) {
            if (isAlive(each)) {
                selection.add(each);
            }
        }
        return selection;
    }

}
