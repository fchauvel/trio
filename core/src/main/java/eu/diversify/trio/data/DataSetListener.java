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

package eu.diversify.trio.data;

/**
 * React to the traversal of a DataSet
 */
public interface DataSetListener {
    
    void enterDataSet(DataSet dataSet);
    
    void exitDataSet(DataSet dataSet);
    
    void enterTrace(Trace trace);
    
    void exitTrace(Trace trace);
    
    void enterState(State state);
    
    void exitState(State state);
    
}
