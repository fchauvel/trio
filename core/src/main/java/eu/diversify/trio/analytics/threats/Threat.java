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

package eu.diversify.trio.analytics.threats;

/**
 * The threat associated with a given failure sequence
 */
public class Threat implements Comparable<Threat> {
    private final String failureSequence;
    private final double robustness;
    private int occurrence;
    private int total;

    public Threat(String description, double robustness) {
        this.failureSequence = description;
        this.robustness = robustness;
    }

    void newInstance() {
        occurrence++;
    }

    void setTotal(int total) {
        assert total >= occurrence : "Total must be above " + occurrence + " (found " + total + ")";
        this.total = total;
    }
    
    public String failureSequence() {
        return failureSequence;
    }

    public double probability() {
        return (double) occurrence / total;
    }

    public double threatLevel() {
        return probability() * (1D - robustness);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.failureSequence != null ? this.failureSequence.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Threat other = (Threat) obj;
        return !((this.failureSequence == null) ? other.failureSequence != null : !this.failureSequence.equals(other.failureSequence));
    }
    
    

    public int compareTo(Threat o) {
        return -1 * Double.compare(threatLevel(), o.threatLevel());
    }
    
}
