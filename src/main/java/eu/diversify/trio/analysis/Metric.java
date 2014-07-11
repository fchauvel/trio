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

package eu.diversify.trio.analysis;

import eu.diversify.trio.data.AbstractDataSetListener;

/**
 * A metric is an value computed on a data set
 */
public abstract class Metric extends AbstractDataSetListener {
    
    private final String name;
    private final String unit;

    public Metric(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }
    
    public String name() {
        return this.name;
    }
    
    public String unit() {
        return this.unit;
    }
    
    public abstract double value();
    
    public abstract Distribution distribution();
    
}
