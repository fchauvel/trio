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
package eu.diversify.trio.unit.simulation.data;

import eu.diversify.trio.simulation.data.DataSet;
import eu.diversify.trio.simulation.data.DataSetListener;
import eu.diversify.trio.simulation.data.Dispatcher;
import eu.diversify.trio.simulation.data.State;
import eu.diversify.trio.simulation.data.Trace;
import eu.diversify.trio.simulation.actions.AbstractAction;
import junit.framework.TestCase;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.jmock.Expectations.any;

/**
 * Check the behaviour of data set listeners
 */
@RunWith(JUnit4.class)
public class DataSetListenerTest extends TestCase {

    private final Mockery context = new JUnit4Mockery();

    @Test
    public void listenerShouldBeProperlyNotified() {
        final DataSetListener listener = context.mock(DataSetListener.class);

        final Trace trace = new Trace(10);
        trace.record(AbstractAction.inactivate("A"), 5);
        trace.record(AbstractAction.inactivate("B"), 0);

        final DataSet data = new DataSet();
        data.include(trace);
        
        final Sequence traversal = context.sequence("traversal");
        context.checking(new Expectations(){{
            oneOf(listener).enterDataSet(data); inSequence(traversal);
            oneOf(listener).enterTrace(trace); inSequence(traversal);
            oneOf(listener).enterState(with(any(State.class))); inSequence(traversal);
            oneOf(listener).exitState(with(any(State.class))); inSequence(traversal);
            oneOf(listener).enterState(with(any(State.class))); inSequence(traversal);
            oneOf(listener).exitState(with(any(State.class))); inSequence(traversal);
            oneOf(listener).enterState(with(any(State.class))); inSequence(traversal);
            oneOf(listener).exitState(with(any(State.class))); inSequence(traversal);            
            oneOf(listener).exitTrace(trace); inSequence(traversal);
            oneOf(listener).exitDataSet(data); inSequence(traversal);
            
        }});
        
        data.accept(listener);
        
        context.assertIsSatisfied();
    }
    
    
    @Test
    public void dispatcherShouldDispatch() {
        final DataSetListener listener = context.mock(DataSetListener.class);
        final DataSetListener dispatcher = new Dispatcher(listener);
        
        final Trace trace = new Trace(10);
        trace.record(AbstractAction.inactivate("A"), 5);
        trace.record(AbstractAction.inactivate("B"), 0);

        final DataSet data = new DataSet();
        data.include(trace);
        
        final Sequence traversal = context.sequence("traversal");
        context.checking(new Expectations(){{
            oneOf(listener).enterDataSet(data); inSequence(traversal);
            oneOf(listener).enterTrace(trace); inSequence(traversal);
            oneOf(listener).enterState(with(any(State.class))); inSequence(traversal);
            oneOf(listener).exitState(with(any(State.class))); inSequence(traversal);
            oneOf(listener).enterState(with(any(State.class))); inSequence(traversal);
            oneOf(listener).exitState(with(any(State.class))); inSequence(traversal);
            oneOf(listener).enterState(with(any(State.class))); inSequence(traversal);
            oneOf(listener).exitState(with(any(State.class))); inSequence(traversal);            
            oneOf(listener).exitTrace(trace); inSequence(traversal);
            oneOf(listener).exitDataSet(data); inSequence(traversal);
            
        }});
        
        data.accept(dispatcher);
        
        context.assertIsSatisfied();
    }
    
    

}
