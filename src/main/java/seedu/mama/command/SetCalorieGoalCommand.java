package seedu.mama.command;

import seedu.mama.model.CalorieGoalEntry;
import seedu.mama.model.EntryList;
import seedu.mama.storage.Storage;

public class SetCalorieGoalCommand implements Command {
    private final int calorieGoal;

    public SetCalorieGoalCommand(int calGoal) {
        this.calorieGoal = calGoal;
    }

    @Override
    public CommandResult execute(EntryList list, Storage storage) throws CommandException {
        CalorieGoalEntry entry = new CalorieGoalEntry(calorieGoal);
        list.add(entry);
        if (storage == null) {
            throw new CommandException("Storage not initialized properly.");
        }

        if (calorieGoal < 0) {
            return new CommandResult("Calorie goal cannot be less than 0!");
        } else if (calorieGoal > 10000) {
            return new CommandResult("Calorie goal too high");
        }

        storage.saveGoal(calorieGoal);
        return new CommandResult("Calorie goal set to " + calorieGoal + " kcal.");
    }
}
