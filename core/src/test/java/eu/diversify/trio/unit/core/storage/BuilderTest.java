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

package eu.diversify.trio.unit.core.storage;

import net.fchauvel.trio.core.Tag;
import net.fchauvel.trio.core.requirements.Requirement;
import net.fchauvel.trio.core.Component;
import net.fchauvel.trio.core.Assembly;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static net.fchauvel.trio.core.storage.parsing.Builder.*;
import static net.fchauvel.trio.core.requirements.Factory.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 */
@RunWith(JUnit4.class)
public class BuilderTest extends TestCase {

    @Test
    public void shouldReadConjunction() {
        final String text = "requires foo and bar";

        final Requirement requirement = build().requirementFrom(text);

        assertThat(requirement, is(equalTo(require("foo").and(require("bar")))));
    }

    @Test
    public void shouldReadDisjunction() {
        final String text = "requires foo or bar";

        final Requirement requirement = build().requirementFrom(text);

        assertThat(requirement, is(equalTo(require("foo").or(require("bar")))));
    }

    @Test
    public void shouldReadRequirementWithBrackets() {
        final String text = "requires foo or ( bar and qux )";

        final Requirement requirement = build().requirementFrom(text);

        assertThat(requirement, is(equalTo(require("foo").or(require("bar").and(require("qux"))))));
    }

    @Test
    public void shouldBuildComponentWithoutRequirements() {
        final String text = "AA";

        final Component component = build().componentFrom(text);

        assertThat(component, is(equalTo(new Component("AA"))));
    }
    
    @Test
    public void shouldBuildCOmponentWithMTTF() {
        final String text = "AA [0.34]";
        final Component component = build().componentFrom(text);

        assertThat(component, is(equalTo(new Component("AA", 0.34))));
    }

    @Test
    public void shouldReadComponentWithRequirements() {
        final String text = "AA requires BB and (CC or (DD and EE))";

        final Component component = build().componentFrom(text);

        final Component expected
                = new Component("AA",
                                require("BB")
                                .and(
                                        require("CC")
                                        .or(
                                                require("DD")
                                                .and(require("EE")))));
        
        assertThat(component, is(equalTo(expected)));
    }

    @Test
    public void shouldReadASystemWithoutTags() {
        final String text
                = "components:"
                + "      AA requires BB"
                + "      BB ";

        final Assembly expected = new Assembly(
                new Component("AA", require("BB")),
                new Component("BB")
        );

        final Assembly system = build().systemFrom(text);

        assertThat(system, is(equalTo(expected)));
    }
    
    @Test
    public void shouldReadASystemWithTags() {
        final String text
                = "components:"
                + "      AA requires BB"
                + "      BB "
                + "tags:"
                + " - 't1' on AA, BB";

        final List<Component> components = new ArrayList<Component>();
        components.add(new Component("AA", require("BB")));
        components.add(new Component("BB"));
        
        final List<Tag> tags = new ArrayList<Tag>();
        tags.add(new Tag("t1", "AA", "BB"));
        
        final Assembly expected = new Assembly(components, tags);

        final Assembly system = build().systemFrom(text);

        assertThat(system, is(equalTo(expected)));
    }
    
    @Test
    public void shouldReadASystemWithTagsAndName() {
        final String text
                = "system: 'A Sample System'"
                + "components:"
                + "      AA requires BB"
                + "      BB "
                + "tags:"
                + " - 't1' on AA, BB";

        final List<Component> components = new ArrayList<Component>();
        components.add(new Component("AA", require("BB")));
        components.add(new Component("BB"));
        
        final List<Tag> tags = new ArrayList<Tag>();
        tags.add(new Tag("t1", "AA", "BB"));
        
        final Assembly expected = new Assembly("A Sample System", components, tags);

        final Assembly system = build().systemFrom(text);

        assertThat(system, is(equalTo(expected)));
    }

}
