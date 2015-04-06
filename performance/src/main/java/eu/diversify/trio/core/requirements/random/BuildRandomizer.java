package eu.diversify.trio.core.requirements.random;

import eu.diversify.trio.core.requirements.Requirement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Randomize the construction of a given build
 */
public class BuildRandomizer {

    private final Builder builder;
    private final Random random;
    private final int componentCount;
    private final List<Integer> components;

    public BuildRandomizer(Builder builder, Random random, int componentCount) {
        if (builder == null) {
            throw new IllegalArgumentException("Invalid builder ('null' found)");
        }
        this.builder = builder;
        this.random = random;
        this.componentCount = componentCount;
        this.components = new ArrayList<>(componentCount);
        for (int index = 0; index < componentCount; index++) {
            components.add(index);
        }
    }

    public Requirement build() {
        while (!builder.isResultReady()) {
            List<Command> candidates = builder.getAllowedCommands();
            Command command = chooseAny(candidates);
            execute(command);
        }
        return builder.getResult();
    }

    protected void execute(Command command) {
        switch (command) {
            case OPEN_CONJUNCTION:
                builder.openConjunction();
                break;
            case OPEN_DISJUNCTION:
                builder.openDisjunction();
                break;
            case CLOSE:
                builder.closeOperator();
                break;
            case ADD_NEGATION:
                builder.addNot();
                break;
            case ADD_REQUIRE:
                int index = pickAny(components);
                builder.addRequire(index);
                break;
        }
    }

    private <T> T chooseAny(List<T> candidates) {
        assert !candidates.isEmpty() : "Cannot choose from an empty collection";

        if (candidates.size() == 1) {
            return candidates.get(0);
        }
        return candidates.get(random.nextInt(candidates.size()));
    }

    private <T> T pickAny(List<T> candidates) {
        assert !candidates.isEmpty() : "Cannot pick from an empty collection";

        if (candidates.size() == 1) {
            T result = candidates.get(0);
            candidates.remove(0);
            return result;
        }
        
        final int index = random.nextInt(candidates.size());
        T result = candidates.get(index);
        candidates.remove(index);
        return result;
    }

}
