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
package eu.diversify.trio.generator;

import eu.diversify.trio.core.requirements.Requirement;
import eu.diversify.trio.core.requirements.Conjunction;
import eu.diversify.trio.core.requirements.Disjunction;
import eu.diversify.trio.core.requirements.Negation;
import eu.diversify.trio.core.requirements.Require;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class Goal {

    private final int numberOfVariables;
    private final int desiredSize;
    private final Random random;

    private final List<Build> builders;

    public Goal(int desiredSize, int numberOfVariables) {
        this.random = new Random();
        this.desiredSize = desiredSize;
        this.numberOfVariables = numberOfVariables;
        this.builders = new ArrayList<Build>();
        this.builders.add(new BuildRequire());
        this.builders.add(new BuildConjunction());
        //this.builders.add(new BuildDisjunction());
        //this.builders.add(new BuildNegation());
    }

    public Requirement build() {
        return buildRequirement(new State()).expression;
    }

    public Result buildRequirement(State current) {
        Build action = pickAny(current.selectRelevant(builders));
        return action.build(current, this);
    }

    private Build pickAny(List<Build> candidates) {
        if (candidates.isEmpty()) {
            throw new IllegalArgumentException("Cannot pick randomly from an empty collection!");
        }
        if (candidates.size() == 1) {
            return candidates.get(0);
        }
        final int draw = random.nextInt(candidates.size());
        return candidates.get(draw);
    }

    private Result buildConjunction(State current) {
        final Result left = buildRequirement(current.addBinaryNode());
        final Result right = buildRequirement(left.state);
        return new Result(new Conjunction(left.expression, right.expression), right.state);
    }

    private Result buildDisjunction(State current) {
        final Result left = buildRequirement(current.addBinaryNode());
        final Result right = buildRequirement(left.state);
        return new Result(new Disjunction(left.expression, right.expression), right.state);
    }

    private Result buildNegation(State current) {
        final Result operand = buildRequirement(current.addUnaryNode());
        return new Result(new Negation(operand.expression), operand.state);
    }

    private Result buildRequire(State current) {
        return new Result(new Require(pickAVariable()), current.addLeaf());
    }

    private String pickAVariable() {
        return "C" + random.nextInt(numberOfVariables);
    }

    public class State {

        private final int localSize;
        private final int minimumGlobalSize;

        public State() {
            this(0, 1);
        }

        public State(int localSize, int minimumGlobalSize) {
            this.localSize = localSize;
            this.minimumGlobalSize = minimumGlobalSize;
        }

        public boolean acceptNewLeaf() {
            return !(wouldCloseTheTree() && !isBigEnough());
        }

        private boolean wouldCloseTheTree() {
            return minimumGlobalSize - localSize == 1;
        }

        public boolean acceptNewNode() {
            return !isBigEnough();
        }

        private boolean isBigEnough() {
            return desiredSize == minimumGlobalSize;
        }

        public State addLeaf() {
            return new State(localSize + 1, minimumGlobalSize);
        }

        public State addBinaryNode() {
            return new State(localSize, minimumGlobalSize + 1);
        }
        
         public State addUnaryNode() {
            return new State(localSize, minimumGlobalSize);
        }

        public List<Build> selectRelevant(List<Build> candidates) {
            final List<Build> selection = new ArrayList<Build>(candidates.size());
            for (Build each: candidates) {
                if (each.isLegalIn(this)) {
                    selection.add(each);
                }
            }
            return selection;
        }

        public String toString() {
            return "[S=" + localSize + " ; M=" + minimumGlobalSize + "]";
        }

    }

    private static class Result {

        private final State state;
        private final Requirement expression;

        public Result(Requirement expression, State state) {
            this.state = state;
            this.expression = expression;
        }

    };

    private interface Build {

        Result build(State state, Goal goal);

        boolean isLegalIn(State current);

    }

    public static class BuildRequire implements Build {

        @Override
        public Result build(State state, Goal goal) {
            return goal.buildRequire(state);
        }

        @Override
        public boolean isLegalIn(State current) {
            return current.acceptNewLeaf();
        }

    }

    public static class BuildConjunction implements Build {

        @Override
        public Result build(State state, Goal goal) {
            return goal.buildConjunction(state);
        }

        @Override
        public boolean isLegalIn(State current) {
            return current.acceptNewNode();
        }

    }

    public static class BuildDisjunction implements Build {

        @Override
        public Result build(State state, Goal goal) {
            return goal.buildDisjunction(state);
        }

        @Override
        public boolean isLegalIn(State current) {
            return current.acceptNewNode();
        }

    }

    public static class BuildNegation implements Build {

        @Override
        public Result build(State state, Goal goal) {
            return goal.buildNegation(state);
        }

        @Override
        public boolean isLegalIn(State current) {
            return true;
        }

    }

}
