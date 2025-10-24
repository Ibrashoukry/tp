package seedu.mama.command;

import seedu.mama.model.EntryList;
import seedu.mama.model.WorkoutGoalEntry;
import seedu.mama.storage.Storage;

public class SetWorkoutGoalCommand implements Command {
    private final int minutesPerWeek;

    public SetWorkoutGoalCommand(int minutesPerWeek) {
        this.minutesPerWeek = minutesPerWeek;
    }

    public static SetWorkoutGoalCommand fromInput(String input) throws CommandException {
        // Expected: workout goal <minutes>
        String[] parts = input.split("\\s+");
        if (parts.length != 3) {
            throw new CommandException("Usage: workout goal <minutes>");
        }
        try {
            int minutes = Integer.parseInt(parts[2]);
            if (minutes <= 0) {
                throw new CommandException("Workout goal must be a positive number of minutes.");
            }
            return new SetWorkoutGoalCommand(minutes);
        } catch (NumberFormatException e) {
            throw new CommandException("Workout goal must be specified as whole number minutes.");
        }
    }

    @Override
    public CommandResult execute(EntryList list, Storage storage) throws CommandException {
        if (storage == null) {
            throw new CommandException("Storage not initialized properly.");
        }

        list.add(new WorkoutGoalEntry(minutesPerWeek));
        storage.save(list);

        return new CommandResult("Weekly workout goal set to " + minutesPerWeek + " minutes.");
    }
}
