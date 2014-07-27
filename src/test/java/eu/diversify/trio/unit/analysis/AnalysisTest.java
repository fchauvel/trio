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
package eu.diversify.trio.unit.analysis;

import eu.diversify.trio.analysis.Analysis;
import eu.diversify.trio.analysis.Metric;
import eu.diversify.trio.analysis.Robustness;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 *
 */
@RunWith(JUnit4.class)
public class AnalysisTest extends TestCase {

    @Test
    public void metricsShouldReturnAllTheMetrics() {
        final Metric metric = new Robustness();
        final Analysis sut = new Analysis(metric);

        assertThat(sut.metrics(), contains(metric));
    }

    @Test
    public void metricShouldReturnTheFirstMetricWithTheGivenName() {
        final Metric metric = new Robustness();
        final Analysis sut = new Analysis(metric);
        
        final Metric selected = sut.metric(Robustness.NAME);
        
        assertThat(selected, is(not(nullValue())));
        assertThat(selected, is(sameInstance(metric)));
    }

    @Test
    public void metricShouldReturnNullIfTheGivemMetricDoesNotExist() {
        final Metric metric = new Robustness();
        final Analysis sut = new Analysis(metric);

        assertThat(sut.metric("foo"), is(nullValue()));
    }

}
