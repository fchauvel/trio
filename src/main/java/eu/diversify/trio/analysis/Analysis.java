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

import eu.diversify.trio.data.DataSetListener;
import eu.diversify.trio.data.Dispatcher;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Specialised dispatcher for metrics
 */
public class Analysis extends Dispatcher {
    
    public Analysis(Metric... listeners) {
        super(listeners);
    }
    
    public Collection<Metric> metrics() {
        final List<DataSetListener> listeners = getListeners();
        final List<Metric> result = new ArrayList<Metric>(listeners.size());
        for (DataSetListener eachListener: listeners) {
            result.add((Metric) eachListener);            
        }
        return result;
    }
    
}
