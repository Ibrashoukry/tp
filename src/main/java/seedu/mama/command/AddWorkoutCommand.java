package seedu.mama.command;

import seedu.mama.model.EntryList;
import seedu.mama.model.WorkoutEntry;
import seedu.mama.storage.Storage;

/**
 * Command to add a workout entry with a type and duration in minutes.
 */
public final class AddWorkoutCommand implements Command {
    private final String workoutType;
    private final int duration; // minutes

    public AddWorkoutCommand(String workoutType, int duration) {
        this.workoutType = workoutType;
        this.duration = duration;
    }

    /**
     * Parses raw user input into an AddWorkoutCommand.
     * Expected format: workout TYPE /dur DURATION
     *
     * @param input raw input string
     * @return AddWorkoutCommand
     * @throws CommandException if the format is invalid
     */
    public static AddWorkoutCommand fromInput(String input) throws CommandException {
        // Assumes lowercase "workout" and exact format, error handling not added yet
        String after = input.substring("workout".length()).trim();
        if (after.isEmpty()) {
            throw new CommandException("Workout type cannot be empty.\nExpected input format: workout <TYPE> /dur <DURATION>");
        }

        if (after.indexOf("/dur") != after.lastIndexOf("/dur")) {
            throw new CommandException("Too many '/dur' segments.\nUsage: workout TYPE /dur DURATION");
        }

        String[] parts = after.split("/dur", 2);
        if (parts.length < 2) {
            throw new CommandException("Missing '/dur' segment.\nUsage: workout TYPE /dur DURATION");
        }

        String type = parts[0].trim();
        if (type.isEmpty()) {
            throw new CommandException("Workout type cannot be empty.\nUsage: workout TYPE /dur DURATION");
        }

        String rhs = parts[1].trim();
        if (rhs.isEmpty()) {
            throw new CommandException("Missing duration after '/dur'.\nUsage: workout TYPE /dur DURATION");
        }

        // tokens after /dur
        String[] tokens = rhs.split("\\s+");
        String durToken = tokens[0];

        // If there are extra tokens after duration, reject
        if (tokens.length > 1) {
            StringBuilder tail = new StringBuilder(tokens[1]);
            for (int i = 2; i < tokens.length; i++) tail.append(' ').append(tokens[i]);
            throw new CommandException("Unexpected input after duration: '" + tail
                    + "'\nUsage: workout TYPE /dur DURATION");
        }

        int duration;
        try {
            duration = Integer.parseInt(durToken.split("\\s+")[0]);
        } catch (NumberFormatException e) {
            throw new CommandException("Duration must be a whole number (minutes).", e);
        }

        if (duration <= 0) {
            throw new CommandException("Duration must be a positive number of minutes.");
        }
        return new AddWorkoutCommand(type, duration);
    }

    @Override
    public String execute(EntryList list, Storage storage) throws CommandException {
        WorkoutEntry entry = new WorkoutEntry(workoutType, duration);
        list.add(entry);
        if (storage != null) {
            storage.save(list);
        }
        long workoutCount = list.asList().stream()
                .filter(e -> e.type().equals("WORKOUT"))
                .count();
        return "Got it. I've logged this workout:\n"
                + "  " + entry.toListLine() + "\n"
                + "Great job Mama! You now have a total of " + workoutCount
                + " workouts completed! Lets keep it up!!";
    }
}
