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
package eu.diversify.trio.core;

import eu.diversify.trio.util.Require;
import java.util.*;

/**
 * Iterate through the sub parts of a system and notifies the associated
 * listener of the progress.
 */
public class Evaluation {

    public static Evaluation evaluate(SystemVisitor visitor) {
        return new Evaluation(visitor);
    }

    public static Evaluation evaluate(SystemVisitor... visitors) {
        return new Evaluation(new Dispatcher(visitors));
    }

    private final SystemVisitor listener;
    private final Deque<Memento> stack;

    public Evaluation(SystemVisitor listener) {
        this.stack = new LinkedList<Memento>();
        this.listener = listener;
    }

    public void on(SystemPart singleRoot) {
        on(Collections.singleton(singleRoot));
    }

    public void on(Collection<SystemPart> roots) {
        this.stack.clear();
        this.stack.push(new Memento(roots));
        run();
    }

    private void run() {
        while (hasNext()) {
            next();
        }
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    public SystemPart next() {
        Require.isTrue(hasNext(), "There is no next element");

        final SystemPart next = currentPart().next();
        push(next);
        backtrack();
        return next;
    }

    private void push(final SystemPart next) {
        final Memento memento = new Memento(next);
        stack.push(memento);
        if (listener != null) {
            next.begin(listener);
        }
    }

    private void backtrack() {
        while (!stack.isEmpty() && !currentPart().hasNext()) {
            pop();
        }

        assert stack.isEmpty() || currentPart().hasNext(): "Wrong usage of the stack";
    }

    private void pop() {
        final Memento memento = stack.pop();
        if (listener != null) {
            memento.end(listener);
        }
    }

    private Memento currentPart() {
        return stack.peek();
    }

    private static class Memento {

        private final SystemPart parent;
        private final Iterator<SystemPart> children;

        public Memento(Collection<SystemPart> children) {
            this(null, children.iterator());
        }

        public Memento(SystemPart parent) {
            this(parent, parent.subParts().iterator());
        }

        public Memento(SystemPart parent, Iterator<SystemPart> iterator) {
            assert iterator != null: "A 'null' iterator shall not be pushed on the stack!";

            this.parent = parent;
            this.children = iterator;
        }

        public boolean hasNext() {
            return this.children.hasNext();
        }

        public SystemPart next() {
            return this.children.next();
        }

        public void end(SystemVisitor visitor) {
            if (parent != null) {
                parent.end(visitor);
            }
        }

    }

}
