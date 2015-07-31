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

package eu.diversify.trio.analytics.sensitivity;

/**
 * Captures a single entry in the ranking (the component and
 * its sensitivity)
 */
public class Sensitivity implements Comparable<Sensitivity> {
 
    private final String component;
    private int totalImpact;
    private int failureCount;
 
    public Sensitivity(String component) {
        this.component = component;
        this.totalImpact = 0;
        this.failureCount = 0;
    }
    
    
    public String component() {
        return component;
    }
 
    public double averageImpact() {
        return (double) totalImpact / failureCount;
    }

    public void recordFailure(int impact) {
        assert impact >= 0 : "A failure impact shall be positive or null (found " + impact + ")";
        this.totalImpact += impact;
        this.failureCount += 1;
    }

    @Override
    public String toString() {
        return String.format("%s : %5.2e [%d / %d]", component, averageImpact(), totalImpact, failureCount);
    }

    public int compareTo(Sensitivity o) {
        return -1 * Double.compare(averageImpact(), o.averageImpact());
    }
    
}
