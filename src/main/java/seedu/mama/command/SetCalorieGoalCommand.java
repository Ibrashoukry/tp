package seedu.mama.command;

import seedu.mama.model.EntryList;
import seedu.mama.storage.Storage;

public class SetCalorieGoalCommand implements Command {
    private final int calorieGoal;

    public SetCalorieGoalCommand(int calGoal) {
        this.calorieGoal = calGoal;
    }

    @Override
    public CommandResult execute(EntryList list, Storage storage) throws CommandException {
        if (storage == null) {
            throw new CommandException("Storage not initialized properly.");
        }

        storage.saveGoal(calorieGoal);
        return new CommandResult("Calorie goal set to " + calorieGoal + " kcal.");
    }
}
