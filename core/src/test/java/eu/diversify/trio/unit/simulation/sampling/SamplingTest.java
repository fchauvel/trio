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
package eu.diversify.trio.unit.simulation.sampling;

import eu.diversify.trio.core.Assembly;
import eu.diversify.trio.core.Component;
import eu.diversify.trio.simulation.AssemblyState;
import eu.diversify.trio.simulation.Topology;
import eu.diversify.trio.simulation.sampling.ProportionalSampling;
import eu.diversify.trio.simulation.sampling.Sampler;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

/**
 * Check the behaviour of sampling strategies
 */
public class SamplingTest {

    private final Recorder recorder;

    private Sampler sampler;

    public SamplingTest() {
        this.recorder = new Recorder();
    }
    
    private void sample(int sampleCount) {
        for (int index = 0; index < sampleCount; index++) {
            String selected = sampler.pick();
            recorder.record(selected);
        }
    }

    @Test
    public void proportionalSamplingShouldBeBalancedWhenWeightAreEqual() {
        sampler = new ProportionalSampling(makeAssembly(1D, 1D, 1D));

        sample(10000);

        assertThat("proportion of A", recorder.proportionOf("A"), is(closeTo(1D / 3, 0.02)));
        assertThat("proportion of B", recorder.proportionOf("B"), is(closeTo(1D / 3, 0.02)));
        assertThat("proportion of C", recorder.proportionOf("C"), is(closeTo(1D / 3, 0.02)));
    }
    
    @Test
    public void proportionalSamplingShouldBeInverslyProportionalToMTTF() {
        sampler = new ProportionalSampling(makeAssembly(1D, 1D, 2D));

        sample(10000);

        assertThat("proportion of A", recorder.proportionOf("A"), is(closeTo(0.4, 0.02)));
        assertThat("proportion of B", recorder.proportionOf("B"), is(closeTo(0.4, 0.02)));
        assertThat("proportion of C", recorder.proportionOf("C"), is(closeTo(0.2, 0.02)));
    }

    private static Topology makeAssembly(double... mttf) {
        assert mttf.length == 3 : "Wrong number of mttf";

        return new AssemblyState(new Assembly(new Component("A", mttf[0]), new Component("B", mttf[1]), new Component("C", mttf[2])));
    }

    private static class Recorder {

        private int total;
        private final Map<String, Integer> counts;

        public Recorder() {
            this.total = 0;
            this.counts = new HashMap<String, Integer>();
        }

        public void record(String selected) {
            Integer count = counts.get(selected);
            if (count == null) {
                count = 0;
            }
            counts.put(selected, count + 1);
            total += 1;
        }

        public double proportionOf(String selected) {
            Integer count = counts.get(selected);
            if (count == null) {
                return 0D;
            }
            return count / (double) total;
        }
    }

}
