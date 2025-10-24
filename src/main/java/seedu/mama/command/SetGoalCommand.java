package seedu.mama.command;

import seedu.mama.model.EntryList;
import seedu.mama.storage.Storage;

public class SetGoalCommand implements Command {
    private final int goal;

    public SetGoalCommand(int goal) {
        this.goal = goal;
    }

    @Override
    public CommandResult execute(EntryList list, Storage storage) throws CommandException {
        if (storage == null) {
            throw new CommandException("Storage not initialized properly.");
        }

        storage.saveGoal(goal);
        return new CommandResult("Calorie goal set to " + goal + " kcal.");
    }
}
