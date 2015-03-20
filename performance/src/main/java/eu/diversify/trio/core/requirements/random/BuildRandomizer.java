package eu.diversify.trio.core.requirements.random;

import eu.diversify.trio.core.requirements.Requirement;
import java.util.List;
import java.util.Random;

/**
 * Randomize the construction of a given build
 */
public class BuildRandomizer {

    private final Builder builder;
    private final Random random;
    private final int componentCount;

    public BuildRandomizer(Builder builder, Random random, int componentCount) {
        if (builder == null) {
            throw new IllegalArgumentException("Invalid builder ('null' found)");
        }
        this.builder = builder;
        this.random = random;
        this.componentCount = componentCount;
    }

    public Requirement build() {
        while (!builder.isResultReady()) {
            List<Command> candidates = builder.getAllowedCommands();
            Command command = pickFrom(candidates);
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
                int index = random.nextInt(componentCount);
                builder.addRequire(index);
                break;
        }
    }

    private Command pickFrom(List<Command> candidates) {
        assert !candidates.isEmpty() : "Cannot pick from an empty collection";
        
        if (candidates.size() == 1) {
            return candidates.get(0);
        }
        return candidates.get(random.nextInt(candidates.size()));
    }

}
