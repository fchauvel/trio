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
package eu.diversify.trio.core.requirements;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class CachedFactory extends RequirementFactory {

//    private static class Cache<T> {
//
//        private final int capacity;
//        private final Map<Integer, SoftReference<T>> cache;
//
//        public Cache() {
//            this(10);
//        }
//
//        public Cache(final int capacity) {
//            this.capacity = capacity;
//            this.cache = new LinkedHashMap<Integer, SoftReference<T>>(capacity) {
//
//                private static final long serialVersionUID = 1L;
//
//                @Override
//                protected boolean removeEldestEntry(Map.Entry<Integer, SoftReference<T>> eldest) {
//                    return size() >= capacity;
//                }
//
//            };
//        }
//
//        private long access;
//        private long failure;
//
//        public T get(int key) {
//            access++;
//            final SoftReference<T> entry = cache.get(key);
//            if (entry == null) {
//                failure++;
//                return null;
//            }
//            //showHitRatio();
//            return entry.get();
//        }
//
//        public void put(int key, T object) {
//
//            cache.put(key, new SoftReference<T>(object));
//        }
//
//        private void showHitRatio() {
//            if (access % 1000 == 0) {
//                double hits = access - failure;
//                double ratio = 100 * hits / access;
//                System.out.printf("HR: %.3f %% \n", ratio);
//                access = 0;
//                failure = 0;
//            }
//
//        }
//
//    }
    private final Map<Integer, Requirement> terminals;
    //private final Cache<Requirement> subTrees;

    public CachedFactory() {
        this.terminals = new HashMap<Integer, Requirement>();
        //this.subTrees = new Cache<Requirement>();
    }

    public int terminalCount() {
        return terminals.size();
    }

    public Requirement nothing() {
        return Nothing.getInstance();
    }

    public Requirement createNegation(Requirement requirement) {
//        return cachedNot(requirement);
        return new Negation(requirement);
    }

//    private Requirement cachedNot(Requirement requirement) {
//        final int hash = Objects.hash(requirement);
//        Requirement result = subTrees.get(hash);
//        if (result == null) {
//            result = new Negation(requirement);
//            subTrees.put(hash, result);
//        }
//        return result;
//    }
    public Requirement createDisjunction(Requirement left, Requirement right) {
        //      return cachedOr(left, right);
        return new Disjunction(left, right);
    }

//    private Requirement cachedOr(Requirement left, Requirement right) {
//        final int hash = Objects.hash(BinaryOperator.Operator.AND, left, right);
//        Requirement result = subTrees.get(hash);
//        if (result == null) {
//            result = new Disjunction(left, right);
//            subTrees.put(hash, result);
//        }
//        return result;
//    }
    public Requirement createConjunction(Requirement left, Requirement right) {
//       return cachedAnd(left, right);
        return new Conjunction(left, right);
    }

//    private Requirement cachedAnd(Requirement left, Requirement right) {
//        final int hash = Objects.hash(BinaryOperator.Operator.AND, left, right);
//        Requirement result = subTrees.get(hash);
//        if (result == null) {
//            result = new Conjunction(left, right);
//            subTrees.put(hash, result);
//        }
//        return result;
//    }
    public Requirement createRequire(String componentName) {
        final int hash = componentName.hashCode();
        Requirement result = terminals.get(hash);
        if (result == null) {
            result = new Require(componentName);
            terminals.put(hash, result);
        }
        return result;
    }

    public Requirement createRequire(int hash) {
        Requirement result = terminals.get(hash);
        if (result == null) {
            result = new Require("C" + hash);
            terminals.put(hash, result);
        }
        return result;
    }

}
