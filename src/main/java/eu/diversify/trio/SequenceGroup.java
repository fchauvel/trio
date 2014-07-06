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

package eu.diversify.trio;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static eu.diversify.trio.Action.kill;

/**
 * A collection of extinction sequence on which some statistics are available
 */
public class SequenceGroup implements Iterable<Extinction> {

    //private final Population population;
    private final int headcount;
    private final List<Extinction> sequences;

    public SequenceGroup(int headcount) {
        this.headcount = headcount;
        this.sequences = new ArrayList<Extinction>();
    }

    public Iterator<Extinction> iterator() {
        return sequences.iterator();
    }

    public void add(Extinction extinction) {
        this.sequences.add(extinction);
    }

    public int size() {
        return sequences.size();
    }

    public Distribution robustness() {
        final List<Double> data = new ArrayList<Double>(sequences.size());
        for (Extinction eachSequence: sequences) {
            data.add(eachSequence.robustness());
        }
        return new Distribution(data);
    }

    public Distribution length() {
        final List<Double> data = new ArrayList<Double>(sequences.size());
        for (Extinction eachSequence: sequences) {
            data.add((double) eachSequence.length());
        }
        return new Distribution(data);
    }

    public Map<String, Distribution> ranking() {
        final Map<String, List<Double>> result = new HashMap<String, List<Double>>();
        for (Extinction each: sequences) {
            aggregate(result, each.impacts());
        }
        return toMapOfDistribution(result);
    }

    private void aggregate(Map<String, List<Double>> accumulator, Map<Action, Integer> impacts) {
        for (Action eachAction: impacts.keySet()) {
            List<Double> values = accumulator.get(eachAction.toString());
            final double impact = (double) impacts.get(eachAction);
            if (values == null) {
                values = new ArrayList<Double>();
                values.add(impact);
                accumulator.put(eachAction.toString(), values);
            } else {
                values.add(impact);
            }
        }
    }

    public Map<String, Distribution> toMapOfDistribution(Map<String, List<Double>> data) {
        final Map<String, Distribution> results = new HashMap<String, Distribution>();
        for (String eachKey: data.keySet()) {
            results.put(eachKey, new Distribution(data.get(eachKey)));
        }
        return results;
    }

    public Distribution sensibilityOf(String eachIndividual) {
        final List<Double> data = new ArrayList<Double>();
        for (Extinction eachSequence: sequences) {
            final int sensibility = eachSequence.impactOf(kill(eachIndividual));
            if (sensibility > 0) {
                data.add((double) sensibility);
            }
        }
        return new Distribution(data);
    }

    public String summary() {
        String output = "";
        output += String.format("%d sequence(s) run.\n", sequences.size());
        output += String.format("Summary: \n");
        output += String.format(" - Robustness: %.2f %%\n", robustness().mean());
        output += String.format(" - Sequence length: %s\n", length());
        output += String.format(" - Sensitive components:\n");

        final List<Map.Entry<String, Distribution>> ranking = new ArrayList<Map.Entry<String, Distribution>>(ranking().entrySet());
        Collections.sort(ranking, new Comparator<Map.Entry<String, Distribution>>() {

            @Override
            public int compare(Map.Entry<String, Distribution> o1, Map.Entry<String, Distribution> o2) {
                final double delta = o2.getValue().mean() - o1.getValue().mean();
                if (delta == 0D) {
                    return 0;
                        
                } else if (delta < 0) {
                    return -1;
                                
                } else {
                    return 1;
                }
            }

        });
        for (Map.Entry<String, Distribution> each: ranking) {
            output += String.format("    %-20s %s\n", each.getKey(), each.getValue());
        }
        return output;
    }

    private static final String END_OF_LINE = System.lineSeparator();
    private static final String SEPARATOR = ",";

    public String toCsv() {
        final StringBuilder buffer = new StringBuilder();
        formatCsvHeader(buffer);
        formatCsvContent(buffer);
        return buffer.toString();
    }

    private void formatCsvContent(final StringBuilder buffer) {
        int count = 1;
        for (Extinction eachSequence: sequences) {
            for (State eachState: eachSequence) {
                buffer.append(count).append(SEPARATOR)
                        .append(eachState.killedCount()).append(SEPARATOR)
                        .append(eachState.survivorCount()).append(SEPARATOR)
                        .append(eachState.getTrigger()).append(SEPARATOR)
                        .append(eachState.loss()).append(END_OF_LINE);
            }
            count += 1;
        }
    }

    private void formatCsvHeader(final StringBuilder buffer) {
        buffer.append("sequence").append(SEPARATOR)
                .append("killed count").append(SEPARATOR)
                .append("survivor count").append(SEPARATOR)
                .append("action").append(SEPARATOR)
                .append("loss").append(END_OF_LINE);
    }

    public void toCsvFile(String csvFileName) throws IOException {
        FileWriter file = new FileWriter(csvFileName);
        file.write(toCsv());
        file.close();
    }

}
