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
/**
 *
 * This file is part of TRIO.
 *
 * TRIO is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * TRIO is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TRIO. If not, see <http://www.gnu.org/licenses/>.
 */
/*
 */
package eu.diversify.trio.analysis;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Distribution {

    private final String name;
    private final Map<String, List<Double>> values;

    public Distribution() {
        this("anaonymous");
    }

    public Distribution(String name) {
        this.name = name;
        this.values = new HashMap<String, List<Double>>();
    }

    public void clear() {
        values.clear();
    }

    public double mean() {
        return mean.of(values());
    }

    public void record(String key, double value) {
        List<Double> list = values.get(key);
        if (list == null) {
            list = new ArrayList<Double>();
            values.put(key, list);
        }
        list.add(value);
    }

    public List<Double> valuesOf(String key) {
        if (!values.containsKey(key)) {
            final String error = String.format("Unknown key '%s'", key);
            throw new IllegalArgumentException(error);
        }
        return values.get(key);
    }

    public List<Double> values() {
        final List<Double> results = new ArrayList<Double>();
        for (List<Double> eachValueList: values.values()) {
            results.addAll(eachValueList);
        }
        return results;
    }

    public double probabilityOf(String key) {
        return ((double) valuesOf(key).size()) / values().size();
    }

    public double expectedValueOf(String key, Function function) {
        return probabilityOf(key) * function.of(valuesOf(key));
    }

    public SortedMap<String, Double> ranking(Function function) {
        final SortedMap<String, Double> results = new TreeMap<String, Double>();
        for (String eachKey: values.keySet()) {
            results.put(eachKey, function.of(valuesOf(eachKey)));
        }
        return results;
    }

    public SortedMap<String, Double> byDecreasingExpectedValue(Function function) {
        final Map<String, Double> base = new HashMap<String, Double>();
        for (String eachKey: values.keySet()) {
            base.put(eachKey, expectedValueOf(eachKey, function));
        }
        final SortedMap<String, Double> results = new TreeMap<String, Double>(new Decreasing(base));
        results.putAll(base);
        return results;
    }
    
     public SortedMap<String, Double> byIncreasingExpectedValue(Function function) {
        final Map<String, Double> base = new HashMap<String, Double>();
        for (String eachKey: values.keySet()) {
            base.put(eachKey, expectedValueOf(eachKey, function));
        }
        final SortedMap<String, Double> results = new TreeMap<String, Double>(new Increasing(base));
        results.putAll(base);
        return results;
    }

    private static class Decreasing implements Comparator<String> {

        Map<String, Double> base;

        public Decreasing(Map<String, Double> base) {
            this.base = base;
        }

        // Note: this comparator imposes orderings that are inconsistent with equals.    
        public int compare(String a, String b) {
            if (base.get(a) >= base.get(b)) {
                return -1;
            } else {
                return 1;
            } // returning 0 would merge keys
        }
    }
    
    private static class Increasing implements Comparator<String> {

        Map<String, Double> base;

        public Increasing(Map<String, Double> base) {
            this.base = base;
        }

        // Note: this comparator imposes orderings that are inconsistent with equals.    
        public int compare(String a, String b) {
            if (base.get(a) <= base.get(b)) {
                return -1;
            } else {
                return 1;
            } // returning 0 would merge keys
        }
    }

    public static interface Function {

        double of(List<Double> values);

    }

    public static Function mean = new Mean();

    private static class Mean implements Function {

        public double of(List<Double> values) {
            double sum = 0;
            for (double eachValue: values) {
                sum += eachValue;
            }
            return sum / values.size();
        }

    }
}
